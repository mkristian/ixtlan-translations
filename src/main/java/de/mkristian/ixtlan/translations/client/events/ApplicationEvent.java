package de.mkristian.ixtlan.translations.client.events;

import java.util.List;


import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.translations.client.models.Application;

public class ApplicationEvent extends ModelEvent<Application> {

    public static final Type<ModelEventHandler<Application>> TYPE = new Type<ModelEventHandler<Application>>();

    public ApplicationEvent(Throwable throwable){
        super(throwable);
    }

    public ApplicationEvent(Application model, ModelEvent.Action action) {
        super(model, action);
    }

    public ApplicationEvent(List<Application> models, ModelEvent.Action action) {
        super(models, action);
    }

    @Override
    public Type<ModelEventHandler<Application>> getAssociatedType() {
        return TYPE;
    }
}