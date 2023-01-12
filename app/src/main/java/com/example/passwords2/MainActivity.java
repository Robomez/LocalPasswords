/*
https://www.youtube.com/watch?v=Jt-F7OSb_LU&t=639s
Tech Projects youtube
"Biometric Fingerprint Authentication in android studio | How to add fingerprint in android studio" video
Basic fingerprint authentication for android. Lots of deprecated methods. Had to correct the code
and modify it to better suit my application.
*/

package com.example.passwords2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.concurrent.Executor;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    ConstraintLayout activity_main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main_layout = findViewById(R.id.activity_main);

        Button btnResult = findViewById(R.id.btnResult);
        btnResult.setOnClickListener(this);

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(), "No biometric features available on this device.", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(), "Biometric features are currently unavailable.", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                Toast.makeText(getApplicationContext(), "No fingerprint assigned.", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_SUCCESS:
                /* Use biometricPrompt */
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
                activity_main_layout.setVisibility(View.VISIBLE);
                Intent goToResult = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(goToResult);

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Hello")
                .setDescription("Use fingerprint to login").setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK | DEVICE_CREDENTIAL).build();
        biometricPrompt.authenticate(promptInfo);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnResult) {
                Intent goToResult = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(goToResult);
        }
    }
}