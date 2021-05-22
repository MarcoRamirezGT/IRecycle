package com.example.irecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.irecycle.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Intent intent, registerIntent;

    private FirebaseAuth mAuth;

    EditText editTextEmail, editTextPassword;
    TextView textViewForgotPassword;
    Button buttonLogin;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // CLICK ON THE LOGIN BUTTON
        buttonLogin.setOnClickListener(view13 -> {

            final String email = editTextEmail.getText().toString().trim();
            final String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() && password.isEmpty()) {
                editTextEmail.setError(getString(R.string.email_is_required_text));
                editTextPassword.setError(getString(R.string.password_is_required_text));
            }

            if (email.isEmpty()) {
                editTextEmail.setError(getString(R.string.email_is_required_text));
                return;
            }
            if (password.isEmpty()) {
                editTextPassword.setError(getString(R.string.password_is_required_text));
                return;
            }
            if (isEmailValid(email) && !password.isEmpty()) {

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        try {
                            throw Objects.requireNonNull(task.getException());
                        } catch (FirebaseAuthInvalidUserException e) {
                            Toast.makeText(this, "Usuario no existe", Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(this, "La contraseña es incorrecta",  Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                Toast.makeText(this, "Corre electrónico no válido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void onLoginClick(View view){
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
    public void onHomeClick(View view){
        startActivity(new Intent(this,HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}

