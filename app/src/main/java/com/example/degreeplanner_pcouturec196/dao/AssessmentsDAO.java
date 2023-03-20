package com.example.degreeplanner_pcouturec196.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.degreeplanner_pcouturec196.entities.Assessments;


import java.util.List;

@Dao
public interface AssessmentsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessments assess);

    @Update
    void update(Assessments assess);

    @Delete
    void delete(Assessments assess);

    @Query("SELECT * FROM assessments ORDER BY assessmentId ASC")
    List<Assessments> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE courseId= :courseId ORDER BY assessmentId ASC")
    List<Assessments> getAllAssociatedAssessments(int courseId);
}
