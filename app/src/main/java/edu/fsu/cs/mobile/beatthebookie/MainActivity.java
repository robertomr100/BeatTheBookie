package edu.fsu.cs.mobile.beatthebookie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button createButton;
    private Button leaderboardButton;
    private TextView textView;
    private FirebaseUser currentUser;
    private FirebaseAuth auth;
    private String UserStr;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseUser = FirebaseDatabase.getInstance().getReference("Users");
        createButton = (Button) findViewById(R.id.CreateBetButton);
        leaderboardButton=(Button)findViewById(R.id.LeaderboardButton);
        textView=(TextView)findViewById(R.id.TextViewMain);
        textView.setText("Welcome what would you like to do?");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateBetActivity.class);
                startActivity(intent);
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                intent.putExtra("q",0);
                startActivity(intent);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser == null) {
            Intent intent =   new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else
        {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionsmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Options:

                break;
            case R.id.Signout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText( this, "Goodbye!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }


}
