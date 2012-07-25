package org.dhamma.translations.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import de.mkristian.gwt.rails.Application;
import de.mkristian.gwt.rails.dispatchers.DefaultDispatcherSingleton;

import org.fusesource.restygwt.client.Defaults;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TranslationsEntryPoint implements EntryPoint {

    @GinModules(TranslationsGinModule.class)
    static public interface TranslationsGinjector extends Ginjector {
        PlaceHistoryHandler getPlaceHistoryHandler();
        Application getApplication();
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        Defaults.setServiceRoot(GWT.getModuleBaseURL().replaceFirst("[a-zA-Z0-9_]+/$", ""));
        Defaults.setDispatcher(DefaultDispatcherSingleton.INSTANCE);
        GWT.log("base url for restservices: " + Defaults.getServiceRoot());

        final TranslationsGinjector injector = GWT.create(TranslationsGinjector.class);

        // setup display
        injector.getApplication().run();
    
        // Goes to the place represented on URL else default place
        injector.getPlaceHistoryHandler().handleCurrentHistory();
    }
}
