package org.dhamma.translations.client.managed;

import de.mkristian.gwt.rails.BaseModule;

import org.dhamma.translations.client.activities.LoginActivity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.name.Names;
import com.google.gwt.core.client.GWT;
import com.google.inject.Provider;
import com.google.inject.Singleton;

public class ManagedGinModule extends BaseModule {

   @Override
    protected void configure() {
        super.configure();
        bind(org.dhamma.translations.client.restservices.TranslationKeysRestService.class).toProvider(TranslationKeysRestServiceProvider.class);
        install(new GinFactoryModuleBuilder()
            .implement(Activity.class, Names.named("translation_keys"), org.dhamma.translations.client.activities.TranslationKeyActivity.class)
            .implement(Activity.class, Names.named("login"), LoginActivity.class)
            .build(ActivityFactory.class));
    }

    @Singleton
    public static class TranslationKeysRestServiceProvider implements Provider<org.dhamma.translations.client.restservices.TranslationKeysRestService> {
        private final org.dhamma.translations.client.restservices.TranslationKeysRestService service = GWT.create(org.dhamma.translations.client.restservices.TranslationKeysRestService.class);
        public org.dhamma.translations.client.restservices.TranslationKeysRestService get() {
            return service;
        }
    }
}

