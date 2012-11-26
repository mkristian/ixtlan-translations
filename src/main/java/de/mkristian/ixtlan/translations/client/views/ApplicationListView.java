package de.mkristian.ixtlan.translations.client.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.ixtlan.translations.client.models.Application;

@ImplementedBy(ApplicationListViewImpl.class)
public interface ApplicationListView extends IsWidget {

    void reset(List<Application> models);
}