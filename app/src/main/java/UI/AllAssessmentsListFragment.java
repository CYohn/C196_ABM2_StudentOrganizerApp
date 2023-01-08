package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Database.RepositoryForStudentOrganizer;
import Entities.Assessment;
import Entities.Course;
import Entities.Instructor;
import Entities.Term;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AllAssessmentsListFragment#newInstance} factory method to
// * create an instance of this fragment.
 //*/
public class AllAssessmentsListFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    int courseId;


    public AllAssessmentsListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_assessments_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Set the recyclerView
        RecyclerView recyclerView = getView().findViewById(R.id.assessmentsRecyclerView);
        RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());
        List<Assessment> assessments = repo.getmAllAssessments();
        final AssessmentAdapter assessmentAdapter= new AssessmentAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(assessmentAdapter);
        assessmentAdapter.setmAssessments(assessments);


        //Set the filter spinner
        ArrayList<Course> courseArrayList = (ArrayList<Course>) repo.getmAllCourses(); //Get terms from repo, add them to the list
        //Add a choice at index 0 to show all assessments
        Course indexZeroChoice = new Course(-1, "Show All (Or Choose a Course to Filter)", "", "", "", "", -1, -1, "", "");
        courseArrayList.add(0, indexZeroChoice);
        Spinner filterAssessmentsSpinner = (Spinner) getView().findViewById(R.id.filterAssessmentsSpinner);
        ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, courseArrayList);
        filterAssessmentsSpinner.setAdapter(courseArrayAdapter);
        int coursePosition = getItemPosition(courseId, courseArrayList);
        System.out.println("Course Position: " + coursePosition);

        if (coursePosition != -1){
            filterAssessmentsSpinner.setSelection(coursePosition);
        }else{filterAssessmentsSpinner.setSelection(0);}


        floatingActionButton = getView().findViewById(R.id.addAssessmentFloatingAction);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment assessmentDetails = new AssessmentDetailsFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.assessmentFragmentContainerView, assessmentDetails);
                fragmentTransaction.addToBackStack("AssessmentDetailsView");
                fragmentTransaction.commit();
            }
        });

        filterAssessmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Course selectedCourse = (Course) filterAssessmentsSpinner.getSelectedItem();
                int courseId = selectedCourse.getCourseId();
                ArrayList<Assessment> assessments = (ArrayList<Assessment>) repo.getmAllAssessments();

                if(courseId == -1){//No need to run the filter, just load all assessments
                    RecyclerView recyclerView = getView().findViewById(R.id.assessmentsRecyclerView);
                    //RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());
                    final AssessmentAdapter assessmentAdapter= new AssessmentAdapter(getContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(assessmentAdapter);
                    assessmentAdapter.setmAssessments(assessments);
                }else{
                    //Set the recyclerView to hold the new array
                    RecyclerView recyclerView = getView().findViewById(R.id.assessmentsRecyclerView);
                    ArrayList<Assessment> filteredAssessments = filterAssessments(courseId, assessments);
                    final AssessmentAdapter assessmentAdapter= new AssessmentAdapter(getContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(assessmentAdapter);
                    assessmentAdapter.setmAssessments(filteredAssessments);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { // Just load all assessment
                //set the recyclerView to hold an array of all Assessments
                RecyclerView recyclerView = getView().findViewById(R.id.assessmentsRecyclerView);
                RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());
                List<Assessment> assessments = repo.getmAllAssessments();
                final AssessmentAdapter assessmentAdapter= new AssessmentAdapter(getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(assessmentAdapter);
                assessmentAdapter.setmAssessments(assessments);
            }
        });



    }

    public void addAssessmentPressed(View view) {
    }


    public int getItemPosition(int id, ArrayList arrayList) {
        if(arrayList.isEmpty() != true) {
            if (arrayList.get(0) instanceof Term) {
                for (int i = 0; i < arrayList.size(); i++) {
                    Term term = (Term) arrayList.get(i);
                    if (term.getTermId() == id) {
                        int index = arrayList.indexOf(term);
                        return index;
                    }
                }
            }
        }
        return 0;
    };

    ArrayList<Assessment> filterAssessments(int courseId, ArrayList arrayList){
        ArrayList<Assessment> filteredAssessments = new ArrayList<>();

        for(int i = 0; i < arrayList.size(); i++){
            Assessment assessment = (Assessment) arrayList.get(i);
            if(assessment.getAssociatedCourseId() == courseId){
                filteredAssessments.add(assessment);
                return filteredAssessments;
            }
        }
        return filteredAssessments;
    }



}
