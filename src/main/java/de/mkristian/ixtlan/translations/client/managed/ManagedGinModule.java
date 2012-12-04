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




