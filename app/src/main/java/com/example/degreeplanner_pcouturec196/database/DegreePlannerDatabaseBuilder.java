package com.example.degreeplanner_pcouturec196.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.degreeplanner_pcouturec196.dao.AssessmentsDAO;
import com.example.degreeplanner_pcouturec196.dao.CoursesDAO;
import com.example.degreeplanner_pcouturec196.dao.TermsDAO;
import com.example.degreeplanner_pcouturec196.entities.Assessments;
import com.example.degreeplanner_pcouturec196.entities.Courses;
import com.example.degreeplanner_pcouturec196.entities.Terms;

@Database(entities = {Terms.class, Courses.class, Assessments.class}, version = 3, exportSchema = false)
public abstract class DegreePlannerDatabaseBuilder extends RoomDatabase {
    public abstract TermsDAO termsDAO();
    public abstract CoursesDAO coursesDAO();
    public abstract AssessmentsDAO assessmentsDAO();


    private static volatile DegreePlannerDatabaseBuilder INSTANCE;

    static DegreePlannerDatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE==null){

            synchronized (DegreePlannerDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),DegreePlannerDatabaseBuilder.class, "myDegreePlannerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
