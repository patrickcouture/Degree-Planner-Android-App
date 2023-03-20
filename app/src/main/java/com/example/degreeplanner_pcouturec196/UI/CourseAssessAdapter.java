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
import com.example.degreeplanner_pcouturec196.entities.Assessments;

import java.util.List;

public class CourseAssessAdapter extends RecyclerView.Adapter<CourseAssessAdapter.CourseAssessViewHolder>{

    class CourseAssessViewHolder extends RecyclerView.ViewHolder{

        private final TextView courseAssessmentViewName;
        private final TextView courseAssessmentViewDate;


        private CourseAssessViewHolder (View view){

            super(view);
            courseAssessmentViewName = view.findViewById(R.id.cAssessNameTextView);
            courseAssessmentViewDate = view.findViewById(R.id.cAssessDateTextView);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessments current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("assessmentDate", current.getAssessmentDate());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("courseId", current.getCourseId());
                    context.startActivity(intent);


                }
            });
        }

    }
    private List<Assessments> mAssessments;
    private final Context context;

    private final LayoutInflater mInflater;

    public CourseAssessAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAssessAdapter.CourseAssessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_assess_list_item, parent, false);

        return new CourseAssessAdapter.CourseAssessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAssessAdapter.CourseAssessViewHolder holder, int position) {
        if(mAssessments != null){
            Assessments currentAssessments = mAssessments.get(position);
            String name = currentAssessments.getAssessmentName();
            String date = currentAssessments.getAssessmentDate();
            holder.courseAssessmentViewName.setText(name);
            holder.courseAssessmentViewDate.setText(date);

        }
        else {
            holder.courseAssessmentViewName.setText("No Assessment Name");
            holder.courseAssessmentViewDate.setText("No Assessment Date");

        }

    }



    public void setAssessments(List<Assessments> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mAssessments != null) {
            return mAssessments.size();
        }else return 0;
    }
}
