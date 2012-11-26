package de.mkristian.ixtlan.translations.client.views;

import static de.mkristian.gwt.rails.places.RestfulActionEnum.SHOW;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import de.mkristian.gwt.rails.places.RestfulActionEnum;
import de.mkristian.gwt.rails.views.ModelButton;
import de.mkristian.ixtlan.translations.client.models.Application;
import de.mkristian.ixtlan.translations.client.places.ApplicationPlace;

@Singleton
public class ApplicationListViewImpl extends Composite implements ApplicationListView {

    @UiTemplate("ApplicationListView.ui.xml")
    interface Binder extends UiBinder<Widget, ApplicationListViewImpl> {}

    private static Binder BINDER = GWT.create(Binder.class);

    private final PlaceController places;

    @UiField FlexTable list;

    @Inject
    public ApplicationListViewImpl(PlaceController places) {
        initWidget(BINDER.createAndBindUi(this));
        this.places = places;
    }
    
    private final ClickHandler clickHandler = new ClickHandler() {
        
        @SuppressWarnings("unchecked")
        public void onClick(ClickEvent event) {
            ModelButton<Application> button = (ModelButton<Application>)event.getSource();
            places.goTo(new ApplicationPlace(button.model.getId(), button.action)); 
        }
    };
 
    private Button newButton(RestfulActionEnum action, Application model){
        ModelButton<Application> button = new ModelButton<Application>(action, model);
        button.addClickHandler(clickHandler);
        return button;
    }

    @Override
    public void reset(List<Application> models) {
        list.removeAllRows();
        list.setText(0, 0, "Id");
        list.setText(0, 1, "Name");
        list.getRowFormatter().addStyleName(0, "gwt-rails-model-listButton-header");
        if (models != null) {
            int row = 1;
            for(Application model: models){
                setRow(row, model);
                row++;
            }
        }
    }

    private void setRow(int row, Application model) {
        list.setText(row, 0, model.getId() + "");
        list.setText(row, 1, model.getName() + "");

        list.setWidget(row, 4, newButton(SHOW, model));
    }
}
