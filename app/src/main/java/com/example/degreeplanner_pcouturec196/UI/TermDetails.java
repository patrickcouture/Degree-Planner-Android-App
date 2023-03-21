package com.example.degreeplanner_pcouturec196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.degreeplanner_pcouturec196.R;
import com.example.degreeplanner_pcouturec196.database.Repository;
import com.example.degreeplanner_pcouturec196.entities.Assessments;
import com.example.degreeplanner_pcouturec196.entities.Courses;
import com.example.degreeplanner_pcouturec196.entities.Terms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    EditText editTermName;
    EditText editTermStart;
    EditText editTermEnd;
    String termName;
    String startDate;
    String endDate;
    int termId;
    Terms term;
    Terms currentTerm;
    int numCourses;
    Repository repository;
    DatePickerDialog.OnDateSetListener termstartDate;
    DatePickerDialog.OnDateSetListener termEndDate;
    final Calendar myCalendarStart = Calendar.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editTermName = findViewById(R.id.tNameFill);
        editTermStart = findViewById(R.id.tStartFill);
        editTermEnd = findViewById(R.id.tEndFill);
        termId = getIntent().getIntExtra("termId", -1);
        termName = getIntent().getStringExtra("termName");

        startDate = getIntent().getStringExtra("termStart");
        endDate = getIntent().getStringExtra("termEnd");

        editTermName.setText(termName);
        editTermStart.setText(startDate);
        editTermEnd.setText(endDate);
        repository = new Repository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.courseRecView);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Courses> filteredCourses = new ArrayList<>();
        courseAdapter.setCourses(filteredCourses);
        for (Courses c : repository.getAllCourses()) {
            if (c.getTermId() == termId) filteredCourses.add(c);
        }
        courseAdapter.notifyDataSetChanged();

        //From DatePicker Video Presented by Carolyn J. Sher-Decusatis
        termstartDate = new DatePickerDialog.OnDateSetListener() {
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
        editTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermDetails.this, termstartDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //From DatePicker Video Presented by Carolyn J. Sher-Decusatis
        termEndDate = new DatePickerDialog.OnDateSetListener() {
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
        editTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermDetails.this, termEndDate, myCalendarStart.get(Calendar.YEAR),
                         myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button saveButton = findViewById(R.id.saveTermDetails);
        saveButton.setOnClickListener(view -> {
            if(termId == -1){
                term = new Terms(0, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
                repository.insert(term);
                Toast.makeText(getApplicationContext(), "New Term added!", Toast.LENGTH_SHORT).show();
                finish();

            }
            else {
                term = new Terms(termId, editTermName.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
                repository.update(term);
                Toast.makeText(getApplicationContext(), "Term updated!", Toast.LENGTH_SHORT).show();
                finish();

            }


        });

        Button deleteButton = findViewById(R.id.deleteTermDetails);
        deleteButton.setOnClickListener(view -> {
            for (Terms ter : repository.getAllTerms()) {
                if (ter.getTermId() == termId) currentTerm = ter;
            }
            numCourses = 0;
            for(Courses courses : repository.getAllCourses()) {
                if(courses.getTermId() == termId)   ++numCourses;
            }
            if(numCourses == 0) {
                repository.delete(currentTerm);
                Toast.makeText(TermDetails.this, currentTerm.getTermName() + " was deleted", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(TermDetails.this, "Can't Delete a Term that has a Course in it", Toast.LENGTH_LONG).show();

            }
        });


    }
    private  void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTermStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTermEnd.setText(sdf.format(myCalendarStart.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.notifyTStart:
                String startDateFromScreen = editTermStart.getText().toString();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date myDate = null;
                try {
                    myDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(TermDetails.this, MyReceiver.class);
                intent.setAction("TermStartNotification");
                intent.putExtra("termStart", " FYI: " + termName + " Term is starting on: " + startDateFromScreen);
                PendingIntent sender = PendingIntent.getBroadcast(TermDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;


            case R.id.notifyTEnd:
                String endDateFromScreen = editTermEnd.getText().toString();
                String myFormat1 = "MM/dd/yy";
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                Date myDate1 = null;
                try {
                    myDate1 = sdf1.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger1 = myDate1.getTime();
                Intent intent1 = new Intent(TermDetails.this, MyReceiver.class);
                intent1.setAction("TermEndNotification");
                intent1.putExtra("termEnd", " FYI: " + termName + " is ending on: " + endDateFromScreen);
                PendingIntent sender1 = PendingIntent.getBroadcast(TermDetails.this, ++MainActivity.numAlert, intent1, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, trigger1, sender1);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Courses> allCourses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.courseRecView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);
        courseAdapter.notifyDataSetChanged();
    }
}