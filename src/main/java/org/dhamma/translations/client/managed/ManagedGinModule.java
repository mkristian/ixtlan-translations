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
        bind(org.dhamma.translations.client.restservices.ApplicationsRestService.class).toProvider(ApplicationsRestServiceProvider.class);
        install(new GinFactoryModuleBuilder()
            .implement(Activity.class, Names.named("applications"), org.dhamma.translations.client.activities.ApplicationActivity.class)
            .implement(Activity.class, Names.named("login"), LoginActivity.class)
            .build(ActivityFactory.class));
    }

    @Singleton
    public static class ApplicationsRestServiceProvider implements Provider<org.dhamma.translations.client.restservices.ApplicationsRestService> {
        private final org.dhamma.translations.client.restservices.ApplicationsRestService service = GWT.create(org.dhamma.translations.client.restservices.ApplicationsRestService.class);
        public org.dhamma.translations.client.restservices.ApplicationsRestService get() {
            return service;
        }
    }
}




