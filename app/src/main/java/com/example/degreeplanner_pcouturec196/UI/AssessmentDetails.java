package com.example.degreeplanner_pcouturec196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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


public class AssessmentDetails extends AppCompatActivity {

    EditText editAssessName;
    EditText editAssessDate;
    Spinner editAssessType;
    Spinner editAssessCourse;


    String assessmentName;
    String assessmentDate;
    String assessmentType;
    int assessmentCourse;
    int assessmentId;
    int courseId;

    Assessments assessments;
    Assessments currentAssessment;


    Repository repository;

    DatePickerDialog.OnDateSetListener aAssessDate;
    final Calendar myCalendarStart = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        editAssessName = findViewById(R.id.aDetailEditName);
        editAssessDate = findViewById(R.id.aDetailEditDate);
        editAssessType = (Spinner) findViewById(R.id.atypeSpinner);
        ArrayAdapter<CharSequence> assessTypeAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_type, android.R.layout.simple_spinner_item);
        assessTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editAssessType.setAdapter(assessTypeAdapter);
        editAssessCourse = findViewById(R.id.aCourseSpinner);
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        assessmentDate = getIntent().getStringExtra("assessmentDate");
        assessmentType = getIntent().getStringExtra("assessmentType");
        assessmentCourse = getIntent().getIntExtra("courseId", -1);


        editAssessName.setText(assessmentName);
        editAssessDate.setText(assessmentDate);
        editAssessType.setSelection(assessTypeAdapter.getPosition(assessmentType));

        editAssessCourse = findViewById(R.id.aCourseSpinner);
        repository = new Repository(getApplication());
        List<String> courseNames = new ArrayList<>();
        for (Courses c : repository.getAllCourses()) {
            courseNames.add(c.getCourseName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editAssessCourse.setAdapter(adapter);
        for (Courses c : repository.getAllCourses()) {
            if (c.getCourseId() == assessmentCourse) editAssessCourse.setSelection(adapter.getPosition(c.getCourseName()));
        }
//        repository = new Repository(getApplication());
//        for (Courses c : repository.getAllCourses()) {
//            if (c.getCourseId() == assessmentCourse) editAssessCourse.setText(c.getCourseName());
//        }

        repository = new Repository(getApplication());

        //From DatePicker Video Presented by Carolyn J. Sher-Decusatis
        aAssessDate = (view, year, monthofYear, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthofYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
            updateLabelStart();
        };
        //From DatePicker Video Presented by Carolyn J. Sher-Decusatis
        editAssessDate.setOnClickListener(view -> new DatePickerDialog(AssessmentDetails.this, aAssessDate, myCalendarStart
                .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show());



        Button button = findViewById(R.id.aDetailsSave);
        button.setOnClickListener(view -> {

            for (Courses c : repository.getAllCourses()) {
                if (c.getCourseName().equals(editAssessCourse.getSelectedItem()) ) {
                    assessmentCourse = c.getCourseId();
                }
            }
            if(assessmentId == -1){

                assessments = new Assessments(0, editAssessName.getText().toString(), editAssessDate.getText().toString(),
                        editAssessType.getSelectedItem().toString(), assessmentCourse );
                repository.insert(assessments);
                Toast.makeText(getApplicationContext(), "New Assessment added!", Toast.LENGTH_SHORT).show();
                finish();

            }
            else {


                assessments = new Assessments(assessmentId, editAssessName.getText().toString(), editAssessDate.getText().toString(),
                        editAssessType.getSelectedItem().toString(), assessmentCourse);
                repository.update(assessments);
                Toast.makeText(getApplicationContext(), "Assessment updated!", Toast.LENGTH_SHORT).show();
                finish();

            }


        });

        Button deleteButton = findViewById(R.id.aDetailsDelete);
        deleteButton.setOnClickListener(view -> {

            for (Assessments assess : repository.getAllAssessments()) {
                if (assess.getAssessmentId() == assessmentId) currentAssessment = assess;
            }
            repository.delete(currentAssessment);
            Toast.makeText(AssessmentDetails.this, currentAssessment.getAssessmentName() + " was deleted", Toast.LENGTH_LONG).show();
            finish();

        });



    }

    private  void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editAssessDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.notifyAssessDate:
                String startDateFromScreen = editAssessDate.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;
                try {
                    myDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.setAction("AssessmentNotification");
                intent.putExtra("assessment", " FYI: " + assessmentName + " is on: " + startDateFromScreen);
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;


         }
        return super.onOptionsItemSelected(item);
    }

}