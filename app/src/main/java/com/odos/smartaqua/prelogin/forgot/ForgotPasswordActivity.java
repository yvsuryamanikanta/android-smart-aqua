package com.odos.smartaqua.prelogin.forgot;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.ActivityForgotBinding;
import com.odos.smartaqua.prelogin.verification.BetterActivityResult;
import com.odos.smartaqua.prelogin.verification.SmsBroadcastReceiver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ForgotPasswordActivity extends Activity {

    private ActivityForgotBinding activityForgotBinding;
    BetterActivityResult<Intent, ActivityResult> intentLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotBinding = DataBindingUtil.setContentView(ForgotPasswordActivity.this, R.layout.activity_forgot);
        activityForgotBinding.setForgotViewModel(new ForgotViewModel(ForgotPasswordActivity.this, activityForgotBinding));
        activityForgotBinding.executePendingBindings();
    }

    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            Log.e("$$$$$$$$$ otp ", " "+message);
            Log.e("$$$$$$$$$ otp code ", " "+matcher.group(0));

            activityForgotBinding.pinView.setText(matcher.group(0));
        }
    }


    SmsBroadcastReceiver smsBroadcastReceiver;

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        intentLauncher.launch(intent, result -> {
                            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                                //That gives all message to us.
                                // We need to get the code from inside with regex
                                String message = result.getData().getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                Log.e("$$$$$$$$$ onact ", " "+message);
                                getOtpFromMessage(message);
                            }
                        });
                    }
                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }
    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(getApplicationContext());
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

}
