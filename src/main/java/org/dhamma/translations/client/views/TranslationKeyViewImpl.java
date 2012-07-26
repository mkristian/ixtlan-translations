package org.dhamma.translations.client.views;

import org.dhamma.translations.client.TranslationsConfirmation;
import org.dhamma.translations.client.editors.TranslationKeyEditor;
import org.dhamma.translations.client.models.TranslationKey;
import org.dhamma.translations.client.presenters.TranslationKeyPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TranslationKeyViewImpl extends Composite implements TranslationKeyView {

  @UiTemplate("TranslationKeyView.ui.xml")
  interface Binder extends UiBinder<Widget, TranslationKeyViewImpl> {}

  interface EditorDriver extends SimpleBeanEditorDriver<TranslationKey, TranslationKeyEditor> {}

  private final Binder BINDER = GWT.create(Binder.class);

  private final EditorDriver editorDriver = GWT.create(EditorDriver.class);

  private final TranslationsConfirmation confirmation;  

  private TranslationKeyPresenter presenter;
  private boolean editable = false;
  private boolean dirty = false;

  @UiField TranslationKeyEditor editor;
  @UiField Button list;

  @Inject
  public TranslationKeyViewImpl(TranslationsConfirmation confirmation) {
      this.confirmation = confirmation;
      initWidget(BINDER.createAndBindUi(this));
      editorDriver.initialize(editor);
  }

  @Override
  public void setPresenter(TranslationKeyPresenter presenter) {
      this.presenter = presenter;
  }

  @Override
  public void show(TranslationKey model){
      editable = false;
      editorDriver.edit(model);
      editor.setEnabled(false);
  }

  @UiHandler("list")
  void onListClick(ClickEvent event) {
      initDirty();
      presenter.listAll();
  }

  private void initDirty(){
      dirty = editable && (editorDriver == null ? false : editorDriver.isDirty());
  }

  @Override
  public boolean isDirty() {
      return dirty;
  }
}
