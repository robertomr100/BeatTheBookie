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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText mUser;
    EditText mPassword;
    Button button;
    private FirebaseAuth mAuth;
    DatabaseReference databaseBet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUser=(EditText) findViewById(R.id.RUsernameEdit);
        mPassword=(EditText) findViewById(R.id.RpasswordEdit);
        button=(Button) findViewById(R.id.button);
        mAuth=FirebaseAuth.getInstance();

        databaseBet= FirebaseDatabase.getInstance().getReference();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();

            }
        });

    }

    private void CreateAccount()
    {
        final String email = mUser.getText().toString();
        String password = mPassword.getText().toString();

        if(TextUtils.isEmpty(email))
            Toast.makeText(this,"Enter a email", Toast.LENGTH_SHORT).show();

        if(TextUtils.isEmpty(password))
            Toast.makeText(this,"Enter a password", Toast.LENGTH_SHORT).show();

        else
        {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if(task.isSuccessful())
                    {
                        FirebaseUser Fuser = mAuth.getCurrentUser();
                        User user = new User(Fuser.getUid(),  email, 0);
                        databaseBet.child("Users").child(Fuser.getUid()).setValue(user);

                        Toast.makeText(RegisterActivity.this,"Success!",Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                         startActivity(intent);
                    }

                    else
                    {
                        String message = task.getException().toString();
                        Toast.makeText(RegisterActivity.this,"Error: " + message,Toast.LENGTH_LONG).show();

                    }

                }
            });
        }
    }
}
