package org.dhamma.translations.client.presenters;

import org.dhamma.translations.client.TranslationsErrorHandler;
import org.dhamma.translations.client.models.TranslationKey;
import org.dhamma.translations.client.places.TranslationKeyPlace;
import org.dhamma.translations.client.views.TranslationKeyListView;
import org.dhamma.translations.client.views.TranslationKeyView;
import org.dhamma.translations.client.restservices.TranslationKeysRestService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import de.mkristian.gwt.rails.places.RestfulAction;
import de.mkristian.gwt.rails.places.RestfulActionEnum;

@Singleton
public class TranslationKeyPresenter extends AbstractPresenter {

    private final TranslationKeyView view;
    private final TranslationKeyListView listView;
    private final TranslationKeysRestService service;
    private final PlaceController places;

    @Inject
    public TranslationKeyPresenter(TranslationsErrorHandler errors, TranslationKeyView view, TranslationKeyListView listView, TranslationKeysRestService service, PlaceController places){
        super(errors);
        this.view = view;
        this.view.setPresenter(this);
        this.listView = listView;
        this.listView.setPresenter(this);
        this.service = service;
        this.places = places;
    }

    public void init(AcceptsOneWidget display){
        setDisplay(display);
    }

    public void listAll(){
        TranslationKeyPlace next = new TranslationKeyPlace(RestfulActionEnum.INDEX);
        if (places.getWhere().equals(next)) {
            setWidget(listView);
            service.index(new MethodCallback<List<TranslationKey>>() {
                @Override
                public void onSuccess(Method method, List<TranslationKey> models) {
                    listView.reset(models);
                }
                @Override
                public void onFailure(Method method, Throwable e) {
                    onError(method, e);   
                }
            });
        }
        else {
            places.goTo(next);
        }
    }

    public void show(int id){
        TranslationKeyPlace next = new TranslationKeyPlace(id, RestfulActionEnum.SHOW);
        if (places.getWhere().equals(next)) {
            setWidget(view);
            service.show(id, new MethodCallback<TranslationKey>() {
                @Override
                public void onSuccess(Method method, TranslationKey model) {
                    view.show(model);
                }
                @Override
                public void onFailure(Method method, Throwable e) {
                    onError(method, e);   
                }
              });
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
