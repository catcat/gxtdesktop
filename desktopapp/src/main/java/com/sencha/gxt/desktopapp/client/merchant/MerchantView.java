package com.sencha.gxt.desktopapp.client.merchant;



import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.desktopapp.client.filemanager.images.Images;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;

public class MerchantView implements HideEvent.HideHandler , IsWidget {
    protected Window window;
    protected MerchantPresenter merchantPresenter;
    protected Integer externalRatio;
    protected VerticalLayoutContainer verticalLayoutContainer;

    public MerchantView(MerchantPresenter merchantPresenter, Integer externalRatio) {
        this.merchantPresenter = merchantPresenter;
        this.externalRatio = externalRatio;
    }

    public void onHide(HideEvent hideEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Widget asWidget() {
        return getWindow();
    }

    private Window getWindow() {
        if (window == null) {
            window = new Window();
            window.setHeadingText("Merchant");
            window.getHeader().setIcon(Images.getImageResources().folder());
            window.setMinimizable(true);
            window.setMaximizable(true);
            window.setPixelSize(500, 400);
            window.setOnEsc(false);
            window.addHideHandler(this);
            //window.setWidget(getVerticalLayoutContainer());
            window.setWidget(getContainer());
        }
        return window;
    }

    private VerticalLayoutContainer getVerticalLayoutContainer() {
        if (verticalLayoutContainer == null) {
            verticalLayoutContainer = new VerticalLayoutContainer();
            verticalLayoutContainer.setBorders(true);
            //verticalLayoutContainer.add(getFileManagerToolBar(), new VerticalLayoutContainer.VerticalLayoutData(1, -1));
            //verticalLayoutContainer.add(getTreeGrid(), new VerticalLayoutContainer.VerticalLayoutData(1, 1));
            TextButton btn = new TextButton();
            btn.setText("Click me");
            btn.addSelectHandler(new SelectEvent.SelectHandler() {
                public void onSelect(SelectEvent selectEvent) {
                    Info.display("clicked!", "ok");
                }
            });
            verticalLayoutContainer.add(btn);
        }
        return verticalLayoutContainer;
    }

    protected Container getContainer() {
        BorderLayoutContainer container = new BorderLayoutContainer();

        ContentPanel west = new ContentPanel();
        west.setHeadingText("West");

        ContentPanel center = new ContentPanel();
        center.setHeadingText("Center");

        BorderLayoutContainer.BorderLayoutData westData = new BorderLayoutContainer.BorderLayoutData(150);
        BorderLayoutContainer.BorderLayoutData centerData = new BorderLayoutContainer.BorderLayoutData(150);

        centerData.setMargins(new Margins(2, 3, 2, 3));
        westData.setMargins(new Margins(2));

        container.setWestWidget(west, westData);
        container.setCenterWidget(center, centerData);

        return container;
    }
}
