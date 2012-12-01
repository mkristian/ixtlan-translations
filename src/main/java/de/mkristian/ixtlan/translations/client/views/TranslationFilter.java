package de.mkristian.ixtlan.translations.client.views;

import java.util.Iterator;
import java.util.List;

import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.models.Domain;
import de.mkristian.ixtlan.translations.client.models.Locale;
import de.mkristian.ixtlan.translations.client.models.Translation;
import de.mkristian.ixtlan.translations.client.models.TranslationKey;
import de.mkristian.ixtlan.translations.client.places.ApplicationPlace;
import de.mkristian.ixtlan.translations.client.places.ApplicationPlaceTokenizer;

public class TranslationFilter implements Iterator<Translation>, Iterable<Translation>{

    private static final String SEPARATOR = "_";
    private Translation current = null;
    private Locale locale;
    private Domain domain;
    private List<TranslationKey> keys;
    private Iterator<TranslationKey> iter;
    private String filter;
    private Application app;
    
    private final ApplicationPlaceTokenizer tokenizer = new ApplicationPlaceTokenizer();
    
    public Iterable<Translation> setup(Application app){
        if (app != null ){
            if( this.app == null || this.app.getId() != app.getId() ){
                this.app = app;
                doReset(null, 0, 0);
            }
            else {
                doReset(filter, locale == null ? 0 : locale.id, domain.id);
            }
            this.keys = app.getTranslationKeys();
            // return the iterator
            return reset();
        }
        return null;
    }
    
    private void doReset(String filter, int localeId, int domainId){
         if( this.locale == null ||
                this.domain == null ||
                this.filter == null ||
                this.locale.id != localeId || 
                !this.filter.equals( filter ) ||
                this.domain.id != domainId ){
             this.locale = app.detectLocale( localeId );
             this.domain = app.detectDomain( domainId );
             this.filter = filter == null ? "" : filter;
         }
    }

    public String toToken(){
        String query = filter + SEPARATOR + locale.id + SEPARATOR + domain.id;
        return tokenizer.getToken( new ApplicationPlace( app, 
                    RestfulActionEnum.SHOW, query ) );
    }

    public Iterable<Translation> reset(String query){
        String[] parts = query.split(SEPARATOR);
        int localeId;
        try {
            localeId = Integer.parseInt(parts[1]);
        }
        catch( NumberFormatException e ){
            localeId = app.getDefaultLocale().id;
        }
        int domainId;
        try {
            domainId = Integer.parseInt(parts[2]);
        }
        catch( NumberFormatException e ){
            domainId = Domain.NONE.id;
        }
        doReset(parts[0], localeId, domainId);
        return reset();
    }
    
    public Iterable<Translation> reset(String filter, int localeId, int domainId){
        doReset(filter, localeId, domainId);
        return reset();
    }
    
    private Iterable<Translation> reset(){
        if (keys != null){
            iter = keys.iterator();
            current = nextCurrent();
        }
        else {
            current = null;
        }
        return this;
    }

    private Translation nextCurrent() {
        while(iter.hasNext()){
            Translation t = iter.next().findTranslation(locale, domain);
            if (t.getText().contains(filter)){
               return t; 
            }
        }
        return null;
    }
    
    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Translation next() {
        Translation next = current;
        current = nextCurrent();
        return next;
    }

    @Override
    public void remove() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Iterator<Translation> iterator() {
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public Domain getDomain() {
        return domain;
    }

    public String getFilter() {
        return filter;
    }
  }