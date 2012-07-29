package org.dhamma.translations.client.views;

import java.util.List;

import org.dhamma.translations.client.models.Application;
import org.dhamma.translations.client.presenters.ApplicationPresenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

@ImplementedBy(ApplicationListViewImpl.class)
public interface ApplicationListView extends IsWidget {

    void setPresenter(ApplicationPresenter presenter);

    void reset(List<Application> models);
}