package org.dhamma.translations.client.caches;

import java.util.List;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.google.gwt.core.client.GWT;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.translations.client.events.ApplicationEvent;
import org.dhamma.translations.client.models.Application;
import org.dhamma.translations.client.restservices.ApplicationsRestService;

import com.google.gwt.event.shared.EventBus;

import de.mkristian.gwt.rails.caches.AbstractModelCacheStore;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;

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
}
