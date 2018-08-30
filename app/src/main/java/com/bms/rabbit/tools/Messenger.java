package com.bms.rabbit.tools;
// Created by Konstantin on 30.08.2018.

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Objects;

public class Messenger {
    private Context context;

    public Messenger(Context context) {
        this.context = context;
    }

    public void showSystemMessage(String messageText) {
        if (messageText != null && !Objects.equals(messageText, "")) {
            if(context!=null) {
                Toast toast = new Toast(context);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                Toast.makeText(context, messageText, Toast.LENGTH_LONG).show();
            }
        }
    }
}
