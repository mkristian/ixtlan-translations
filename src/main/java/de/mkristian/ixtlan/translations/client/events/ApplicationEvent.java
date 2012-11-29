package de.mkristian.ixtlan.translations.client.events;

import java.util.List;

import org.fusesource.restygwt.client.Method;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.translations.client.models.Application;

public class ApplicationEvent extends ModelEvent<Application> {

    public static final Type<ModelEventHandler<Application>> TYPE = new Type<ModelEventHandler<Application>>();

    public ApplicationEvent(Method method, Throwable throwable){
        super(method, throwable);
    }

    public ApplicationEvent(Method method, Application model, ModelEvent.Action action) {
        super(method, model, action);
    }

    public ApplicationEvent(Method method, List<Application> models, ModelEvent.Action action) {
        super(method, models, action);
    }

    @Override
    public Type<ModelEventHandler<Application>> getAssociatedType() {
        return TYPE;
    }
}