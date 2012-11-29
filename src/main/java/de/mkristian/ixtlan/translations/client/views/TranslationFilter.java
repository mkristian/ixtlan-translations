package de.mkristian.ixtlan.translations.client.views;

import java.util.Iterator;
import java.util.List;

import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.models.Domain;
import de.mkristian.ixtlan.translations.client.models.Locale;
import de.mkristian.ixtlan.translations.client.models.Translation;
import de.mkristian.ixtlan.translations.client.models.TranslationKey;

public class TranslationFilter implements Iterator<Translation>, Iterable<Translation>{

    private Translation current = null;
    private Locale locale;
    private Domain domain;
    private List<TranslationKey> keys;
    private Iterator<TranslationKey> iter;
    private String filter;
    private Application app;
    
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
    
    public void doReset(String filter, int localeId, int domainId){
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
            Translation t = iter.next().translation(locale, domain);
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
  }