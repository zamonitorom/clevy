package com.bms.rabbit;
// Created by Konstantin on 24.08.2018.


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.bms.rabbit.AppConstants.haveMail;

public class GetMailDialogFragment extends BottomSheetDialogFragment {
    private Dialog dialog;
    private SharedPreferences sharedPref;


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        this.dialog = dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_mail, container, false);
        if (getActivity() != null) {
            final EditText editText = view.findViewById(R.id.editText228);
            final Button button = view.findViewById(R.id.button228);
            final ProgressBar progressBar = view.findViewById(R.id.progressBar2);
            progressBar.setVisibility(View.GONE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mail = editText.getText().toString();

                    if(isValidEmailAddress(mail)) {

                        progressBar.setVisibility(View.VISIBLE);
                        button.setVisibility(View.GONE);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();


                        Map<String, Object> user = new HashMap<>();
                        user.put("mail", mail);

                        Log.d("2228", "start loading");

// Add a new document with a generated ID
                        db.collection("users")
                                .add(user);

//                        ScheduledExecutorService worker =
//                                Executors.newSingleThreadScheduledExecutor();
//                        worker.schedule(new Runnable() {
//                            @Override
//                            public void run() {
                                button.setVisibility(View.VISIBLE);

                                if(sharedPref!=null){
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putBoolean(haveMail, true);
                                    editor.apply();
                                }

                                ((MainActivity)getActivity()).doBillingMagic();

                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                                Log.d("2228", "DocumentSnapshot added with ID: ");
//                            }
//                        },2, TimeUnit.SECONDS);
//                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                    @Override
//                                    public void onSuccess(DocumentReference documentReference) {
//                                        progressBar.setVisibility(View.GONE);
//                                        button.setVisibility(View.VISIBLE);
//
//                                        if(sharedPref!=null){
//                                            SharedPreferences.Editor editor = sharedPref.edit();
//                                            editor.putBoolean(haveMail, true);
//                                            editor.apply();
//                                        }
//
//                                        ((MainActivity)getActivity()).doBillingMagic();
//
//                                        if (dialog != null) {
//                                            dialog.dismiss();
//                                        }
//                                        Log.d("2228", "DocumentSnapshot added with ID: " + documentReference.getId());
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        button.setVisibility(View.VISIBLE);
//                                        progressBar.setVisibility(View.GONE);
//                                        Crashlytics.logException(e);
//                                        if (dialog != null) {
//                                            dialog.dismiss();
//                                        }
//                                        Log.d("2228", "Error adding document", e);
//                                    }
//                                });



                    }else {
                        Toast.makeText(getContext(), "Неправильный адрес",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity()!=null) {
            sharedPref = getActivity().getSharedPreferences(
                    getString(R.string.preference_name), Context.MODE_PRIVATE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}