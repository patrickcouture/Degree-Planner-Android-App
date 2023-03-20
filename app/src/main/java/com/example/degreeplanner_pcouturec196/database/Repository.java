package com.example.degreeplanner_pcouturec196.database;


import android.app.Application;

import com.example.degreeplanner_pcouturec196.dao.AssessmentsDAO;
import com.example.degreeplanner_pcouturec196.dao.CoursesDAO;
import com.example.degreeplanner_pcouturec196.dao.TermsDAO;
import com.example.degreeplanner_pcouturec196.entities.Assessments;
import com.example.degreeplanner_pcouturec196.entities.Courses;
import com.example.degreeplanner_pcouturec196.entities.Terms;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private AssessmentsDAO mAssessmentDAO;
    private CoursesDAO mCoursesDAO;

    private TermsDAO mTermsDAO;

    private List<Terms> mAllTerms;
    private List<Courses> mAllCourses;

    private List<Assessments> mAllAssessments;



    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DegreePlannerDatabaseBuilder db = DegreePlannerDatabaseBuilder.getDatabase(application);
        mTermsDAO = db.termsDAO();
        mCoursesDAO = db.coursesDAO();
        mAssessmentDAO = db.assessmentsDAO();

    }

    public List<Terms>getAllTerms() {
        databaseExecutor.execute(()->{
            mAllTerms=mTermsDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Terms term) {
        databaseExecutor.execute(()->{
            mTermsDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Terms term) {
        databaseExecutor.execute(()->{
            mTermsDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Terms term) {
        databaseExecutor.execute(()->{
            mTermsDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Courses>getAllCourses() {
        databaseExecutor.execute(()->{
            mAllCourses=mCoursesDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(Courses course) {
        databaseExecutor.execute(()->{
            mCoursesDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Courses course) {
        databaseExecutor.execute(()->{
            mCoursesDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Courses course) {
        databaseExecutor.execute(()->{
            mCoursesDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }



    public List<Assessments>getAllAssessments() {
        databaseExecutor.execute(()->{
            mAllAssessments=mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(Assessments assessments) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessments);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Assessments assessments) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessments);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Assessments assessments) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessments);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }





}
