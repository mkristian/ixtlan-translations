package de.mkristian.ixtlan.translations.client.views;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.views.ModelButton;
import de.mkristian.ixtlan.translations.client.editors.ApplicationEditor;
import de.mkristian.ixtlan.translations.client.editors.TranslationEditor;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.models.Translation;
import de.mkristian.ixtlan.translations.client.models.TranslationKey;
import de.mkristian.ixtlan.translations.client.presenters.ApplicationPresenter;

@Singleton
public class ApplicationViewImpl extends Composite implements ApplicationView {

  @UiTemplate("ApplicationView.ui.xml")
  interface Binder extends UiBinder<Widget, ApplicationViewImpl> {}

  interface EditorDriver extends SimpleBeanEditorDriver<Application, ApplicationEditor> {}

  interface TEditorDriver extends SimpleBeanEditorDriver<Translation, TranslationEditor> {}

  private final static Binder BINDER = GWT.create(Binder.class);

  private final EditorDriver editorDriver = GWT.create(EditorDriver.class);
  private final TEditorDriver tEditorDriver = GWT.create(TEditorDriver.class);

  private ApplicationPresenter presenter;

  @UiField ApplicationEditor editor;
  
  @UiField FlexTable list;
  
  private final Button save = new Button("Save");
  
  private TranslationEditor tEditor = new TranslationEditor();
  
  @Inject
  public ApplicationViewImpl() {
      initWidget(BINDER.createAndBindUi(this));
      editorDriver.initialize(editor);
      tEditorDriver.initialize(tEditor);
      save.addClickHandler(saveClickHandler);
  }

  @Override
  public void setPresenter(ApplicationPresenter presenter){
      this.presenter = presenter;
  }

  @Override
  public void show(Application model){
      reset(model);
      editor.setEnabled(false);
  }

  private final ClickHandler saveClickHandler = new ClickHandler() {
      
      public void onClick(ClickEvent event) {
          presenter.save(tEditorDriver.flush());
      }
  };

  private final ClickHandler clickHandler = new ClickHandler() {
      
      @SuppressWarnings("unchecked")
      public void onClick(ClickEvent event) {
          ModelButton<TranslationKey> button = (ModelButton<TranslationKey>)event.getSource();
          switch(button.action){
              case EDIT: presenter.edit(button.model); break; 
          }
      }
  };

  private Button newButton(RestfulActionEnum action, TranslationKey model){
      ModelButton<TranslationKey> button = new ModelButton<TranslationKey>(action, model);
      button.addClickHandler(clickHandler);
      return button;
  }

  private final Map<Integer, Integer> id2row = new HashMap<Integer, Integer>();

  private TranslationKey current;
  
  private void setRow(int row, TranslationKey model) {
      id2row.put(model.getId(), row);
      list.setText(row, 0, model.getName() + "");
      list.setWidget(row, 1, newButton(RestfulActionEnum.EDIT, model));
  }
  
  @Override
  public void reset(Application model) {
      editorDriver.edit(model);
      resetTranslationKeys(model.getTranslationKeys());
  }
  
  public void resetTranslationKeys(List<TranslationKey> keys){
      list.clear();
      id2row.clear();
      int row = 0;
      for(TranslationKey key: keys){
          setRow(row, key);
          row ++;
      }
  }
  
  @Override
  public void edit(Translation translation){
      if (current != null){
          setRow(id2row.get(current.getId()), current);
      }
      current = translation.getTranslationKey();
      int row = id2row.get(current.getId());
      list.setWidget(row, 0, tEditor);
      list.setWidget(row, 1, save);
      doReset(translation);
  }

  private void doReset(Translation model){
      tEditorDriver.edit(model);
      tEditor.setEnabled(true);
  }
  
  @Override
  public void reset(Translation model){
      if (current != null && current.id == model.getTranslationKeyId()){
          doReset(model);
      }
  }
}
