package com.devicecosmos.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity {

    private EditText mail,pass;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        mail=(EditText)findViewById(R.id.editMail);
        pass=(EditText)findViewById(R.id.editPass);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
    }

    public void onSignupClick(View view) {
        {
            String a=mail.getText().toString().trim();
            String b=pass.getText().toString().trim();
            String c="@gmail.com";
            String d= "@outlook.com";
            String e = "yahoo.com";
            pass.setText("");
            if(a.isEmpty())
            {
                mail.setError("E-mail is required");
                mail.requestFocus();
                return;
            }
            if(b.isEmpty())
            {
                pass.setError("Password is required");
                pass.requestFocus();
                return;
            }
            if(!a.contains(c) && !a.contains(d) && !a.contains(e))
            {
                mail.setError("Please enter valid email");
                mail.requestFocus();
                return;
            }
            if(b.length()<6)
            {
                pass.setError("Minimum length of password should be 6");
                pass.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(a,b).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    mail.setText("");
                    if(task.isSuccessful())
                    {
                        Intent intents = new Intent(SignUp.this,MainActivity.class);
                        Toast.makeText(getApplicationContext(),"User registered",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(intents);
                    }
                    else
                    {
                        if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        {
                            Toast.makeText(getApplicationContext(),"User already registered",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


        }
    }
}
