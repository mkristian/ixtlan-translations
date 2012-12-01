package de.mkristian.ixtlan.translations.client.places;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.QueryableRestfulPlace;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.ixtlan.translations.client.managed.ActivityFactory;
import de.mkristian.ixtlan.translations.client.models.Application;

public class ApplicationPlace extends QueryableRestfulPlace<Application, ActivityFactory> {
    
    public static final String NAME = "applications";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ApplicationPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public ApplicationPlace(RestfulAction restfulAction, String query) {
        super(restfulAction, NAME, query);
    }

    public ApplicationPlace(Application model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public ApplicationPlace(Application model, RestfulAction restfulAction, String query) {
        super(model.getId(), model, restfulAction, NAME, query);
    }

    public ApplicationPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public ApplicationPlace(int id, RestfulAction restfulAction, String query) {
        super(id, restfulAction, NAME, query);
    }    
    
    public ApplicationPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}