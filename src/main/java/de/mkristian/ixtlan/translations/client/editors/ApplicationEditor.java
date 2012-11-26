package de.mkristian.ixtlan.translations.client.editors;


import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.ixtlan.translations.client.models.Application;


public class ApplicationEditor extends Composite implements Editor<Application>{
    
    interface Binder extends UiBinder<Widget, ApplicationEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
   
    @UiField Label name;

    public ApplicationEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void resetSignature() {
    }

    public void setEnabled(boolean enabled) {
    }
}
