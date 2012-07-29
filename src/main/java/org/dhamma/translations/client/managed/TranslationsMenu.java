package org.dhamma.translations.client.managed;

import static de.mkristian.gwt.rails.places.RestfulActionEnum.INDEX;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.place.shared.PlaceController;

import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.views.SessionMenu;

@Singleton
public class TranslationsMenu extends SessionMenu {

    @Inject
    TranslationsMenu(final PlaceController placeController,
                        final Guard guard){
        super(placeController, guard);
        addButton("Applications", new org.dhamma.translations.client.places.ApplicationPlace(INDEX));
    }
}
