package com.sencha.gxt.desktopapp.client.exentity;


import java.util.ArrayList;
import java.util.List;

public class MerchantGenerator {
    static List<Merchant> result = null;
    public static List<Merchant> getMerchants() {
        if(result == null) {
            result = new ArrayList<Merchant>();

            Merchant m;
            m = new Merchant();
            m.setId(190);
            m.setName("cocacola");
            m.setSite("http://cocacola.com");
            m.setChargeAmount(42);
            result.add(m);

            m = new Merchant();
            m.setId(200);
            m.setName("fanta");
            m.setSite("http://fanta.com");
            m.setChargeAmount(242);
            result.add(m);

            m = new Merchant();
            m.setId(300);
            m.setName("sprite");
            m.setSite("http://sprite.com");
            m.setChargeAmount(342);
            result.add(m);
        }
        return result;
    }

    public static void getMerchants(List<Merchant> set) {
        result = set;
    }

}
