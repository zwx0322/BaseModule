package com.smartxin.baselibrary.bean;

public class BtnGroup {

    private int icon ;

    private String name ;

    private int type ;

    private boolean isSelect = false;

    public BtnGroup() {
    }

    public BtnGroup(int icon, String name) {

        this.icon = icon;
        this.name = name;
    }

    public BtnGroup(int icon, String name, int type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }

    public BtnGroup(int icon, String name, boolean isSelect) {
        this.icon = icon;
        this.name = name;
        this.isSelect = isSelect;
    }

    public BtnGroup(int icon, String name, int type, boolean isSelect) {
        this.icon = icon;
        this.name = name;
        this.type = type;
        this.isSelect = isSelect;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
