package com.example.tyler.a34_auth_and_image_upload;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN";

    private FirebaseAuth mAuth;

    @BindView(R.id.email)
    TextView mEmail;
    @BindView(R.id.password)
    TextView mPassword;

    @BindView(R.id.signin)
    Button mSignin;
    @BindView(R.id.signinAnonymously)
    Button mSigninAnonymously;

    @BindView(R.id.signedInAs)
    TextView mSignedInAs;
    @BindView(R.id.usernameInfo)
    TextView mUsernameInfo;

    @BindView(R.id.proceedToFeed)
    Button mProceedToFeed;
    @BindView(R.id.logout)
    Button mLogout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }

    public void updateUI(FirebaseUser user) {
        if(user != null && user.getUid() != null){
            if(user.getEmail() != null) {
                mUsernameInfo.setText(user.getEmail());
            } else {
                mUsernameInfo.setText("Anonymous " + user.getUid());
            }
            showLogout();
        } else {
            mUsernameInfo.setText(R.string.must_be_logged_in);
            showLogin();
        }
    }

    public void showLogin(){
        mEmail.setVisibility(View.VISIBLE);
        mPassword.setVisibility(View.VISIBLE);
        mSignin.setVisibility(View.VISIBLE);
        mSigninAnonymously.setVisibility(View.VISIBLE);

        mSignedInAs.setVisibility(View.GONE);
        mUsernameInfo.setVisibility(View.GONE);
        mProceedToFeed.setVisibility(View.GONE);
        mLogout.setVisibility(View.GONE);


    }

    public void showLogout(){
        mEmail.setVisibility(View.GONE);
        mPassword.setVisibility(View.GONE);
        mSignin.setVisibility(View.GONE);
        mSigninAnonymously.setVisibility(View.GONE);

        mSignedInAs.setVisibility(View.VISIBLE);
        mUsernameInfo.setVisibility(View.VISIBLE);
        mProceedToFeed.setVisibility(View.VISIBLE);
        mLogout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.signin)
    public void signin() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    @OnClick(R.id.signinAnonymously)
    public void anonymousSignIn() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                });
    }

    @OnClick(R.id.proceedToFeed)
    public void setProceedToFeed() {
        Intent intent = new Intent(this, Feed.class);
        startActivity(intent);
    }

    @OnClick(R.id.logout)
    public void logout() {
        mAuth.signOut();
        updateUI(null);
    }

}
