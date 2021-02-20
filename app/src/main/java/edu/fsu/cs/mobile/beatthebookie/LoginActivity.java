package edu.fsu.cs.mobile.beatthebookie;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText EditUser;
    EditText EditPassword;
    Button mLoginButton;
    Button mRegisterButton;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditUser=(EditText) findViewById(R.id.LUsernameEdit);
        EditPassword=(EditText) findViewById(R.id.LPasswordEdit);
        mLoginButton=(Button) findViewById(R.id.LoginButton);
        mRegisterButton=(Button) findViewById(R.id.ConfirmEdit);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = EditUser.getText().toString();
                String password = EditPassword.getText().toString();
                if(TextUtils.isEmpty(email))
                    Toast.makeText(LoginActivity.this,"Enter a email", Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(password))
                    Toast.makeText(LoginActivity.this,"Enter a password", Toast.LENGTH_SHORT).show();

                else
                {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this,"Success!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }

                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this,"Error: " + message,Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser!=null)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }
}
