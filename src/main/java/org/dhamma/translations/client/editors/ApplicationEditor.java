package org.dhamma.translations.client.editors;

import org.dhamma.translations.client.models.Application;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;


public class ApplicationEditor extends Composite implements Editor<Application>{
    
    interface Binder extends UiBinder<Widget, ApplicationEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField public NumberLabel<Integer> id;

    @UiField TextBox name;

    @UiField TextBox url;

    @UiField DateBox updatedAt;

    public ApplicationEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void resetSignature() {
        this.signature.setVisible(id.getValue() != null && id.getValue() > 0);
    }

    public void setEnabled(boolean enabled) {
        resetSignature();
        this.name.setEnabled(enabled);
        this.url.setEnabled(enabled);
        this.updatedAt.setEnabled(enabled);
    }
}
