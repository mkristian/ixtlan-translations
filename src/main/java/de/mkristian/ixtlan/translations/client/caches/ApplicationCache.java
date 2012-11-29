package de.mkristian.ixtlan.translations.client.caches;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;

import de.mkristian.gwt.rails.caches.AbstractPreemptiveCache;
import de.mkristian.gwt.rails.caches.BrowserOrMemoryStore;
import de.mkristian.gwt.rails.caches.Store;
import de.mkristian.gwt.rails.events.ModelEventHandler;
import de.mkristian.ixtlan.translations.client.events.ApplicationEvent;
import de.mkristian.ixtlan.translations.client.models.Application;

@Singleton
public class ApplicationCache extends AbstractPreemptiveCache<Application>{

    static interface Coder extends JsonEncoderDecoder<Application>{
    }
    static Coder coder = GWT.create(Coder.class);

    private static Store<Application> store(){
        return new BrowserOrMemoryStore<Application>( coder, "applications" );
    }
    
    @Inject
    ApplicationCache( EventBus eventBus, ApplicationRemoteModel remote ) {
        super( eventBus, store(), remote );
    }

    @Override
    protected Type<ModelEventHandler<Application>> modelEventHandlerType() {
        return ApplicationEvent.TYPE;
    }
}
