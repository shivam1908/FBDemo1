package com.example.main_hall.fbdemo;

// Create a Mapping Java Bean Class

public class MyStudent {

    private int rollno;
    private String name;
    private int marks;

    MyStudent()
    {
      rollno=0;
      name="DUMMY";
      marks=0;
    }

    public MyStudent(int rollno, String name, int marks) {
        this.rollno = rollno;
        this.name = name;
        this.marks = marks;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}


