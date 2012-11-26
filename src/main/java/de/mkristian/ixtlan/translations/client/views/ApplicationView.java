package de.mkristian.ixtlan.translations.client.views;


import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.models.Translation;
import de.mkristian.ixtlan.translations.client.presenters.ApplicationPresenter;

@ImplementedBy(ApplicationViewImpl.class)
public interface ApplicationView extends IsWidget {

    void setPresenter(ApplicationPresenter presenter);//, TranslationViewImpl widget);

    void show(Application model);

    void reset(Application model);

    void edit(Translation model);

    void reset(Translation model);
}
