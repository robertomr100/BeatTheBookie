package edu.fsu.cs.mobile.beatthebookie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LeaderboardActivity extends AppCompatActivity {
    private ListView list;
    private Button button;
    private Button Refresh;
    private  CustomAdapter mAdapter;
    private Spinner mSpinner;
    DatabaseReference databaseBet;
    SimpleDateFormat dateFormat;
    Date date;
    int querypos;
    Query query;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        querypos=getIntent().getExtras().getInt("q");

        dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        databaseBet = FirebaseDatabase.getInstance().getReference("bets");
        list = (ListView) findViewById(R.id.listview);
        button=(Button) findViewById(R.id.button2);
        mAdapter = new CustomAdapter(LeaderboardActivity.this, R.layout.row);
        mSpinner=(Spinner)findViewById(R.id.spinner);
        Refresh=(Button) findViewById(R.id.button3);
        list.setAdapter(mAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LeaderboardActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LeaderboardActivity.this, LeaderboardActivity.class);
                intent.putExtra("q",querypos);
                startActivity(intent);
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                if(position==0)
                {
                    //sort by votes
                    querypos=0;

                }

                else if(position==1)
                {
                    //sort by earliest
                    querypos=1;
                }

                else
                {
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),
                        "You didn't select anything" ,
                        Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        doQuery();

    }

    private void doQuery() {
        if(querypos==1)
            query=databaseBet.orderByChild("votes");
        else
            query=databaseBet;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //have to clear the listview
                for(DataSnapshot betSnapshot: dataSnapshot.getChildren())
                {
                    Bet bet=betSnapshot.getValue(Bet.class);
                    try {
                        date =dateFormat.parse(bet.getValidUntil());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date currentTime= Calendar.getInstance().getTime();

                    if(date.after(currentTime))
                        mAdapter.add(bet);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}