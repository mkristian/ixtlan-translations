package de.mkristian.ixtlan.translations.client.caches;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.fusesource.restygwt.client.Method;

import com.google.gwt.event.shared.EventBus;

import de.mkristian.gwt.rails.caches.RemoteModelAdapter;
import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEvent.Action;
import de.mkristian.ixtlan.translations.client.events.ApplicationEvent;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.restservices.ApplicationsRestService;

@Singleton
public class ApplicationRemoteModel extends RemoteModelAdapter<Application> {

    private final ApplicationsRestService restService;

    @Inject
    protected ApplicationRemoteModel(EventBus eventBus, ApplicationsRestService restService) {
        super(eventBus);
        this.restService = restService;
    }

    @Override
    public Application newModel() {
        return new Application();
    }

    @Override
    protected ModelEvent<Application> newEvent(Method method, List<Application> models, Action action) {
        return new ApplicationEvent(method, models, action);
    }

    @Override
    protected ModelEvent<Application> newEvent(Method method, Application model, Action action) {
        return new ApplicationEvent(method, model, action);
    }

    @Override
    protected ModelEvent<Application> newEvent(Method method, Throwable e) {
        return new ApplicationEvent(method, e);
    }

    @Override
    public void retrieveAll() {
        restService.index(newRetrieveAllCallback());
    }

    @Override
    public void retrieve(int id) {
        restService.show(id, newRetrieveCallback());
    }
    
}