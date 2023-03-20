package com.example.degreeplanner_pcouturec196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.degreeplanner_pcouturec196.R;
import com.example.degreeplanner_pcouturec196.database.Repository;
import com.example.degreeplanner_pcouturec196.entities.Assessments;
import com.example.degreeplanner_pcouturec196.entities.Courses;
import com.example.degreeplanner_pcouturec196.entities.Terms;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Terms terms = new Terms(3, "Fall", "2023-03-30", "2023-06-30");
        Repository repository = new Repository(getApplication());
        repository.insert(terms);

        Courses courses = new Courses(2, "Computer Science", "2023-03-30", "2023-06-30",
                "In Progress", "Easy class", 3, "Joe Schmoe", "555-555-1212", "joe@university.com");
        repository.insert(courses);


        Assessments assessments = new Assessments(4, "Technical Paper", "2023-04-05", "Performance", 2);
        repository.insert(assessments);

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