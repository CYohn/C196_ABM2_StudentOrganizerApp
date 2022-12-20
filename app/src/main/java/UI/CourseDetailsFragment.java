package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.ArrayList;

import Database.RepositoryForStudentOrganizer;
import Entities.Term;


public class CourseDetailsFragment extends Fragment {



    public CourseDetailsFragment() {
        // Required empty public constructor
    }


    public static CourseDetailsFragment newInstance(String param1, String param2) {
        CourseDetailsFragment fragment = new CourseDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //String termName = Term.getTermTitle();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_details, container, false);
        RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());
        ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms(); //Get terms from repo, add them to the list
        Spinner termsSelectionSpinner = (Spinner) view.findViewById(R.id.associatedTermSpinner);

        ArrayAdapter<Term> termArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, termArrayList);
        termsSelectionSpinner.setAdapter(termArrayAdapter);


        Button addAssessmentBtn = (Button) view.findViewById(R.id.addAssessmentBtn);
        //TODO: SET THE COURSE INFO TO PASS
        addAssessmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewCourses, new AssessmentDetailsFragment());
                fragmentTransaction.addToBackStack("addAssessmentView");
                fragmentTransaction.commit();
            }
        });


        //TODO: SET THE COURSE INFO BUNDLE TO PASS
        ImageButton addInstructorBtn = (ImageButton) view.findViewById(R.id.addInstructorBtn);
        addInstructorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewCourses, new InstructorFragment());
                fragmentTransaction.addToBackStack("addInstructorView");
                fragmentTransaction.commit();
            }
    });


        //TODO: SET THE COURSE INFO BUNDLE TO PASS
        Button addNoteBtn = (Button) view.findViewById(R.id.addNoteBtn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewCourses, new AllNotesFragment());
                fragmentTransaction.addToBackStack("addNoteView");
                fragmentTransaction.commit();
            }
        });


    return view;
    }



}