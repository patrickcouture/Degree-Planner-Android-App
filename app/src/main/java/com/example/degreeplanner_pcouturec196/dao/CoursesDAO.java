package com.example.degreeplanner_pcouturec196.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.degreeplanner_pcouturec196.entities.Courses;


import java.util.List;

@Dao
public interface CoursesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Courses course);

    @Update
    void update(Courses course);

    @Delete
    void delete(Courses course);

    @Query("SELECT * FROM courses ORDER BY courseId ASC")
    List<Courses> getAllCourses();

    @Query("SELECT * FROM courses WHERE termId= :termId ORDER BY courseId ASC")
    List<Courses> getAllAssociatedCourses(int termId);

}
