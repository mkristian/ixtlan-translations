package org.dhamma.translations.client.managed;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.place.shared.PlaceController;

import static de.mkristian.gwt.rails.places.RestfulActionEnum.*;

import de.mkristian.gwt.rails.session.Guard;
import de.mkristian.gwt.rails.views.SessionMenu;

@Singleton
public class TranslationsMenu extends SessionMenu {

    @Inject
    TranslationsMenu(final PlaceController placeController,
                        final Guard guard){
        super(placeController, guard);
    }
}
