package UI;

import android.content.Context;
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

import Entities.Instructor;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {

    private List<Instructor> mInstructor;
    private final Context context;
    private final LayoutInflater mInflator;
    InstructorAdapter(Context context){
        mInflator= LayoutInflater.from(context);
        this.context=context;
    }


    class InstructorViewHolder extends RecyclerView.ViewHolder{
        private final TextView DBInstructorName;
        private final TextView DBInstructorPhone;
        private final TextView DBInstructorEmail;


        private InstructorViewHolder(View itemView){
            super(itemView);
            DBInstructorName = itemView.findViewById(R.id.instructorNameCardView);
            DBInstructorEmail = itemView.findViewById(R.id.instructorEmailCardView);
            DBInstructorPhone = itemView.findViewById(R.id.instructorPhoneCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Instructor selectedInstructor= mInstructor.get(position);

                    Fragment instructorDetails = new InstructorDetailsFragment();
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();

                    Bundle bundle = new Bundle();

                    bundle.putString("instructorNameValue", selectedInstructor.getInstructorName());
                    bundle.putString("instructorEmailValue", selectedInstructor.getInstructorEmail());
                    bundle.putString("instructorPhoneValue", selectedInstructor.getInstructorPhone());
                    bundle.putInt("instructorIdValue", selectedInstructor.getInstructorId());

                    instructorDetails.setArguments(bundle);
                    fragmentTransaction.replace(R.id.instructorActivityFragmentViewer, instructorDetails);
                    fragmentTransaction.addToBackStack("InstructorDetailFragment");
                    fragmentTransaction.commit();
                }
            });
        }
    }


    //Inflate the list item
    @NonNull
    @Override
    public InstructorAdapter.InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflator.inflate(R.layout.instructor_list_item_layout,parent,false);
        return new InstructorViewHolder(itemView);
    }

    //Put things on the text view
    @Override
    public void onBindViewHolder(@NonNull InstructorAdapter.InstructorViewHolder holder, int position) {
        if(mInstructor !=null){
            Instructor selectedInstructor = mInstructor.get(position);
            String name = selectedInstructor.getInstructorName();
            String email = selectedInstructor.getInstructorEmail();
            String phone = selectedInstructor.getInstructorPhone();

            holder.DBInstructorName.setText(name);
            holder.DBInstructorPhone.setText(phone);
            holder.DBInstructorEmail.setText(email);

        }
        else{
            holder.DBInstructorName.setText("No Instructor Found");
            holder.DBInstructorPhone.setText("Phone");
            holder.DBInstructorEmail.setText("Email");
        }
    }
    public void setmInstructor(List<Instructor> instructors){
        mInstructor =instructors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mInstructor !=null){
            return mInstructor.size();
        }
        else return 0;
    }

}
