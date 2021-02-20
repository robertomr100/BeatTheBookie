package edu.fsu.cs.mobile.beatthebookie;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateBetActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    EditText mBet;
    EditText mPayout;
    EditText mWebsite;
    Button button;
    Button DateButton;
    FirebaseAuth mAuth;
    DatabaseReference databaseBet;
    DatabaseReference databaseUser;
    String Creator;
    String UID;
    String ValidStr;
    SimpleDateFormat dateFormat;
    Date Valid;
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bet);
        mBet=(EditText) findViewById(R.id.BetEdit);
        mPayout=(EditText) findViewById(R.id.PayoutEdit);
        mWebsite=(EditText) findViewById(R.id.WebsiteEdit);
        button=(Button) findViewById(R.id.AddBetButton);
        DateButton=(Button) findViewById(R.id.DateButton);
        mAuth=FirebaseAuth.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        DateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String ID= mAuth.getCurrentUser().getUid();
                Toast.makeText(CreateBetActivity.this,ID,Toast.LENGTH_LONG).show();
            }
        });

        DateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c= Calendar.getInstance();
                year=c.get(Calendar.YEAR);
                month=c.get(Calendar.MONTH);
                day=c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateBetActivity.this,CreateBetActivity.this, year, month, day);
                datePickerDialog.show();



            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bet = mBet.getText().toString();
                final String payout =mPayout.getText().toString();
                final String website= mWebsite.getText().toString();

                databaseBet=FirebaseDatabase.getInstance().getReference("bets");
                databaseUser=FirebaseDatabase.getInstance().getReference("Users");


                if(!TextUtils.isEmpty(bet)){
                    final String id=databaseBet.push().getKey();
                    UID = mAuth.getCurrentUser().getUid();

                    databaseUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot betSnapshot: dataSnapshot.getChildren())
                            {
                                User user1=betSnapshot.getValue(User.class);
                                if(user1.getID().equals(UID)) {
                                 Creator = user1.getEmail();
                                    Bet bet1= new Bet(id,bet,Double.valueOf(payout),website,Creator,ValidStr);
                                    bet1.setCreator(Creator);
                                    bet1.setCreatorID(UID);
                                    databaseBet.child(id).setValue(bet1);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    Bet bet1= new Bet(id,bet,Double.valueOf(payout),website,Creator, ValidStr);
                    bet1.setCreator(Creator);
                    databaseBet.child(id).setValue(bet1);

                    Intent intent= new Intent(CreateBetActivity.this,LeaderboardActivity.class);

                    intent.putExtra("q",0);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(CreateBetActivity.this, "Enter a bet",  Toast.LENGTH_LONG).show();
                }

                if(!TextUtils.isEmpty(payout)){

                }
                else
                {
                    Toast.makeText(CreateBetActivity.this, "Enter a payout",  Toast.LENGTH_LONG).show();
                }


                if(!TextUtils.isEmpty(website)){

                }
                else
                {
                    Toast.makeText(CreateBetActivity.this, "Enter a website",  Toast.LENGTH_LONG).show();
                }



            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal=i;
        monthFinal= i1+1;
        dayFinal= i2;

        Calendar c=Calendar.getInstance();
        hour= c.get(Calendar.HOUR_OF_DAY);
        minute= c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateBetActivity.this, CreateBetActivity.this,hour,minute, DateFormat.is24HourFormat(CreateBetActivity.this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal=i;
        minuteFinal=i1;
        Valid= new GregorianCalendar(yearFinal,monthFinal,dayFinal,hourFinal,minuteFinal).getTime();

        ValidStr=dateFormat.format(Valid);
        String syear=Integer.toString(yearFinal);
        String smonth=Integer.toString(monthFinal);
        String sday=Integer.toString(dayFinal);
        String shour=Integer.toString(hourFinal);
        String sminute=Integer.toString(minuteFinal);

        if(monthFinal<10)
            smonth="0"+smonth;
        if(dayFinal<10)
            sday="0"+sday;
        if(hourFinal<10)
            shour="0"+shour;
        if(minuteFinal<10)
            sminute="0"+sminute;
        ValidStr= syear+"-"+smonth+"-"+sday+" "+shour+":"+sminute+":00";
        try {
            Valid=dateFormat.parse(ValidStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentTime= Calendar.getInstance().getTime();



    }
}
