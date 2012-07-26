package org.dhamma.translations.client.places;

import de.mkristian.gwt.rails.places.RestfulPlaceTokenizer;

public class TranslationKeyPlaceTokenizer extends RestfulPlaceTokenizer<TranslationKeyPlace> {
    
    public TranslationKeyPlace getPlace(String token) {
        Token t = toToken(token);
        if(t.identifier == null){
            return new TranslationKeyPlace(t.action);
        }
        else {
            return new TranslationKeyPlace(t.id, t.action);
        }
    }
}
