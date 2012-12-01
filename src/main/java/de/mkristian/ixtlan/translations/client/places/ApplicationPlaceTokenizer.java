package de.mkristian.ixtlan.translations.client.places;

import de.mkristian.gwt.rails.places.QueryableRestfulPlaceTokenizer;
import de.mkristian.gwt.rails.places.RestfulAction;

public class ApplicationPlaceTokenizer extends QueryableRestfulPlaceTokenizer<ApplicationPlace> {
    
    @Override
    protected ApplicationPlace newRestfulPlace(RestfulAction action,
            String query) {        
        return new ApplicationPlace(action, query);
    }

    @Override
    protected ApplicationPlace newRestfulPlace(int id, RestfulAction action,
            String query) {
        return new ApplicationPlace(id, action, query);
    }
}
