package com.khak.daan.ModelAndAdapters.LikesAdapter;

public class LikedChildArrayModel {

    private String title;
    private String cat;
    private boolean active;


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public LikedChildArrayModel(String title, String cat, boolean active) {
        this.title = title;
        this.cat = cat;
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

}
