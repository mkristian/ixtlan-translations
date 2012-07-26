package org.dhamma.translations.client.places;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulPlace;

import org.dhamma.translations.client.managed.ActivityFactory;
import org.dhamma.translations.client.models.TranslationKey;

import com.google.gwt.activity.shared.Activity;

public class TranslationKeyPlace extends RestfulPlace<TranslationKey, ActivityFactory> {
    
    public static final String NAME = "translation_keys";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public TranslationKeyPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public TranslationKeyPlace(TranslationKey model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public TranslationKeyPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public TranslationKeyPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}