package com.khak.daan.ModelAndAdapters;

public class Title {

    private String title_top;
    private String title;
    private String cat;
    private boolean active;


    public Title(String title_top,String title, String cat, boolean active) {
        this.title_top = title_top;
        this.title = title;
        this.cat = cat;
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

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
