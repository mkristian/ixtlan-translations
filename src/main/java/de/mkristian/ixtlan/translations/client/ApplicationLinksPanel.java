package de.mkristian.ixtlan.translations.client;

import javax.inject.Singleton;


import de.mkristian.gwt.rails.views.LinksPanel;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.models.User;

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