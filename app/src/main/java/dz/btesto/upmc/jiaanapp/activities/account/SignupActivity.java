package dz.btesto.upmc.jiaanapp.activities.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.activities.MainActivity;

/**
 * -------------------------
 * ### JI3AN APPLICATION ###
 * -------------------------
 * <p>
 * Created by :
 * ------------
 * ++ Nour Elislam SAIDI
 * ++ Mohamed Tayeb BENTERKI
 * <p>
 * ------ 2016-2017 --------
 */
public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputMobile, inputAddress, inputPseudo;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    //private ProgressBar progressBar;
    private FirebaseAuth auth;

    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputAddress = (EditText) findViewById(R.id.address);
        inputMobile = (EditText) findViewById(R.id.mobile);
        inputPseudo = (EditText) findViewById(R.id.pseudo);
        inputPassword = (EditText) findViewById(R.id.password);
        // progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String address = inputAddress.getText().toString().trim();
                final String pseudo = inputPseudo.getText().toString().trim();
                final String mobile = inputMobile.getText().toString().trim();

                Log.i("dddddfff", password);

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                String uID = task.getResult().getUser().getUid();
                                String email = task.getResult().getUser().getEmail();


                                Account account = new Account(email, pseudo, address, mobile, password);
                                mRef.child("users").child(uID).setValue(account);

                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                // progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // progressBar.setVisibility(View.GONE);
    }
}