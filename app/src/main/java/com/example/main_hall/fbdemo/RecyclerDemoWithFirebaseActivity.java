package com.example.main_hall.fbdemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerDemoWithFirebaseActivity extends AppCompatActivity {

    ArrayList<MyStudent> al = new ArrayList<>();
    RecyclerView rcv1;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mainref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_demo_with_firebase);

        firebaseDatabase=FirebaseDatabase.getInstance();
        mainref=firebaseDatabase.getReference("Students");

        al.add(new MyStudent(111,"ABCD",900));
        al.add(new MyStudent(222,"ABCD",700));
        al.add(new MyStudent(333,"ABCD",800));

        rcv1 = (RecyclerView) (findViewById(R.id.rcv1));

//        LinearLayoutManager simpleverticallayout= new LinearLayoutManager(getApplicationContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rcv1.setLayoutManager(gridLayoutManager);

        final myadapter myad = new myadapter();
        rcv1.setAdapter(myad);

        mainref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                al.clear();

                for(DataSnapshot singleDS  : dataSnapshot.getChildren() )
                {
                    //Log.d("MYMESSAGE", singleDS.toString()+"\n");

                    // Convert singleDS Value to Java Object
                    MyStudent myStudent  = singleDS.getValue(MyStudent.class);

                     al.add(myStudent);
                }


                myad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }




    class myadapter extends RecyclerView.Adapter<myadapter.MyViewHolder>
    {


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.single_row_design,parent,false);

            CardView cardView = v.findViewById(R.id.cardView);

            MyViewHolder mv = new MyViewHolder(cardView);

            return mv;
        }


        class MyViewHolder extends RecyclerView.ViewHolder
        {
            CardView ll1;
            public MyViewHolder(CardView itemView) {
                super(itemView);
                ll1 = itemView;
            }
        }


        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

            CardView localCardView =   myViewHolder.ll1;

            final MyStudent st = al.get(position);

            localCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), st.getRollno()+" "+st.getName(), Toast.LENGTH_SHORT).show();
                }
            });

            TextView tv_name = (TextView) localCardView.findViewById(R.id.tv_name);
            TextView tv_rollno = (TextView) localCardView.findViewById(R.id.tv_rollno);
            TextView tv_marks = (TextView) localCardView.findViewById(R.id.tv_marks);


            tv_name.setText("Name : "+st.getName());
            tv_rollno.setText("Rollno : "+st.getRollno());
            tv_marks.setText("Rollno : "+st.getMarks());


        }

        @Override
        public int getItemCount() {
            return al.size();
        }



    }

}
