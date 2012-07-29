package org.dhamma.translations.client.presenters;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.translations.client.TranslationsErrorHandler;
import org.dhamma.translations.client.caches.ApplicationCacheStore;
import org.dhamma.translations.client.events.ApplicationEvent;
import org.dhamma.translations.client.events.ApplicationEventHandler;
import org.dhamma.translations.client.models.Application;
import org.dhamma.translations.client.places.ApplicationPlace;
import org.dhamma.translations.client.views.ApplicationListView;
import org.dhamma.translations.client.views.ApplicationView;
import org.fusesource.restygwt.client.Method;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mkristian.gwt.rails.events.ModelEvent;
import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

@Singleton
public class ApplicationPresenter extends AbstractPresenter {

    private final ApplicationView view;
    private final ApplicationListView listView;
    private final ApplicationCacheStore cache;
    private final PlaceController places;

    @Inject
    public ApplicationPresenter(TranslationsErrorHandler errors, ApplicationView view, ApplicationListView listView,
	   ApplicationCacheStore cache, PlaceController places){
        super(errors);
        this.view = view;
        this.view.setPresenter(this);
        this.listView = listView;
        this.listView.setPresenter(this);
        this.cache = cache;
        this.places = places;
    }

    public void init(AcceptsOneWidget display, EventBus eventBus){
        setDisplay(display);
        eventBus.addHandler(ApplicationEvent.TYPE, new ApplicationEventHandler(){
            @Override
            public void onModelEvent(ModelEvent<Application> event) {
                switch(event.getAction()){
                    case LOAD:
                        if (event.getModel() != null) {
                            view.reset(event.getModel());
                        }
                        if (event.getModels() != null) {
                            listView.reset(event.getModels());
                        }
                        break;
                    case ERROR:
                        onError(null, event.getThrowable());
                        break;
                }
            }
        });
    }

    public void listAll(){
        ApplicationPlace next = new ApplicationPlace(RestfulActionEnum.INDEX);
        if (places.getWhere().equals(next)) {
            setWidget(listView);
            listView.reset(cache.getOrLoadModels());
        }
        else {
            places.goTo(next);
        }
    }

    public void show(int id){
        ApplicationPlace next = new ApplicationPlace(id, RestfulActionEnum.SHOW);
        if (places.getWhere().equals(next)) {
            setWidget(view);
            view.show(cache.getOrLoadModel(id));
        }
        else {
            places.goTo(next);
        }
    }

    public void unknownAction(RestfulAction action){
        errors.show("unknown action: " + action);
    }

    private void onError(Method method, Throwable e) {
        errors.onError(method, e);
    }
}
