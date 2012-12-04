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

package de.mkristian.ixtlan.translations.client.activities;


import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.ixtlan.translations.client.events.ApplicationEvent;
import de.mkristian.ixtlan.translations.client.events.ApplicationEventHandler;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.places.ApplicationPlace;
import de.mkristian.ixtlan.translations.client.presenters.ApplicationPresenter;

public class ApplicationActivity extends AbstractActivity {

    private final ApplicationPlace place;
    private final ApplicationPresenter presenter;
    
    @Inject
    public ApplicationActivity(@Assisted ApplicationPlace place, ApplicationPresenter presenter) {
        this.place = place;
        this.presenter = presenter;
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        presenter.setDisplay(display);
        eventBus.addHandler(ApplicationEvent.TYPE, new ApplicationEventHandler(){
            @Override
            public void onModelEvent(ModelEvent<Application> event) {
                switch(event.getAction()){
                    case LOAD:
                        if (event.getModel() != null) {
                            presenter.reset( event.getModel() );
                        }
                        if (event.getModels() != null) {
                            presenter.reset( event.getModels() );
                        }
                        break;
                    case ERROR:
                        presenter.onError(null, event.getThrowable());
                        break;
                }
            }

        });
        switch(RestfulActionEnum.valueOf(place.action)){
            case SHOW:
                presenter.show(place.id, place.query);
                break;
            case INDEX:
                presenter.showAll();
                break;
            default:
                presenter.unknownAction(place.action);
        }
    }
}
