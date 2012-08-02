package org.dhamma.translations.client.editors;

import org.dhamma.translations.client.models.RemotePermission;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Widget;


public class RemotePermissionEditor extends Composite implements Editor<RemotePermission>{
    
    interface Binder extends UiBinder<Widget, RemotePermissionEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);
    
    @Ignore @UiField FlowPanel signature;

    @UiField public NumberLabel<Integer> id;
    @UiField DateLabel createdAt;
    @UiField DateLabel updatedAt;

    @UiField TextBox ip;

    @UiField TextBox token;

    public RemotePermissionEditor() {
        initWidget(BINDER.createAndBindUi(this));
    }

    public void resetSignature() {
        this.signature.setVisible(id.getValue() != null && id.getValue() > 0);
    }

    public void setEnabled(boolean enabled) {
        resetSignature();
        this.ip.setEnabled(enabled);
        this.token.setEnabled(enabled);
    }
}
