package org.dhamma.translations.client.views;

import org.dhamma.translations.client.models.TranslationKey;
import org.dhamma.translations.client.presenters.TranslationKeyPresenter;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
 
import static de.mkristian.gwt.rails.places.RestfulActionEnum.SHOW;

import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.views.ModelButton;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TranslationKeyListViewImpl extends Composite implements TranslationKeyListView {

    @UiTemplate("TranslationKeyListView.ui.xml")
    interface Binder extends UiBinder<Widget, TranslationKeyListViewImpl> {}

    private static Binder BINDER = GWT.create(Binder.class);

    private TranslationKeyPresenter presenter;

    @UiField FlexTable list;

    @Inject
    public TranslationKeyListViewImpl() {
        initWidget(BINDER.createAndBindUi(this));
    }

    @Override
    public void setPresenter(TranslationKeyPresenter presenter) {
        this.presenter = presenter;
    }
    
    private final ClickHandler clickHandler = new ClickHandler() {
        
        @SuppressWarnings("unchecked")
        public void onClick(ClickEvent event) {
            ModelButton<TranslationKey> button = (ModelButton<TranslationKey>)event.getSource();
            switch(button.action){
                case SHOW: presenter.show(button.model.id); break; 
            }
        }
    };
 
    private Button newButton(RestfulActionEnum action, TranslationKey model){
        ModelButton<TranslationKey> button = new ModelButton<TranslationKey>(action, model);
        button.addClickHandler(clickHandler);
        return button;
    }

    @Override
    public void reset(List<TranslationKey> models) {
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Name");
        list.getRowFormatter().addStyleName(0, "gwt-rails-model-list-header");
        if (models != null) {
            int row = 1;
            for(TranslationKey model: models){
                setRow(row, model);
                row++;
            }
        }
    }

    private void setRow(int row, TranslationKey model) {
        list.setText(row, 0, model.getId() + "");
        list.setText(row, 1, model.getName() + "");

        list.setWidget(row, 2, newButton(SHOW, model));
    }
}
