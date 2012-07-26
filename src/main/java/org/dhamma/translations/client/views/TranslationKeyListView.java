package org.dhamma.translations.client.views;

import java.util.List;

import org.dhamma.translations.client.models.TranslationKey;
import org.dhamma.translations.client.presenters.TranslationKeyPresenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.ImplementedBy;

@ImplementedBy(TranslationKeyListViewImpl.class)
public interface TranslationKeyListView extends IsWidget {

    void setPresenter(TranslationKeyPresenter presenter);

    void reset(List<TranslationKey> models);
}