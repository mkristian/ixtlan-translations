package org.dhamma.translations.client.managed;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.mkristian.gwt.rails.places.SessionRestfulPlaceHistoryMapper;
import de.mkristian.gwt.rails.session.HasSession;

@Singleton
public class TranslationsPlaceHistoryMapper extends SessionRestfulPlaceHistoryMapper {

    @Inject
    public TranslationsPlaceHistoryMapper(HasSession session){
        super(session);
    }
}
