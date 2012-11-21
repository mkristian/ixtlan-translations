package de.mkristian.ixtlan.translations.client.managed;

import de.mkristian.gwt.rails.BaseModule;
import de.mkristian.ixtlan.translations.client.activities.LoginActivity;


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
        bind(de.mkristian.ixtlan.translations.client.restservices.ApplicationsRestService.class).toProvider(ApplicationsRestServiceProvider.class);
        install(new GinFactoryModuleBuilder()
            .implement(Activity.class, Names.named("applications"), de.mkristian.ixtlan.translations.client.activities.ApplicationActivity.class)
            .implement(Activity.class, Names.named("login"), LoginActivity.class)
            .build(ActivityFactory.class));
    }

    @Singleton
    public static class ApplicationsRestServiceProvider implements Provider<de.mkristian.ixtlan.translations.client.restservices.ApplicationsRestService> {
        private final de.mkristian.ixtlan.translations.client.restservices.ApplicationsRestService service = GWT.create(de.mkristian.ixtlan.translations.client.restservices.ApplicationsRestService.class);
        public de.mkristian.ixtlan.translations.client.restservices.ApplicationsRestService get() {
            return service;
        }
    }
}




