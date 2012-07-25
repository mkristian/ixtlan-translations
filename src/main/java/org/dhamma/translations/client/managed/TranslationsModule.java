package org.dhamma.translations.client.managed;

import org.dhamma.translations.client.TranslationsApplication;
import org.dhamma.translations.client.TranslationsConfirmation;
import org.dhamma.translations.client.ActivityPlaceActivityMapper;
import de.mkristian.gwt.rails.Application;
import de.mkristian.gwt.rails.BaseModule;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.place.shared.PlaceController.Delegate;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

public class TranslationsModule extends BaseModule {

    @Override
    protected void configure() {
        super.configure();
        bind(Application.class).to(TranslationsApplication.class);
        bind(PlaceHistoryMapper.class).to(TranslationsPlaceHistoryMapper.class).in(Singleton.class);
        bind(ActivityMapper.class).to(ActivityPlaceActivityMapper.class).in(Singleton.class);
        bind(Delegate.class).to(TranslationsConfirmation.class);
        bind(TranslationsConfirmation.class);
        install(new GinFactoryModuleBuilder()
            .build(ActivityFactory.class));
    }
}
