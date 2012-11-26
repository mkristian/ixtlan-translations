package de.mkristian.ixtlan.translations.client.caches;

import java.util.List;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.google.gwt.core.client.GWT;

import javax.inject.Inject;
import javax.inject.Singleton;


import com.google.gwt.event.shared.EventBus;

import de.mkristian.gwt.rails.caches.AbstractModelCacheStore;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.ixtlan.translations.client.events.ApplicationEvent;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.restservices.ApplicationsRestService;

@Singleton
public class ApplicationCacheStore extends AbstractModelCacheStore<Application>{

    private final ApplicationsRestService restService;

    static interface Coder extends JsonEncoderDecoder<Application>{
    }
    static Coder coder = GWT.create(Coder.class);

    @Inject
    ApplicationCacheStore( EventBus eventBus, ApplicationsRestService restService) {
	super(eventBus, coder, "applications");
        this.restService = restService;
    }

    @Override
    protected void loadModels() {
        restService.index(newListMethodCallback());
    }

    @Override
    protected void loadModel(int id) {
        restService.show(id, newMethodCallback());
    }

    @Override
    protected Application newModel() {
        return new Application();
    }

    @Override
    protected ModelEvent<Application> newEvent(List<Application> models, Action action) {
        return new ApplicationEvent(models, action);
    }

    @Override
    protected ModelEvent<Application> newEvent(Application model, Action action) {
        return new ApplicationEvent(model, action);
    }

    @Override
    protected ModelEvent<Application> newEvent(Throwable e) {
        return new ApplicationEvent(e);
    }
    
// TODO remove that method
    public Application getOrLoadModel(int id){
        Application model = super.get(id);
        if (model == null){
            model = getFromStore(id);
            if( model == null){
                model = new Application();
            }
        }
        if(model.getUrl() == null){
            loadModel(id);
        }
        return model;
    }
}
