/*
 * ixtlan_gettext - helper to use fast_gettext with datamapper/ixtlan
 * Copyright (C) 2012 Christian Meier
 *
 * This file is part of ixtlan_gettext.
 *
 * ixtlan_gettext is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * ixtlan_gettext is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with ixtlan_gettext.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.mkristian.ixtlan.translations.client;


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
import de.mkristian.ixtlan.translations.client.SessionActivityPlaceActivityMapper;
import de.mkristian.ixtlan.translations.client.TranslationsApplication;
import de.mkristian.ixtlan.translations.client.TranslationsConfirmation;
import de.mkristian.ixtlan.translations.client.managed.ManagedGinModule;
import de.mkristian.ixtlan.translations.client.managed.TranslationsPlaceHistoryMapper;
import de.mkristian.ixtlan.translations.client.models.User;
import de.mkristian.ixtlan.translations.client.presenters.LoginPresenter;
import de.mkristian.ixtlan.translations.client.views.LoginViewImpl;

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
        bind(LoginView.class).to(LoginViewImpl.class);
        bind(TranslationsConfirmation.class);
    }
}
