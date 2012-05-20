package com.sencha.gxt.desktopapp.client.exentity;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface MerchantProperties extends PropertyAccess<Merchant>{
    ModelKeyProvider<Merchant> id();
    ValueProvider<Merchant, String> name();
    ValueProvider<Merchant, String> site();
    ValueProvider<Merchant, Integer> chargeAmount();
}

/*
   protected int id;
    protected String name;
    protected String site;
    protected Integer chargeAmount;
*/