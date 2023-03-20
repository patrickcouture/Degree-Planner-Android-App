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
import com.example.degreeplanner_pcouturec196.database.Repository;
import com.example.degreeplanner_pcouturec196.entities.Assessments;
import com.example.degreeplanner_pcouturec196.entities.Courses;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{


    class AssessmentViewHolder extends RecyclerView.ViewHolder{

        private final TextView assessmentViewName;
        private final TextView assessmentViewDate;




        private AssessmentViewHolder (View view){

            super(view);
            assessmentViewName = view.findViewById(R.id.aName);
            assessmentViewDate = view.findViewById(R.id.aDate);


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

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_list_item, parent, false);

        return new AssessmentAdapter.AssessmentViewHolder(view);
    }

    Repository repository;
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            Assessments currentAssessments = mAssessments.get(position);
            String name = currentAssessments.getAssessmentName();
            String date = currentAssessments.getAssessmentDate();
            int courseId = currentAssessments.getCourseId();
            holder.assessmentViewName.setText(name);
            holder.assessmentViewDate.setText(date);



        }
        else {
            holder.assessmentViewName.setText("No Assessment Name");
            holder.assessmentViewDate.setText("No Assessment Date");

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
