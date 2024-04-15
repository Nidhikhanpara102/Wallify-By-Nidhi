package com.android.nidhiandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonSignup, buttonAlreadyUserLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignup = findViewById(R.id.buttonSignup);
        buttonAlreadyUserLogin = findViewById(R.id.buttonAlreadyUserLogin);

        ImageView imageViewLogo = findViewById(R.id.imageViewLogo);

        TextView textWelcome = findViewById(R.id.textWelcomeMessage);
        TextView textAdditional = findViewById(R.id.textAdditionalMessage);

        // Add animation
        Animation animationWelcome = new AlphaAnimation(0.0f, 1.0f);
        animationWelcome.setDuration(1000);
        textWelcome.startAnimation(animationWelcome);

        Animation animationAdditional = new AlphaAnimation(0.0f, 1.0f);
        animationAdditional.setDuration(1000);
        animationAdditional.setStartOffset(500);
        textAdditional.startAnimation(animationAdditional);

        Animation animationLogo = new AlphaAnimation(0.0f, 1.0f);
        animationLogo.setDuration(1000);
        animationLogo.setStartOffset(1000);
        imageViewLogo.startAnimation(animationLogo);


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Check if email or password fields are empty
                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError(getString(R.string.email_required));
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError(getString(R.string.password_required));
                    return;
                }

                // Proceed with Firebase authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, R.string.signup_successful, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    // Close SignupActivity
                                    finish();
                                } else {
                                    Toast.makeText(SignupActivity.this, getString(R.string.signup_failed, task.getException().getMessage()), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Button to go to login page if user already exist
        buttonAlreadyUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
