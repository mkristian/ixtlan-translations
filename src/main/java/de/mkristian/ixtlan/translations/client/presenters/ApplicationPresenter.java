package de.mkristian.ixtlan.translations.client.presenters;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.user.client.History;

import de.mkristian.gwt.rails.caches.Cache;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.ixtlan.translations.client.TranslationsErrorHandler;
import de.mkristian.ixtlan.translations.client.caches.ApplicationCache;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.models.Translation;
import de.mkristian.ixtlan.translations.client.models.TranslationKey;
import de.mkristian.ixtlan.translations.client.restservices.ApplicationsRestService;
import de.mkristian.ixtlan.translations.client.views.ApplicationListView;
import de.mkristian.ixtlan.translations.client.views.ApplicationView;
import de.mkristian.ixtlan.translations.client.views.TranslationFilter;

@Singleton
public class ApplicationPresenter extends AbstractPresenter {

    private final ApplicationView view;
    private final ApplicationListView listView;
    private final Cache<Application> cache;
    private final ApplicationsRestService service;

    private final TranslationFilter filter = new TranslationFilter();

    @Inject
    public ApplicationPresenter(TranslationsErrorHandler errors, 
            ApplicationView view, 
            ApplicationListView listView, 
            ApplicationCache cache, 
            ApplicationsRestService service){
        super(errors);
        this.view = view;
        this.view.setPresenter(this);
        this.listView = listView;
        this.cache = cache;
        this.service = service;
    }

    public void showAll(){
        setWidget(listView);
        reset(cache.getOrLoadModels());
    }
    
    public void edit(Translation translation){
        view.edit(translation);
    }
    
    public void show(int id, String query){
        setWidget(view);
        reset( cache.getOrLoadModel(id), query );
    }

    public void unknownAction(RestfulAction action){
        errors.show("unknown action: " + action);
    }

    public void onError(Method method, Throwable e) {
        errors.onError(method, e);
    }

    public void save(final Translation translation) {
        service.save(translation, new MethodCallback<Translation>() {
            
            @Override
            public void onSuccess(Method method, Translation response) {
                Application application = cache.getModel(response.getAppId());
                Translation t = application.updateTranslation(response);
                // take the original key and add the new or updated translation
                TranslationKey key = translation.getTranslationKey();
                // make sure we also have translation added
                key.addTranslation(t);
                // for viewing we need to setup all dependent data first
                t = key.findTranslation(application.detectLocale(t.getLocaleId()),
                        application.detectDomain(t.getDomainId()));
                view.reset(t);
            }
            
            @Override
            public void onFailure(Method method, Throwable exception) {
                onError(method, exception);
            }
        });
    }
    
    public void filter( String filter, int localeId, int domainId ){
        Iterable<Translation> trans = this.filter.reset(filter, localeId, domainId );
        if( trans != null ){
            view.reset( trans );
        }
        History.newItem(this.filter.toToken(), false);
    }
    
    public void reset( Application app) {
        reset( app, null );
    }

    public void reset( Application app, String query ) {
        Iterable<Translation> trans = filter.setup( app );
        if( query != null ){
            trans = filter.reset(query);
        }
        view.reset( app );
        if ( trans != null ){
            view.reset(filter.getLocale(), filter.getDomain(), 
                    filter.getFilter());
            view.reset( trans );
        }
    }
    public void reset(List<Application> apps) {
        listView.reset( apps );
    }
}
