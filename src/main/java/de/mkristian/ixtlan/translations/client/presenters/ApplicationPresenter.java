package de.mkristian.ixtlan.translations.client.presenters;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.ixtlan.translations.client.TranslationsErrorHandler;
import de.mkristian.ixtlan.translations.client.caches.ApplicationCacheStore;
import de.mkristian.ixtlan.translations.client.events.ApplicationEvent;
import de.mkristian.ixtlan.translations.client.events.ApplicationEventHandler;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.models.Domain;
import de.mkristian.ixtlan.translations.client.models.Translation;
import de.mkristian.ixtlan.translations.client.models.TranslationKey;
import de.mkristian.ixtlan.translations.client.restservices.ApplicationsRestService;
import de.mkristian.ixtlan.translations.client.views.ApplicationListView;
import de.mkristian.ixtlan.translations.client.views.ApplicationView;

@Singleton
public class ApplicationPresenter extends AbstractPresenter {

    private final ApplicationView view;
    private final ApplicationListView listView;
    private final ApplicationCacheStore cache;
    private final ApplicationsRestService service;

    @Inject
    public ApplicationPresenter(TranslationsErrorHandler errors, ApplicationView view, ApplicationListView listView,
            ApplicationCacheStore cache, ApplicationsRestService service,
            PlaceController places){
        super(errors);
        this.view = view;
        this.view.setPresenter(this);
        this.listView = listView;
        this.cache = cache;
        this.service = service;
    }

    public void setDisplayAndEventBus(AcceptsOneWidget display, EventBus eventBus){
        setDisplay(display);
        eventBus.addHandler(ApplicationEvent.TYPE, new ApplicationEventHandler(){
            @Override
            public void onModelEvent(ModelEvent<Application> event) {
                switch(event.getAction()){
                    case LOAD:
                        if (event.getModel() != null) {
                            view.reset(event.getModel());
                        }
                        if (event.getModels() != null) {
                            listView.reset(event.getModels());
                        }
                        break;
                    case ERROR:
                        onError(null, event.getThrowable());
                        break;
                }
            }
        });
    }

    public void listAll(){
        setWidget(listView);
        listView.reset(cache.getOrLoadModels());
    }
    
    public void edit(TranslationKey key){
        view.edit(key.translation(key.application().getDefaultLocale(), Domain.NONE));
    }
    
    public void show(int id){
        setWidget(view);
        view.show(cache.getOrLoadModel(id));
    }

    public void unknownAction(RestfulAction action){
        errors.show("unknown action: " + action);
    }

    private void onError(Method method, Throwable e) {
        errors.onError(method, e);
    }

    public void save(Translation translation) {
        service.save(translation, new MethodCallback<Translation>() {
            
            @Override
            public void onSuccess(Method method, Translation response) {
                Application application = cache.getModel(response.getAppId());
                application.updateTranslation(response);
                view.reset(response);
            }
            
            @Override
            public void onFailure(Method method, Throwable exception) {
                onError(method, exception);
            }
        });
    }
}
