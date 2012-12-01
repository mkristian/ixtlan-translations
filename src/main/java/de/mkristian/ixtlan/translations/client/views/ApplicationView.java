package de.mkristian.ixtlan.translations.client.views;


import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.models.Domain;
import de.mkristian.ixtlan.translations.client.models.Locale;
import de.mkristian.ixtlan.translations.client.models.Translation;
import de.mkristian.ixtlan.translations.client.presenters.ApplicationPresenter;

@ImplementedBy(ApplicationViewImpl.class)
public interface ApplicationView extends IsWidget {

    void setPresenter(ApplicationPresenter presenter);

    void reset(Application model);

    void edit(Translation model);

    void reset(Translation model);

    void reset(Iterable<Translation> trans);

    void reset(Locale locale, Domain domain, String text);
}
