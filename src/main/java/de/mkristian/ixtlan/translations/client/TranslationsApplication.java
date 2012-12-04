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


import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.mkristian.gwt.rails.SessionApplication;
import de.mkristian.ixtlan.translations.client.managed.TranslationsMenu;
import de.mkristian.ixtlan.translations.client.models.User;
import de.mkristian.ixtlan.translations.client.presenters.LoginPresenter;

public class TranslationsApplication extends Composite implements SessionApplication<User> {

    interface Binder extends UiBinder<Widget, TranslationsApplication> {}

    private static Binder BINDER = GWT.create(Binder.class);

    @UiField(provided=true) final SimplePanel display = new ScrollPanel();
    @UiField(provided=true) Panel header;
    @UiField(provided=true) Panel navigation;
    @UiField(provided=true) Panel footer;

    @Inject
    TranslationsApplication(final ActivityManager activityManager, 
            final TranslationsMenu menu, 
            final BreadCrumbsPanel breadCrumbs,
            final ApplicationLinksPanel links,
            final LoginPresenter presenter){
        presenter.init(this);
        activityManager.setDisplay(display);
        this.navigation = menu;
        this.footer = links;
        this.header = breadCrumbs;
        initWidget(BINDER.createAndBindUi(this));
    }

    @Override
    public void run() {
        LayoutPanel root = RootLayoutPanel.get();
        root.add(this.asWidget());
    }

    @Override
    public void startSession(User user) {
        ((BreadCrumbsPanel) this.header).initUser(user);
        ((ApplicationLinksPanel) this.footer).initApplications(user.applications);
        this.navigation.setVisible(true);
    }

    @Override
    public void stopSession() {
        ((BreadCrumbsPanel) this.header).initUser(null);
        ((ApplicationLinksPanel) this.footer).initApplications(null);
        this.navigation.setVisible(false);
    }
}
