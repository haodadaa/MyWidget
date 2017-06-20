package com.hao.radiobuttonrecyclerview.bean;

/**
 * Created by hao on 2017/5/11.
 */

public class ShopBean extends SelectedBean {
    private String shopName;

    public ShopBean(String shopName) {
        this.shopName = shopName;
    }

    public ShopBean(String name, boolean isSelected) {
        this.shopName = name;
        setSelected(isSelected);
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
