package de.mkristian.ixtlan.translations.client;

import java.util.List;

import javax.inject.Singleton;

import de.mkristian.gwt.rails.views.LinksPanel;
import de.mkristian.ixtlan.translations.client.models.Application;

@Singleton
public class ApplicationLinksPanel extends LinksPanel<Application> {

    @Override
    protected void initApplications(List<Application> applications) {
        if (applications != null) {
            for(Application app: applications){
                addLink(app.getName(), app.getUrl());
            }
            setVisible(true);
        }
        else {
            clear();
            setVisible(false);
        }
    }
}