//package com.bms.rabbit.bullshit;
//// Created by Konstantin on 24.08.2018.
//
//
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.BottomSheetDialogFragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.bms.rabbit.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.Random;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//import static com.bms.rabbit.bullshit.AppConstants.haveMail;
//
//public class GetMailDialogFragment extends BottomSheetDialogFragment {
//    private Dialog dialog;
//    private SharedPreferences sharedPref;
//
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    public void setupDialog(final Dialog dialog, int style) {
//        super.setupDialog(dialog, style);
//        this.dialog = dialog;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.dialog_mail, container, false);
//        if (getActivity() != null) {
//            final EditText editText = view.findViewById(R.id.editText228);
//            final Button button = view.findViewById(R.id.button228);
//            final ProgressBar progressBar = view.findViewById(R.id.progressBar2);
//            progressBar.setVisibility(View.GONE);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String mail = editText.getText().toString();
//
//                    if (isValidEmailAddress(mail)) {
//                        progressBar.setVisibility(View.VISIBLE);
//                        button.setVisibility(View.GONE);
//
//                        Log.d("2228", "start loading");
//
//                        final ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
//
//                        exec.schedule(new Runnable() {
//                            public void run() {
//                                finishDialog();
//                            }
//                        }, 4, TimeUnit.SECONDS);
//
//                        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference myRef = database.getReference("learn-rabbit-ac350");
//                        myRef.child("users").child(String.valueOf(new Random().nextLong())).setValue(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                exec.shutdown();
//                                finishDialog();
//                            }
//                        });
//
//
//                    } else {
//                        Toast.makeText(getContext(), "Неправильный адрес", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//        return view;
//    }
//
//    private void finishDialog() {
//        if (sharedPref != null) {
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putBoolean(haveMail, true);
//            editor.apply();
//        }
//
////        ((MainActivity) getActivity()).doBillingMagic();
//
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//        Log.d("2228", "DocumentSnapshot added with ID: ");
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        if (getActivity() != null) {
//            sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_name), Context.MODE_PRIVATE);
//        }
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    private boolean isValidEmailAddress(String email) {
//        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
//        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
//        java.util.regex.Matcher m = p.matcher(email);
//        return m.matches();
//    }
//
//}