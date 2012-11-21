package de.mkristian.ixtlan.translations.client.views;

import java.util.List;


import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.presenters.ApplicationPresenter;

@ImplementedBy(ApplicationListViewImpl.class)
public interface ApplicationListView extends IsWidget {

    void setPresenter(ApplicationPresenter presenter);

    void reset(List<Application> models);
}