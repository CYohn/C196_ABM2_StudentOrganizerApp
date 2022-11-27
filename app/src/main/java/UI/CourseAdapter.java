package UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.List;

import Entities.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

    private List<Course>mCourses;
    private final Context context;
    private final LayoutInflater mInflator;
    CourseAdapter(Context context){
        mInflator= LayoutInflater.from(context);
        this.context=context;
    }


    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView DBCourseTitle;
        private final TextView DBCourseStartDate;
        private final TextView DBCourseEndDate;
        private final TextView DBCourseInstructor;

        private CourseViewHolder(View itemView){
            super(itemView);
            DBCourseTitle = itemView.findViewById(R.id.courseTitleItemView);
            DBCourseStartDate = itemView.findViewById(R.id.courseStartItemView);
            DBCourseEndDate = itemView.findViewById(R.id.courseEndItemView);
            DBCourseInstructor = itemView.findViewById(R.id.courseInstructorItemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Course selectedCourse=mCourses.get(position);
                    Intent intent = new Intent(context,CourseDetailsActivity.class);

                    intent.putExtra("courseTitleTxtView", selectedCourse.getCourseTitle());
                    intent.putExtra("courseStartTxtView", selectedCourse.getCourseStartDate());
                    intent.putExtra("courseEndTxtView", selectedCourse.getCourseEndDate());
                    intent.putExtra("courseInstructorTxtView", selectedCourse.getCourseInstructor());
                    //intent.putExtra("courseStatus")
                    context.startActivity(intent);


                }
            });
        }
    }


    //Inflate the list item
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflator.inflate(R.layout.course_list_item_layout,parent,false);
        return new CourseViewHolder(itemView);
    }

    //Put things on the text view
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses!=null){
            Course selectedCourse = mCourses.get(position);
            String name = selectedCourse.getCourseTitle();
            String startDate = selectedCourse.getCourseStartDate();
            String endDate = selectedCourse.getCourseEndDate();
            String instructor = selectedCourse.getCourseInstructor();
            holder.DBCourseTitle.setText(name);
            holder.DBCourseStartDate.setText(startDate);
            holder.DBCourseEndDate.setText(endDate);
            holder.DBCourseInstructor.setText(instructor);
        }
        else{
            holder.DBCourseTitle.setText("No Course Found");
            holder.DBCourseStartDate.setText("N/A");
            holder.DBCourseEndDate.setText("N/A");
            holder.DBCourseInstructor.setText("N/A");
        }
    }
    public void setmCourses(List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourses!=null){
            return mCourses.size();
        }
        else return 0;
    }

}
