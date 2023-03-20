package com.example.degreeplanner_pcouturec196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.degreeplanner_pcouturec196.R;
import com.example.degreeplanner_pcouturec196.database.Repository;
import com.example.degreeplanner_pcouturec196.entities.Courses;
import com.example.degreeplanner_pcouturec196.entities.Terms;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
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

        Button button = findViewById(R.id.saveTermDetails);
        button.setOnClickListener(view -> {
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
}