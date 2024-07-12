package com.khak.daan.ModelAndAdapters.LikesAdapter;

import java.util.List;

public class LikedChildModel {

    private String title;
    private String cat;
    private boolean active;

    List<LikedChildArrayModel> childArrayModelList;

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

    public LikedChildModel(String title, String cat, boolean active, List<LikedChildArrayModel> childArrayModelList) {
        this.title = title;
        this.cat = cat;
        this.active = active;
        this.childArrayModelList = childArrayModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LikedChildArrayModel> getChildArrayModelList() {
        return childArrayModelList;
    }

    public void setChildArrayModelList(List<LikedChildArrayModel> childArrayModelList) {
        this.childArrayModelList = childArrayModelList;
    }
}
