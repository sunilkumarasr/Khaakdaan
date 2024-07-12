package com.khak.daan.Config;

import com.khak.daan.ModelAndAdapters.GhazalsModel;

import java.util.List;

public class CommonData {

    private List<GhazalsModel> titleList;
    private static CommonData commonData = null;

    private CommonData() {
    }

    public static CommonData getInstance() {
        if (commonData == null) {
            commonData = new CommonData();
        }
        return commonData;
    }

    public List<GhazalsModel> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<GhazalsModel> titleList) {
        this.titleList = titleList;
    }
}
