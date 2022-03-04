package com.example.firebasenotif;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();
    Context mContext;
    private String android_id = "";
    EditText et_DeviceId, et_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        android_id = Secure.getString(mContext.getContentResolver(), Secure.ANDROID_ID);

        et_DeviceId = findViewById(R.id.et_DeviceId);
        et_token = findViewById(R.id.et_token);

        et_DeviceId.setText(android_id);
        getCurrentToken();
    }

    private void getCurrentToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        et_token.setText(token);

                        Log.e(TAG, "currToken: " + token);
                    }
                });
    }
}