package com.bms.rabbit.binding;
// Created by Konstantin on 30.08.2018.

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.bms.rabbit.tools.StringContainer;

public class EditTextBinding {
    @BindingAdapter({"bindError", "errorMessage"})
    public static void bindError(TextInputLayout view, Boolean isError, String message) {
        view.setErrorEnabled(isError);
        if (isError) {
            view.setError(message);
        }
    }

    @BindingAdapter({"bindingText"})
    public static void bindEditText(EditText view,final StringContainer observableString) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                observableString.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        String newValue = observableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }

    }

    @BindingConversion
    public static String convertObservableStringToString(StringContainer observableString) {
        return observableString.get();
    }
}
