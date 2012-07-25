package org.dhamma.translations.client;

import org.dhamma.translations.client.managed.TranslationsMenu;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import de.mkristian.gwt.rails.Application;

public class TranslationsApplication extends Composite implements Application {

    interface Binder extends UiBinder<Widget, TranslationsApplication> {}

    private static Binder BINDER = GWT.create(Binder.class);

    @UiField(provided=true) final SimplePanel display = new ScrollPanel();
    @UiField(provided=true) Panel header;
    @UiField(provided=true) Panel navigation;
    @UiField(provided=true) Panel footer = new SimplePanel();

    @Inject
    TranslationsApplication(ActivityManager activityManager, 
                                      final TranslationsMenu menu, 
                                      final BreadCrumbsPanel breadCrumbs){
        activityManager.setDisplay(display);
        this.navigation = menu;
        this.header = breadCrumbs;
        initWidget(BINDER.createAndBindUi(this));
    }

    @Override
    public void run() {
        LayoutPanel root = RootLayoutPanel.get();
        root.add(this.asWidget());
    }
}
