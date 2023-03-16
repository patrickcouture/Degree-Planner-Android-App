package com.example.degreeplanner_pcouturec196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.degreeplanner_pcouturec196.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchTermList (View v) {

        Intent i = new Intent(this, TermList.class);
        startActivity(i);
    }

    public void launchCourses (View v) {

        Intent i = new Intent(this, CourseList.class);
        startActivity(i);
    }

    public void launchAssessments (View v) {

        Intent i = new Intent(this, AssessmentList.class);
        startActivity(i);
    }
}