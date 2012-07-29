package org.dhamma.translations.client.views;

import org.dhamma.translations.client.models.Application;
import org.dhamma.translations.client.presenters.ApplicationPresenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

@ImplementedBy(ApplicationViewImpl.class)
public interface ApplicationView extends IsWidget {

    void setPresenter(ApplicationPresenter presenter);

    void show(Application model);

    void reset(Application model);
}
