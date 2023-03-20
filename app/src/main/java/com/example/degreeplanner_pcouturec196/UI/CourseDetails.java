package com.example.degreeplanner_pcouturec196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.degreeplanner_pcouturec196.R;
import com.example.degreeplanner_pcouturec196.database.Repository;
import com.example.degreeplanner_pcouturec196.entities.Assessments;
import com.example.degreeplanner_pcouturec196.entities.Courses;
import com.example.degreeplanner_pcouturec196.entities.Terms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {


    EditText editCourseName;
    EditText editCourseStart;
    EditText editCourseEnd;
    Spinner editCourseStatus;
    EditText editCourseInst;
    EditText editInstPhone;
    EditText editInstEmail;
    EditText editCourseNotes;
    Spinner editCourseTermId;

    String courseName;
    String courseStartDate;
    String courseEndDate;
    String courseStatus;
    String courseInstructor;
    String courseInstructorPhone;
    String courseInstructorEmail;
    String courseNotes;
    Courses courses;
    int courseId;
    int termId;
    int assessment;
    Repository repository;

    DatePickerDialog.OnDateSetListener cStartDate;
    DatePickerDialog.OnDateSetListener cEndDate;
    final Calendar myCalendarStart = Calendar.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        editCourseName = findViewById(R.id.cNameEdit);
        editCourseStart = findViewById(R.id.cStartEdit);
        editCourseEnd = findViewById(R.id.cEndEdit);
        editCourseStatus = (Spinner) findViewById(R.id.cStatusSpinner);
        ArrayAdapter<CharSequence> courseStatusAdapter = ArrayAdapter.createFromResource(this, R.array.course_status, android.R.layout.simple_spinner_item);
        courseStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCourseStatus.setAdapter(courseStatusAdapter);
        editCourseTermId = findViewById(R.id.termScheduledSpinner);
        editCourseInst = findViewById(R.id.cInstructorEdit);
        editInstPhone = findViewById(R.id.cInstructorPhoneEdit);
        editInstEmail = findViewById(R.id.cInstructorEmailEdit);

        editCourseNotes = findViewById(R.id.cNotesEdit);
        courseId = getIntent().getIntExtra("courseId", -1);
        courseName = getIntent().getStringExtra("courseName");
        courseStartDate = getIntent().getStringExtra("courseStart");
        courseEndDate = getIntent().getStringExtra("courseEnd");
        courseStatus = getIntent().getStringExtra("courseStatus");
        courseNotes = getIntent().getStringExtra("courseNotes");
        courseInstructor = getIntent().getStringExtra("courseInstructor");
        courseInstructorPhone = getIntent().getStringExtra("courseInstructorPhone");
        courseInstructorEmail = getIntent().getStringExtra("courseInstructorEmail");
        termId = getIntent().getIntExtra("termId", -1);



        editCourseName.setText(courseName);
        editCourseStart.setText(courseStartDate);
        editCourseEnd.setText(courseEndDate);
        editCourseStatus.setSelection(courseStatusAdapter.getPosition(courseStatus));
        repository = new Repository(getApplication());
        editCourseTermId = findViewById(R.id.termScheduledSpinner);
        repository = new Repository(getApplication());
        List<String> termNames = new ArrayList<>();
        for (Terms t : repository.getAllTerms()) {
            termNames.add(t.getTermName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCourseTermId.setAdapter(adapter);
        for (Terms t : repository.getAllTerms()) {
            if (t.getTermId() == termId) editCourseTermId.setSelection(adapter.getPosition(t.getTermName()));
        }
        editCourseInst.setText(courseInstructor);
        editInstPhone.setText(courseInstructorPhone);
        editInstEmail.setText(courseInstructorEmail);
        editCourseNotes.setText(courseNotes);
        repository = new Repository(getApplication());


        RecyclerView recyclerView = findViewById(R.id.courseAssessRecView);
        repository = new Repository(getApplication());
        final CourseAssessAdapter courseAssessmentAdapter = new CourseAssessAdapter(this);
        recyclerView.setAdapter(courseAssessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessments> filteredAssessments = new ArrayList<>();
        courseAssessmentAdapter.setAssessments(filteredAssessments);
        for (Assessments c : repository.getAllAssessments()) {
            if (c.getCourseId() == courseId) filteredAssessments.add(c);
        }



        //From DatePicker Video Presented by Carolyn J. Sher-Decusatis
        cStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthofYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
                updateLabelStart();
            }
        };
        //From DatePicker Video Presented by Carolyn J. Sher-Decusatis
        editCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseDetails.this, cStartDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //From DatePicker Video Presented by Carolyn J. Sher-Decusatis
        cEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthofYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
                updateLabelEnd();
            }
        };
        //From DatePicker Video Presented by Carolyn J. Sher-Decusatis
        editCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseDetails.this, cEndDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button button = findViewById(R.id.cDetailsSave);
        button.setOnClickListener(view -> {
            for (Terms t : repository.getAllTerms()) {
                if (t.getTermName().equals(editCourseTermId.getSelectedItem()) ) {
                    termId = t.getTermId();
                }
            }
            if(courseId == -1){

                courses = new Courses(0, editCourseName.getText().toString(), editCourseStart.getText().toString(),
                        editCourseEnd.getText().toString(), editCourseStatus.getSelectedItem().toString(), editCourseNotes.getText().toString(),
                        termId,editCourseInst.getText().toString(),
                        editInstPhone.getText().toString(), editInstEmail.getText().toString());
                repository.insert(courses);
                Toast.makeText(getApplicationContext(), "New Course added!", Toast.LENGTH_SHORT).show();
                finish();

            }
            else {
                courses = new Courses(courseId, editCourseName.getText().toString(), editCourseStart.getText().toString(),
                        editCourseEnd.getText().toString(), editCourseStatus.getSelectedItem().toString(), editCourseNotes.getText().toString(),
                        termId,editCourseInst.getText().toString(),
                        editInstPhone.getText().toString(), editInstEmail.getText().toString());
                repository.update(courses);
                Toast.makeText(getApplicationContext(), "Course updated!", Toast.LENGTH_SHORT).show();
                finish();

            }


        });


    }

    private  void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseEnd.setText(sdf.format(myCalendarStart.getTime()));
    }



}