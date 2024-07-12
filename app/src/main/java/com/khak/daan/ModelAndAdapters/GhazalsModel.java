package com.khak.daan.ModelAndAdapters;

import java.util.ArrayList;

public class GhazalsModel {

    private String title_top;
    private String title;
    private String cat;
    private ArrayList<String> des;
    private boolean active;


    public GhazalsModel(String title_top, String title, String cat, ArrayList<String> des, boolean active) {
        this.title_top = title_top;
        this.title = title;
        this.cat = cat;
        this.des = des;
        this.active = active;
    }


    public String getTitle_top() {
        return title_top;
    }

    public void setTitle_top(String title_top) {
        this.title_top = title_top;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public ArrayList<String> getDes() {
        return des;
    }

    public void setDes(ArrayList<String> des) {
        this.des = des;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
