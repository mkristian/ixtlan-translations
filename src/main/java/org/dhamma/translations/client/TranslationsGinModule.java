package org.dhamma.translations.client;

import org.dhamma.translations.client.TranslationsApplication;
import org.dhamma.translations.client.TranslationsConfirmation;
import org.dhamma.translations.client.SessionActivityPlaceActivityMapper;
import org.dhamma.translations.client.managed.TranslationsPlaceHistoryMapper;
import org.dhamma.translations.client.managed.ManagedGinModule;
import org.dhamma.translations.client.models.User;
import org.dhamma.translations.client.presenters.LoginPresenter;
import org.dhamma.translations.client.views.LoginViewImpl;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.PlaceController.Delegate;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import de.mkristian.gwt.rails.Application;
import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.session.HasSession;
import de.mkristian.gwt.rails.session.LoginView;
import de.mkristian.gwt.rails.session.SessionManager;

public class TranslationsGinModule extends ManagedGinModule {

    @Override
    protected void configure() {
        super.configure();
        bind(Application.class).to(TranslationsApplication.class);
        bind(LoginView.Presenter.class).to(LoginPresenter.class);
        bind(Guard.class).to(Key.get(new TypeLiteral<SessionManager<User>>() {})).in(Singleton.class);
        bind(HasSession.class).to(Key.get(new TypeLiteral<SessionManager<User>>() {})).in(Singleton.class);
        bind(PlaceHistoryMapper.class).to(TranslationsPlaceHistoryMapper.class).in(Singleton.class);
        bind(ActivityMapper.class).to(SessionActivityPlaceActivityMapper.class).in(Singleton.class);
        bind(Delegate.class).to(TranslationsConfirmation.class);
        bind(TranslationsConfirmation.class);
        bind(LoginView.class).to(LoginViewImpl.class);
    }
}
