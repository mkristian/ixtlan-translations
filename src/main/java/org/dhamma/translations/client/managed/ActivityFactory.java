package org.dhamma.translations.client.managed;

import com.google.gwt.activity.shared.Activity;
import com.google.inject.name.Named;

import org.dhamma.translations.client.places.LoginPlace;

public interface ActivityFactory {
    @Named("translation_keys") Activity create(org.dhamma.translations.client.places.TranslationKeyPlace place);
    @Named("login") Activity create(LoginPlace place);
}