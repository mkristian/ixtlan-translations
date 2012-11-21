package de.mkristian.ixtlan.translations.client.managed;

import com.google.gwt.activity.shared.Activity;
import com.google.inject.name.Named;

import de.mkristian.ixtlan.translations.client.places.LoginPlace;


public interface ActivityFactory {
    @Named("applications") Activity create(de.mkristian.ixtlan.translations.client.places.ApplicationPlace place);
    @Named("login") Activity create(LoginPlace place);
}