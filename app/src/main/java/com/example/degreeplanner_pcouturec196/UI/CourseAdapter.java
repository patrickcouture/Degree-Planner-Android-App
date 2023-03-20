package com.example.degreeplanner_pcouturec196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.degreeplanner_pcouturec196.R;
import com.example.degreeplanner_pcouturec196.entities.Courses;
import com.example.degreeplanner_pcouturec196.entities.Terms;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{
    class CourseViewHolder extends RecyclerView.ViewHolder{

        private final TextView courseItemViewName;
        private final TextView courseItemViewStatus;

        private CourseViewHolder (View view){

            super(view);
            courseItemViewName = view.findViewById(R.id.courseItemTextView);
            courseItemViewStatus = view.findViewById(R.id.courseStatusTextView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Courses current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("courseStart", current.getCourseStart());
                    intent.putExtra("courseEnd", current.getCourseEnd());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("courseNotes", current.getCourseNotes());
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("courseInstructor", current.getCourseInstructor());
                    intent.putExtra("courseInstructorPhone", current.getCourseInstructorPhone());
                    intent.putExtra("courseInstructorEmail", current.getCourseInstructorEmail());
                    context.startActivity(intent);


                }
            });
        }

    }
    private List<Courses> mCourses;
    private final Context context;

    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item, parent, false);

        return new CourseAdapter.CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses != null){
            Courses currentCourses = mCourses.get(position);
            String name = currentCourses.getCourseName();
            String status = currentCourses.getCourseStatus();
            holder.courseItemViewName.setText(name);
            holder.courseItemViewStatus.setText(status);
        }
        else {
            holder.courseItemViewName.setText("No Course Name");
            holder.courseItemViewStatus.setText("No Course Status");
        }

    }



    public void setCourses(List<Courses> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourses != null) {
            return mCourses.size();
        }else return 0;
    }
}
