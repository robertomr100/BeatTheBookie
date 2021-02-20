package edu.fsu.cs.mobile.beatthebookie;

import android.content.Context;
import android.media.Image;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Bet> {

    private boolean votedUP=false;
    private  boolean votedDOWN=false;
    private Context mContext;
    private ArrayList<Bet> mBets;
    DatabaseReference databaseUser;
    FirebaseAuth mAuth;
    private String Creator;
    User usertemp;
    int temp;
    String tempID;


    public CustomAdapter(@NonNull Context context, int resource)
    {
        super(context, resource);
        mContext=context;
        mBets= new ArrayList<>();
    }

    private static class MyBetHolder {
        TextView textViewEntry;
        TextView textViewScore;
        TextView textViewWebsite;
        TextView textViewPayout;
        TextView textViewUser;
        TextView textViewValidUntil;
        ImageButton upButton;
        ImageButton downButton;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Bet item = getItem(position);
        MyBetHolder viewHolder;
        if (convertView == null) {
            // If row has not been created, inflate from row_object.xml
            //   and assign viewHolder to tag
            viewHolder = new MyBetHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row, parent, false);
            mAuth=FirebaseAuth.getInstance();
            databaseUser = FirebaseDatabase.getInstance().getReference("Users");

            viewHolder.textViewEntry =(TextView) convertView.findViewById(R.id.entry);
            viewHolder.textViewScore=(TextView) convertView.findViewById(R.id.Score);
            viewHolder.textViewWebsite =(TextView) convertView.findViewById(R.id.rowWebsite);
            viewHolder.textViewPayout =(TextView) convertView.findViewById(R.id.rowPayout);
            viewHolder.textViewUser =(TextView) convertView.findViewById(R.id.rowUser);
            viewHolder.textViewValidUntil=(TextView) convertView.findViewById(R.id.rowValidUntil);
            viewHolder.upButton=(ImageButton) convertView.findViewById(R.id.upButton);
            viewHolder.downButton=(ImageButton) convertView.findViewById(R.id.downButton);

            viewHolder.upButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(votedUP==false) {
                        item.setVotes(item.getVotes() + 1);
                       // updateScore(+1, item);
                        if(votedDOWN==true) {
                            item.setVotes(item.getVotes() + 1);
                        //    updateScore(+1, item);
                        }
                        votedUP=true;
                        votedDOWN=false;


                    }

                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("bets").child(item.getID());
                    Bet bet = new Bet(item.getID(),item.getText(),item.getFinalOdds(),item.getWebsite(),item.getCreator(), item.getValidUntil());
                    bet.setVotes(item.getVotes());
                    databaseReference.setValue(bet);
                    notifyDataSetChanged();

                }
            });

            viewHolder.downButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(votedDOWN==false) {
                        item.setVotes(item.getVotes() - 1);
                        //updateScore(-1,item);
                        if(votedUP==true) {
                            item.setVotes(item.getVotes() - 1);
                            //updateScore(-1, item);
                        }

                        votedDOWN=true;
                        votedUP=false;

                    }
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("bets").child(item.getID());
                    Bet bet = new Bet(item.getID(),item.getText(),item.getFinalOdds(),item.getWebsite(),item.getCreator(), item.getValidUntil());
                    bet.setVotes(item.getVotes());
                    bet.setCreatorID(item.getCreatorID());
                    databaseReference.setValue(bet);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (MyBetHolder) convertView.getTag();
        }

        viewHolder.textViewEntry.setText(item.getText());
        viewHolder.textViewScore.setText(String.valueOf( item.getVotes()));
        viewHolder.textViewPayout.setText("@"+String.valueOf(item.getFinalOdds()));
        viewHolder.textViewWebsite.setText("Website: "+item.getWebsite());
        viewHolder.textViewUser.setText(item.getCreator());



        return convertView;

    }
/*
    private void updateScore(final int i, final Bet item) {
        Toast.makeText(mContext,item.getCreatorID(),Toast.LENGTH_LONG).show();
        final String UID= item.getCreatorID();
        temp=0;

        databaseUser.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               for(DataSnapshot betSnapshot: dataSnapshot.getChildren())
               {
                    usertemp=betSnapshot.getValue(User.class);
                   if(usertemp.getID().equals(item.getCreatorID())) {
                       {
                           temp = usertemp.getPoints();
                           Toast.makeText(mContext,UID,Toast.LENGTH_LONG).show();
                       }

                   }

               }

           }


           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });



        //databaseUser.child(UID).child("points").setValue(temp+i);
    }

*/
    @Override
    public int getCount() {
        return mBets.size();
    }


    @Nullable
    @Override
    public Bet getItem(int position) {
        return mBets.get(position);
    }

    @Override
    public int getPosition(@Nullable Bet item) {
        for (int i = 0; i < mBets.size(); i++) {
            if (TextUtils.equals(item.getText(), mBets.get(i).getText())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void add(@Nullable Bet object) {
        int idx = getPosition(object);
        if (idx >= 0) {
            mBets.set(idx, object);

        } else {
            mBets.add(object);
        }
        notifyDataSetChanged();
    }

}
