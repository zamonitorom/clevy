package com.bms.rabbit.entities;
// Created by Konstantin on 01.08.2018.


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorMessage {

    @SerializedName(value="group_code"/*, alternate={"name", "user"}*/)
    @Expose
    private List<String> groupCode = null;

    public List<String> getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(List<String> groupCode) {
        this.groupCode = groupCode;
    }


}
