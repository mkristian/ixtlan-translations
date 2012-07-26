package org.dhamma.translations.client.presenters;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

import org.dhamma.translations.client.TranslationsErrorHandler;

public class AbstractPresenter {

    protected final TranslationsErrorHandler errors;
    private AcceptsOneWidget display;

    public AbstractPresenter(TranslationsErrorHandler errors){
        this.errors = errors;
    }

    public void setDisplay(AcceptsOneWidget display){
        this.display = display;
        this.errors.setDisplay(display);
    }

    protected void setWidget(IsWidget view) {
        display.setWidget(view.asWidget());
    }
}
