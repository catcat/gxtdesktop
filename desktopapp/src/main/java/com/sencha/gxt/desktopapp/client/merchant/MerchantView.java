package com.sencha.gxt.desktopapp.client.merchant;



import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.desktopapp.client.exentity.Merchant;
import com.sencha.gxt.desktopapp.client.exentity.MerchantGenerator;
import com.sencha.gxt.desktopapp.client.exentity.MerchantProperties;
import com.sencha.gxt.desktopapp.client.filemanager.images.Images;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.ArrayList;
import java.util.List;

public class MerchantView implements HideEvent.HideHandler , IsWidget {
    protected Window window;
    protected MerchantPresenter merchantPresenter;
    protected Integer externalRatio;
    protected VerticalLayoutContainer verticalLayoutContainer;



    @Editor.Ignore
    MerchantEditor merchantEditor;

    Grid grid;

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
        
        addWestContent(west);
        addCenterContent(center);

        container.setWestWidget(west, westData);
        container.setCenterWidget(center, centerData);

        return container;
    }

    /*
    interface ListDriver extends SimpleBeanEditorDriver<StockExchange, GridBindingExample> {
    }
    */

    interface MerchantDriver extends SimpleBeanEditorDriver<Merchant, MerchantEditor> {
    }

    /*
    private ListDriver driver = GWT.create(ListDriver.class);
    */

    private MerchantDriver itemDriver = GWT.create(MerchantDriver.class);


    protected void addWestContent(Container parent) {

        final MerchantProperties props = GWT.create(MerchantProperties.class);


        List<ColumnConfig<Merchant, ?>> columns = new ArrayList<ColumnConfig<Merchant, ?>>();
        //columns.add(new ColumnConfig<Merchant, Integer>(props.id(), 43, "Id"));
        columns.add(new ColumnConfig<Merchant, String>(props.name(), 65, "Name"));
        //columns.add(new ColumnConfig<Merchant, Integer>(props.chargeAmount(), 70, "CA"));
        ColumnConfig<Merchant, Integer> columnConfigCA =new ColumnConfig<Merchant, Integer>(props.chargeAmount(), 70, "CA");
        columns.add(columnConfigCA);
        columnConfigCA.setCell(new AbstractCell<Integer>() {
            @Override
            public void render(Context context, Integer value, SafeHtmlBuilder sb) {
                sb.appendHtmlConstant(value+" "+(value>100?
                        "<span style=\"color:green\">good</span>":"bad"));
            }
        });

        ListStore<Merchant> listStore = new ListStore<Merchant>(props.id());
        listStore.addAll(MerchantGenerator.getMerchants());

        grid = new Grid<Merchant>(
                listStore,
                new ColumnModel<Merchant>(columns));
        grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
        grid.setBorders(true);

        final ListStoreEditor<Merchant> storeEditor = new ListStoreEditor<Merchant>(grid.getStore());

        grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<Merchant>() {
            public void onSelectionChanged(SelectionChangedEvent<Merchant> event) {
                if (event.getSelection().size() > 0) {
                    edit(event.getSelection().get(0));
                } else {
                    merchantEditor.setSaveEnabled(false);
                }
            }
        });

        parent.add(grid);
        
    }

    protected void edit(Merchant item) {
        merchantEditor.setSaveEnabled(true);
        itemDriver.edit(item);
    }

    protected void addCenterContent(Container parent) {
        merchantEditor = new MerchantEditor();
        
        itemDriver.initialize(merchantEditor);

        merchantEditor.getSaveButton().addSelectHandler(new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent selectEvent) {
                saveCurrentMerchant();
            }
        });

        //parent.add(merchantEditor);
        
        TextButton addIt = new TextButton("AddIt");
        addIt.addSelectHandler(new SelectEvent.SelectHandler() {
            public void onSelect(SelectEvent selectEvent) {
                Merchant toAdd = new Merchant();
                toAdd.setId((int)Math.ceil(Math.random()*1000.));
                toAdd.setName("new"+System.currentTimeMillis());
                toAdd.setSite("http://" + System.currentTimeMillis() + ".com");
                toAdd.setChargeAmount((int) Math.ceil(Math.random() * 1000.));
                grid.getStore().add(toAdd);

                itemDriver.edit(toAdd);



            }
        });
        //parent.add(addIt);

        FlowPanel p = new FlowPanel();
        p.add(merchantEditor);
        p.add(addIt);
        parent.add(p);

    }

    protected void saveCurrentMerchant() {
        Merchant edited = itemDriver.flush();
        if (!itemDriver.hasErrors()) {
            merchantEditor.setSaveEnabled(false);

            grid.getStore().update(edited);
            itemDriver.edit(edited);
        }
    }
}

class MerchantEditor implements IsWidget, Editor<Merchant> {

    private FormPanel panel;
    private TextButton save;

    TextField name;
    TextField site;
    NumberField<Integer> chargeAmount;

    MerchantEditor() {
        panel = new FormPanel();
        panel.setLabelWidth(50);

        name = new TextField();
        site = new TextField();
        RegExValidator http = new RegExValidator("http://.+\\..+", "Please supply a valid url");
        site.addValidator(http);
        chargeAmount = new NumberField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());

        name.setAllowBlank(false);
        site.setAllowBlank(false);
        chargeAmount.setAllowBlank(false);

        Container container = new VerticalLayoutContainer();//???
        panel.setWidget(container);

        container.add(new FieldLabel(name, "Name"));
        container.add(new FieldLabel(chargeAmount, "Ca"));
        container.add(new FieldLabel(site, "Site"));

        save = new TextButton("Save");
        save.setEnabled(false);
        container.add(save);

        panel.setLabelWidth(50);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    public void setSaveEnabled(boolean enabled) {
        save.setEnabled(enabled);
        if (!enabled) {
            name.setValue("");
            chargeAmount.setValue(null);
        }
    }

    public SelectEvent.HasSelectHandlers getSaveButton() {
        return save;
    }
}