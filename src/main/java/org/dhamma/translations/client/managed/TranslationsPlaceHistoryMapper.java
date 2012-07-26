package org.dhamma.translations.client.managed;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.core.client.GWT;

import de.mkristian.gwt.rails.places.SessionRestfulPlaceHistoryMapper;
import de.mkristian.gwt.rails.session.HasSession;

@Singleton
public class TranslationsPlaceHistoryMapper extends SessionRestfulPlaceHistoryMapper {

    HasSession session;
    @Inject
    public TranslationsPlaceHistoryMapper(HasSession session){
        super(session);
        this.session = session;
        GWT.log(session.toString());
        register("translation_keys", new org.dhamma.translations.client.places.TranslationKeyPlaceTokenizer());
    }
}
