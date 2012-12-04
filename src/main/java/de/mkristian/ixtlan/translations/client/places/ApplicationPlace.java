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

package de.mkristian.ixtlan.translations.client.places;

import com.google.gwt.activity.shared.Activity;

import de.mkristian.gwt.rails.places.QueryableRestfulPlace;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.ixtlan.translations.client.managed.ActivityFactory;
import de.mkristian.ixtlan.translations.client.models.Application;

public class ApplicationPlace extends QueryableRestfulPlace<Application, ActivityFactory> {
    
    public static final String NAME = "applications";

    public Activity create(ActivityFactory factory){
        return factory.create(this);
    }
    
    public ApplicationPlace(RestfulAction restfulAction) {
        super(restfulAction, NAME);
    }

    public ApplicationPlace(RestfulAction restfulAction, String query) {
        super(restfulAction, NAME, query);
    }

    public ApplicationPlace(Application model, RestfulAction restfulAction) {
        super(model.getId(), model, restfulAction, NAME);
    }

    public ApplicationPlace(Application model, RestfulAction restfulAction, String query) {
        super(model.getId(), model, restfulAction, NAME, query);
    }

    public ApplicationPlace(int id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }    
    
    public ApplicationPlace(int id, RestfulAction restfulAction, String query) {
        super(id, restfulAction, NAME, query);
    }    
    
    public ApplicationPlace(String id, RestfulAction restfulAction) {
        super(id, restfulAction, NAME);
    }
}