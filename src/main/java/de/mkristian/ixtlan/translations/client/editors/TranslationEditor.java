package de.mkristian.ixtlan.translations.client.editors;


import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.editors.UserLabel;
import de.mkristian.ixtlan.translations.client.models.Translation;
import de.mkristian.ixtlan.translations.client.models.User;

public class TranslationEditor extends Composite implements Editor<Translation>{
    
    interface Binder extends UiBinder<Widget, TranslationEditor> {}

    private static final Binder BINDER = GWT.create(Binder.class);

    @Ignore @UiField FlowPanel signature;
    @UiField NumberLabel<Integer> translationKeyId;
    @UiField NumberLabel<Integer> localeId;
    @UiField NumberLabel<Integer> domainId;
    @UiField NumberLabel<Integer> appId;
    @UiField DateLabel updatedAt;
    @UiField UserLabel<User> modifiedBy;
    @UiField TextBox text;

    public TranslationEditor() {
        initWidget(BINDER.createAndBindUi(this));
        translationKeyId.setVisible(false);
        localeId.setVisible(false);
        domainId.setVisible(false);
        appId.setVisible(false);
        signature.setVisible(false);
    }

    public void setEnabled(boolean enabled) {
        this.text.setEnabled(enabled);
        this.signature.setVisible(this.updatedAt.getValue() != null);
    }
}
