package org.dhamma.translations.client;

import javax.inject.Singleton;

import org.dhamma.translations.client.models.Application;
import org.dhamma.translations.client.models.User;

import de.mkristian.gwt.rails.views.LinksPanel;

@Singleton
public class ApplicationLinksPanel extends LinksPanel<User> {

    @Override
    protected void initUser(User user) {
        if (user != null) {
            for(Application app: user.applications){
                addLink(app.getName().equals("THIS") ? 
                    "users" : 
                    app.getName(), app.getUrl());
            }
            setVisible(true);
        }
        else {
            clear();
            setVisible(false);
        }
    }
}