package org.dhamma.translations.client.views;

import org.dhamma.translations.client.models.TranslationKey;
import org.dhamma.translations.client.presenters.TranslationKeyPresenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

@ImplementedBy(TranslationKeyViewImpl.class)
public interface TranslationKeyView extends IsWidget {

    void setPresenter(TranslationKeyPresenter presenter);

    void show(TranslationKey model);

    boolean isDirty();
}
