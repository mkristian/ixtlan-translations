package org.dhamma.translations.client.views;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.dhamma.translations.client.editors.ApplicationEditor;
import org.dhamma.translations.client.models.Application;
import org.dhamma.translations.client.presenters.ApplicationPresenter;

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

@Singleton
public class ApplicationViewImpl extends Composite implements ApplicationView {

  @UiTemplate("ApplicationView.ui.xml")
  interface Binder extends UiBinder<Widget, ApplicationViewImpl> {}

  interface EditorDriver extends SimpleBeanEditorDriver<Application, ApplicationEditor> {}

  private final Binder BINDER = GWT.create(Binder.class);

  private final EditorDriver editorDriver = GWT.create(EditorDriver.class);

  private ApplicationPresenter presenter;

  @UiField ApplicationEditor editor;
  @UiField Button list;

  @Inject
  public ApplicationViewImpl() {
      initWidget(BINDER.createAndBindUi(this));
      editorDriver.initialize(editor);
  }

  @Override
  public void setPresenter(ApplicationPresenter presenter) {
      this.presenter = presenter;
  }

  @Override
  public void show(Application model){
      editorDriver.edit(model);
      editor.setEnabled(false);
  }

  @Override
  public void reset(Application model) {
      editorDriver.edit(model);
  }

  @UiHandler("list")
  void onListClick(ClickEvent event) {
      presenter.listAll();
  }
}
