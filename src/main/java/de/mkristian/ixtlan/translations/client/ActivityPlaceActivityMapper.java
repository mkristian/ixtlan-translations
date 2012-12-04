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


import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.ixtlan.translations.client.managed.ActivityFactory;

public class ActivityPlaceActivityMapper implements ActivityMapper {
    protected final ActivityFactory factory;
    protected final Notice notice;

    @Inject
    public ActivityPlaceActivityMapper(ActivityFactory factory, Notice notice) {
        this.notice = notice;
        this.factory = factory;
    }

    @SuppressWarnings("unchecked")
    public Activity getActivity(Place place) {
        if (place instanceof RestfulPlace<?, ?>) {
            GWT.log(place.toString());
            return ((RestfulPlace<?, ActivityFactory>) place).create(factory);
        }
        notice.warn("nothing to see");
        return null;
    }
}
