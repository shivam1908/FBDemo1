package com.example.main_hall.fbdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference  mainref;

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mainref = firebaseDatabase.getReference("Students");

        final DatabaseReference myref = mainref.child("1");

        tv1=(TextView)(findViewById(R.id.tv1));

        mainref.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Data from Cloud database is received Here

                //Log.d("MYMESSAGE",dataSnapshot.toString());
                //tv1.setText(dataSnapshot.toString());

                tv1.setText("");

                for(DataSnapshot singleDS  : dataSnapshot.getChildren() )
                {
                    //Log.d("MYMESSAGE", singleDS.toString()+"\n");

                    // Convert singleDS Value to Java Object
                    MyStudent myStudent  = singleDS.getValue(MyStudent.class);

                    tv1.append(myStudent.getRollno()
                            +" "+myStudent.getName()+" "
                            +myStudent.getMarks()+"\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Use This Listener if we want to fetch only once
        mainref.addListenerForSingleValueEvent(  new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    public void go(View v)
    {
        int rollno=(int)(Math.random()*100);
        String name=getRandomName();
        int marks=80+(int)(Math.random()*20);

        MyStudent myobj=new MyStudent(rollno,name,marks);

        mainref.child(rollno+"").setValue(myobj);

        Toast.makeText(this, "Record Added", Toast.LENGTH_SHORT).show();
    }


    String getRandomName()
    {
        String s[]={"Harpreet","Harman","Rahul","Rohit","Aman","Lalu","ABCD","XYZ","PQR","HELLO"};

        int pos=(int)(Math.random()*s.length);

        return s[pos];
    }

    public void go2(View v)
    {
        int rollno=1;
        String name="AMRINDER PAL SINGH";
        int marks=95;

        MyStudent myStudent=new MyStudent(rollno,name,marks);
        mainref.child(rollno+"").setValue(myStudent);

    }

    public void go5(View v)
    {
        int rollno=(int)(Math.random()*100);
        String name=getRandomName();
        int marks=80+(int)(Math.random()*20);

        MyStudent myobj=new MyStudent(rollno,name,marks);

        //This will generate PUSHID and then setvalue
        mainref.push().setValue(myobj);

        Toast.makeText(this, "Record Added", Toast.LENGTH_SHORT).show();


    }

    public void go3(View v)
    {
        mainref.child("1").child("marks").setValue(98);
    }

    public void go4(View v)
    {
        mainref.child("4").removeValue();
    }

    public void go6(View v)
    {
        mainref.orderByChild("marks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv1.setText("");

                for(DataSnapshot singleDS  : dataSnapshot.getChildren() )
                {
                    //Log.d("MYMESSAGE", singleDS.toString()+"\n");

                    // Convert singleDS Value to Java Object
                    MyStudent myStudent  = singleDS.getValue(MyStudent.class);

                    tv1.append(myStudent.getRollno()
                            +" "+myStudent.getName()+" "
                            +myStudent.getMarks()+"\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

   public void go7(View v)
   {
       Intent in=new Intent(this, RecyclerDemoWithFirebaseActivity.class);
       startActivity(in);
   }


}
