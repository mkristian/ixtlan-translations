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

import javax.inject.Inject;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

import de.mkristian.gwt.rails.Notice;
import de.mkristian.gwt.rails.places.RestfulPlace;
import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.session.NeedsAuthorization;
import de.mkristian.gwt.rails.session.NoAuthorization;
import de.mkristian.ixtlan.translations.client.managed.ActivityFactory;
import de.mkristian.ixtlan.translations.client.places.LoginPlace;

public class SessionActivityPlaceActivityMapper extends ActivityPlaceActivityMapper {

    private final Guard guard;

    @Inject
    public SessionActivityPlaceActivityMapper(ActivityFactory factory, Guard guard, Notice notice) {
        super(factory, notice);
        this.guard = guard;
    }

    public Activity getActivity(Place place) {
        return pessimisticGetActivity(place);
    }

    /**
     * pessimistic in the sense that default is authorisation, only the places
     * which implements {@link NoAuthorization} will be omitted by the check.
     */
    protected Activity pessimisticGetActivity(Place place) {
        if (!(place instanceof NoAuthorization)) {
            if(guard.hasSession()){
                if(!guard.isAllowed((RestfulPlace<?,?>)place)){
                    notice.warn("nothing to see");
                    return null;
                }
                //TODO move into a dispatch filter or callback filter
                guard.resetCountDown();
            }
            else {
                return LoginPlace.LOGIN.create(factory);
            }
        }
        return super.getActivity(place);
    }

    /**
     * optimistic in the sense that default is no authorisation, only the places
     * which implements {@link NeedsAuthorization} will be checked.
     */
    protected Activity optimisticGetActivity(Place place) {
        if (place instanceof NeedsAuthorization) {
            if(guard.hasSession()){
                if(!guard.isAllowed((RestfulPlace<?,?>)place)){
                    notice.warn("nothing to see");
                    return null;
                }
                //TODO move into a dispatch filter or callback filter
                guard.resetCountDown();
            }
            else {
                return LoginPlace.LOGIN.create(factory);
            }
        }
        return super.getActivity(place);
    }
}
