package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import Database.RepositoryForStudentOrganizer;
import Entities.Course;
import Entities.Instructor;
import Entities.Term;


public class AllCoursesListFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    Spinner filterCoursesSpinner;
    RepositoryForStudentOrganizer.Repository repo;
    int termId;
    ArrayList termArrayList;

    public AllCoursesListFragment() {
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
        return inflater.inflate(R.layout.fragment_all_courses_list, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Set the recyclerView
        RecyclerView recyclerView = getView().findViewById(R.id.courseRecyclerView);
        RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer
                .Repository(requireActivity().getApplication());
        List<Course> courses=repo.getmAllCourses();
        final CourseAdapter courseAdapter= new CourseAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.setmCourses(courses);

        //Set the filter spinner
        ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms(); //Get terms from repo, add them to the list
        Spinner filterCoursesSpinner = (Spinner) getView().findViewById(R.id.filterCoursesSpinner);
        ArrayAdapter<Term> termArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, termArrayList);
        filterCoursesSpinner.setAdapter(termArrayAdapter);
        int termPosition = getItemPosition(termId, termArrayList);
        System.out.println("Term Position: " + termPosition);

        if (termPosition != -1){
            filterCoursesSpinner.setSelection(termPosition);
        }else{filterCoursesSpinner.setSelection(0);}

        floatingActionButton = getView().findViewById(R.id.addCourseFloatingAction);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment courseDetails = new CourseDetailsFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewCourses, courseDetails);
                fragmentTransaction.addToBackStack("CourseDetailsView");
                fragmentTransaction.commit();
            }
        });
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
}