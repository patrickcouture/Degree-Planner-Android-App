package com.example.degreeplanner_pcouturec196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.degreeplanner_pcouturec196.R;
import com.example.degreeplanner_pcouturec196.database.Repository;
import com.example.degreeplanner_pcouturec196.entities.Courses;
import com.example.degreeplanner_pcouturec196.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        FloatingActionButton fab = findViewById(R.id.cListFltAcBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, CourseDetails.class);
                startActivity(intent);
            }
        });

        repository = new Repository(getApplication());
        List<Courses> allCourses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.cListRecView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);
        courseAdapter.notifyDataSetChanged();




    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Courses> allCourses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.cListRecView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);
        courseAdapter.notifyDataSetChanged();

    }
}