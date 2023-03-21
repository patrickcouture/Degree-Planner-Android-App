package com.example.degreeplanner_pcouturec196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

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
    int numAssessments;
    Courses currentCourse;
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
        String myFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);


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
            if (t.getTermId() == termId)
                editCourseTermId.setSelection(adapter.getPosition(t.getTermName()));
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
        courseAssessmentAdapter.notifyDataSetChanged();


        //From DatePicker Video Presented by Carolyn J. Sher-Decusatis
        editCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date;
                String info = editCourseStart.getText().toString();
                try {
                    myCalendarStart.setTime(simpleDateFormat.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, cStartDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //From DatePicker Video Presented by Carolyn J. Sher-Decusatis
        cStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthofYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabelStart();
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


        Button button = findViewById(R.id.cDetailsSave);
        button.setOnClickListener(view -> {
            for (Terms t : repository.getAllTerms()) {
                if (t.getTermName().equals(editCourseTermId.getSelectedItem())) {
                    termId = t.getTermId();
                }
            }
            if (courseId == -1) {

                courses = new Courses(0, editCourseName.getText().toString(), editCourseStart.getText().toString(),
                        editCourseEnd.getText().toString(), editCourseStatus.getSelectedItem().toString(), editCourseNotes.getText().toString(),
                        termId, editCourseInst.getText().toString(),
                        editInstPhone.getText().toString(), editInstEmail.getText().toString());
                repository.insert(courses);
                Toast.makeText(getApplicationContext(), "New Course added!", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                courses = new Courses(courseId, editCourseName.getText().toString(), editCourseStart.getText().toString(),
                        editCourseEnd.getText().toString(), editCourseStatus.getSelectedItem().toString(), editCourseNotes.getText().toString(),
                        termId, editCourseInst.getText().toString(),
                        editInstPhone.getText().toString(), editInstEmail.getText().toString());
                repository.update(courses);
                Toast.makeText(getApplicationContext(), "Course updated!", Toast.LENGTH_SHORT).show();
                finish();

            }


        });

        Button deleteButton = findViewById(R.id.cDetailsDeleteBtn);
        deleteButton.setOnClickListener(view -> {

            for (Courses cour : repository.getAllCourses()) {
                if (cour.getCourseId() == courseId) currentCourse = cour;
            }
            numAssessments = 0;
            for(Assessments assessments : repository.getAllAssessments()) {
                if(assessments.getCourseId() == courseId)   ++numAssessments;
            }
            if(numAssessments == 0) {
                repository.delete(currentCourse);
                Toast.makeText(CourseDetails.this, currentCourse.getCourseName() + " was deleted", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(CourseDetails.this, "Can't Delete a Course that has an Assessment in it", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseEnd.setText(sdf.format(myCalendarStart.getTime()));
    }

    //From Bicycle Shop Video - Dolphin Part 4 by Carolyn J. Sher-Decusatis
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.shareCourseNotes:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editCourseNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, courseName + " Notes");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;


            case R.id.notifyCStart:
                String startDateFromScreen = editCourseStart.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;
                try {
                    myDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
                intent.setAction("CourseStartNotification");
                intent.putExtra("courseStart", " FYI: " + courseName + " is starting on: " + startDateFromScreen);
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;


            case R.id.notifyCEnd:
                String endDateFromScreen = editCourseEnd.getText().toString();
                String myFormat1 = "MM/dd/yy";
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                Date myDate1 = null;
                try {
                    myDate1 = sdf1.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger1 = myDate1.getTime();
                Intent intent1 = new Intent(CourseDetails.this, MyReceiver.class);
                intent1.setAction("CourseEndNotification");
                intent1.putExtra("courseEnd", " FYI: " + courseName + " is ending on: " + endDateFromScreen);
                PendingIntent sender1 = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent1, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, trigger1, sender1);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courseAssessRecView);
        repository = new Repository(getApplication());
        final CourseAssessAdapter courseAssessmentAdapter = new CourseAssessAdapter(this);
        recyclerView.setAdapter(courseAssessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessments> filteredAssessments = new ArrayList<>();
        for (Assessments c : repository.getAllAssessments()) {
            if (c.getCourseId() == courseId) filteredAssessments.add(c);
        }
        courseAssessmentAdapter.setAssessments(filteredAssessments);
        courseAssessmentAdapter.notifyDataSetChanged();


    }
}