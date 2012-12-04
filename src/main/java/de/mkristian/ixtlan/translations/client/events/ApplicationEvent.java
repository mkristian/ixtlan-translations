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

package de.mkristian.ixtlan.translations.client.events;

import java.util.List;

import org.fusesource.restygwt.client.Method;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.translations.client.models.Application;

public class ApplicationEvent extends ModelEvent<Application> {

    public static final Type<ModelEventHandler<Application>> TYPE = new Type<ModelEventHandler<Application>>();

    public ApplicationEvent(Method method, Throwable throwable){
        super(method, throwable);
    }

    public ApplicationEvent(Method method, Application model, ModelEvent.Action action) {
        super(method, model, action);
    }

    public ApplicationEvent(Method method, List<Application> models, ModelEvent.Action action) {
        super(method, models, action);
    }

    @Override
    public Type<ModelEventHandler<Application>> getAssociatedType() {
        return TYPE;
    }
}