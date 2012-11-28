package de.mkristian.ixtlan.translations.client.views;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.views.ModelButton;
import de.mkristian.ixtlan.translations.client.editors.ApplicationEditor;
import de.mkristian.ixtlan.translations.client.editors.TranslationEditor;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.models.Domain;
import de.mkristian.ixtlan.translations.client.models.Locale;
import de.mkristian.ixtlan.translations.client.models.Translation;
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
  
  @UiField TextBox filter;
  
  @UiField ListBox locales;
  
  @UiField ListBox domains;
  
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

  private final ClickHandler saveClickHandler = new ClickHandler() {
      
      public void onClick(ClickEvent event) {
          presenter.save(tEditorDriver.flush());
      }
  };

  private final ClickHandler clickHandler = new ClickHandler() {
      
      @SuppressWarnings("unchecked")
      public void onClick(ClickEvent event) {
          ModelButton<Translation> button = (ModelButton<Translation>)event.getSource();
          switch(button.action){
              case EDIT: presenter.edit(button.model); break; 
          }
      }
  };

  private Button newButton(RestfulActionEnum action, Translation model){
      ModelButton<Translation> button = new ModelButton<Translation>(action, model);
      button.addClickHandler(clickHandler);
      return button;
  }

  private final Map<Integer, Integer> id2row = new HashMap<Integer, Integer>();

  private Translation current;
  
  private void setRow(int row, Translation model) {
      id2row.put(model.getTranslationKeyId(), row);
      list.setText(row, 0, model.getText());
      list.setWidget(row, 1, newButton(RestfulActionEnum.EDIT, model));
  }
  
  @Override
  public void reset(Application model) {
      editorDriver.edit(model);
      locales.clear();
      for(Locale locale: model.getLocales()){
          locales.addItem(locale.getCode(), locale.getId() + "");
      }
      domains.clear();
      domains.addItem("DEFAULT", Domain.NONE.getId() + "");
      for(Domain domain: model.getDomains()){
          domains.addItem(domain.getName(), domain.getId() + "");
      }
      list.clear();
      id2row.clear();
  }
  
  @UiHandler({"locales", "domains"})
  void changeHandler(ChangeEvent event){
      presenter.filter(filter.getText(), localeId(), domainId());
  }

  @UiHandler({"filter"})
  void keyUpHandler(KeyUpEvent event){
      presenter.filter(filter.getText(), localeId(), domainId());
  }
  
  @Override
  public void reset(Iterable<Translation> trans){
      int row = 0;
      list.removeAllRows();
      for(Translation t: trans){
          setRow(row, t);
          row ++;
      }
  }

  private int localeId(){
      int i = locales.getSelectedIndex();
      return i > -1 ? Integer.parseInt(locales.getValue(i)) : 0;
  }
  
  private int domainId(){
      int i = domains.getSelectedIndex();
      return i > -1 ? Integer.parseInt(domains.getValue(i)) : 0;
  }
  
  @Override
  public void edit(Translation translation){
      if (current != null){
          setRow(id2row.get(current.getTranslationKeyId()), current);
      }
      current = translation;
      int row = id2row.get(current.getTranslationKeyId());
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
      if (current != null && current.getTranslationKeyId() == model.getTranslationKeyId()){
          doReset(model);
      }
  }
}
