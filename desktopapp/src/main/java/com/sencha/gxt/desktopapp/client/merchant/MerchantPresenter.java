package com.sencha.gxt.desktopapp.client.merchant;

import com.google.gwt.user.client.ui.HasWidgets;
import com.sencha.gxt.desktopapp.client.Presenter;
import com.sencha.gxt.desktopapp.client.filemanager.FileManagerView;
import com.sencha.gxt.widget.core.client.info.Info;

public class MerchantPresenter implements Presenter {

    private int settingsRatio = 142;
    private MerchantView merchantView;

    public MerchantPresenter(int settingsRatio) {
        Info.display("construct", "construct");
        this.settingsRatio = settingsRatio;
    }

    public void go(HasWidgets hasWidgets) {
        Info.display("go", "go");
        hasWidgets.add(getMerchantView().asWidget());
    }

    private MerchantView getMerchantView() {
        if (merchantView == null) {
            merchantView = new MerchantView(this, settingsRatio);
        }
        return merchantView;
    }
}
