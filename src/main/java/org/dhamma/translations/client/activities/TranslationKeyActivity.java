package org.dhamma.translations.client.activities;

import org.dhamma.translations.client.places.TranslationKeyPlace;
import org.dhamma.translations.client.presenters.TranslationKeyPresenter;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.mkristian.gwt.rails.places.RestfulActionEnum;

public class TranslationKeyActivity extends AbstractActivity {

    private final TranslationKeyPlace place;
    private final TranslationKeyPresenter presenter;
    
    @Inject
    public TranslationKeyActivity(@Assisted TranslationKeyPlace place, TranslationKeyPresenter presenter) {
        this.place = place;
        this.presenter = presenter;
    }

    public void start(AcceptsOneWidget display, EventBus eventBus) {
        presenter.init(display);
        switch(RestfulActionEnum.valueOf(place.action)){
            case SHOW:
                presenter.show(place.id);
                break;
            case INDEX:
                presenter.listAll();
                break;
            default:
                presenter.unknownAction(place.action);
        }
    }
}
