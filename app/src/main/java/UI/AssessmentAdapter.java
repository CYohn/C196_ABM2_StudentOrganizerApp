package UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.List;

import Entities.Assessment;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment>mAssessments;
    private final Context context;
    private final LayoutInflater mInflator;

    //Assessment adapter constructor
    AssessmentAdapter(Context context){
        mInflator= LayoutInflater.from(context);
        this.context=context;
    }


    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView DBAssessmentTitle;
        private final TextView DBAssessmentStartDate;
        private final TextView DBAssessmentEndDate;
        private final TextView DBAssessmentType;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            DBAssessmentTitle = itemView.findViewById(R.id.assessmentTitleView);
            DBAssessmentStartDate = itemView.findViewById(R.id.assessmentStartDateView);
            DBAssessmentEndDate = itemView.findViewById(R.id.assessmentEndDateView);
            DBAssessmentType = itemView.findViewById(R.id.assessmentTypeView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment selectedAssessment = mAssessments.get(position);

                    Fragment assessmentDetails = new AssessmentDetailsFragment();
                    FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                    Bundle bundle = new Bundle();
                    bundle.putString("assessmentTitleTxtView", selectedAssessment.getAssessmentTitle());
                    bundle.putString("assessmentStartTxtView", selectedAssessment.getAssessmentStartDate());
                    bundle.putString("assessmentEndTxtView", selectedAssessment.getAssessmentEndDate());
                    bundle.putString("assessmentTypeTxtView", selectedAssessment.getAssessmentType());
                    bundle.putInt("associatedCourse", selectedAssessment.getAssociatedCourseId());
                    bundle.putInt("assessmentId", selectedAssessment.getAssessmentId());
                    bundle.putString("assessmentNotifyStart", selectedAssessment.getNotifyStartDate());
                    bundle.putString("assessmentNotifyEnd", selectedAssessment.getNotifyEndDate());
                    assessmentDetails.setArguments(bundle);
                    fragmentTransaction.replace(R.id.assessmentFragmentContainerView, assessmentDetails);
                    fragmentTransaction.addToBackStack("AssessmentDetailsFragmentView");
                    fragmentTransaction.commit();
                }
            });
        }
    }


    //Inflate the list item
    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentViewHolder(itemView);
    }


    //Put things on the text view
    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if(mAssessments!=null){
            Assessment selectedAssessment = mAssessments.get(position);
            String name = selectedAssessment.getAssessmentTitle();
            String startDate = selectedAssessment.getAssessmentStartDate();
            String endDate = selectedAssessment.getAssessmentEndDate();
            String type = selectedAssessment.getAssessmentType();
            holder.DBAssessmentTitle.setText(name);
            holder.DBAssessmentStartDate.setText(startDate);
            holder.DBAssessmentEndDate.setText(endDate);
            holder.DBAssessmentType.setText(type);
        }
        else{
            holder.DBAssessmentTitle.setText("No assessments found");
            holder.DBAssessmentStartDate.setText("N/A");
            holder.DBAssessmentEndDate.setText("N/A");
            holder.DBAssessmentType.setText("N/A");
        }
    }
    public void setmAssessments(List<Assessment> assessments){
        mAssessments=assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mAssessments!=null){
            return mAssessments.size();
        }
        else return 0;
    }


}
