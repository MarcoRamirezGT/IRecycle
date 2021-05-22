package com.example.irecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.irecycle.databinding.ActivityRegisterBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    Intent intent;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    Button buttonRegister;
    EditText editTextEmail, editTextPassword;
    DialogLoading loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        buttonRegister = findViewById(R.id.buttonRegister);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        loadingDialog = new DialogLoading(RegisterActivity.this);

        buttonRegister.setOnClickListener(view1 -> {

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

                buttonRegister.setVisibility(View.INVISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();


                        // AFTER ALL DEFAULT PROJECTS ARE CREATED THEN GO TO MAIN ACTIVITY
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String error = Objects.requireNonNull(task.getException()).toString();
                        Log.e("ErrorRegister", error);
                        Toast.makeText(RegisterActivity.this, error , Toast.LENGTH_SHORT).show();
                        buttonRegister.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                Toast.makeText(this, "Corre electrónico no válido", Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonGoogle.setOnClickListener(view12 -> {
            loadingDialog.startLoadingDialog();
            signIn();
        });


        changeStatusBarColor();
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void createRequest() {

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    private void signIn() {
        // Clearing previous sign in caches
        mGoogleSignInClient.signOut();

        // Getting the Google sign in intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null)
                    firebaseAuthWithGoogle(account.getIdToken());
                else
                    Toast.makeText(this, getString(R.string.error_sign_in_text) + 537, Toast.LENGTH_SHORT).show();

            } catch (ApiException e) {
                Log.e("LogInError", Objects.requireNonNull(e.getMessage()));
                Toast.makeText(this, getString(R.string.error_sign_in_text) + 538, Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }
        } else {
            Toast.makeText(this, "Request code invalid: " + requestCode, Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        // CHECK IF USER IS NEW OR ALREADY EXISTS
                        boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();

                        if (isNew) {
                            // GET USER JUST CREATED
                            FirebaseUser user = mAuth.getCurrentUser();

                            // REFERENCE TO THE PROJECT TREE
                            reference = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Projects");

                        }

                        // USER ALREADY EXISTS - CONTINUE TO LOGIN
                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.error_sign_in_text) + 539, Toast.LENGTH_SHORT).show();
                    }
                    loadingDialog.dismissDialog();
                });
    }


    public void changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public void onHomeClick(View view){
        startActivity(new Intent(this,HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}