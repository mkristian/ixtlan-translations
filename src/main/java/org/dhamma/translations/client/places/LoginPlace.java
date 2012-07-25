package org.dhamma.translations.client.places;

import org.dhamma.translations.client.managed.ActivityFactory;

import de.mkristian.gwt.rails.places.RestfulPlace;

import com.google.gwt.activity.shared.Activity;

public class LoginPlace extends RestfulPlace<Void, ActivityFactory> {

    public static final LoginPlace LOGIN = new LoginPlace();

    private LoginPlace() {
        super(null, null);
    }

    @Override
    public Activity create(ActivityFactory factory) {
        return factory.create(this);
    }
}
