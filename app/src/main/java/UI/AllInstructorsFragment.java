package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.List;

import Database.RepositoryForStudentOrganizer;
import Entities.Instructor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllInstructorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllInstructorsFragment extends Fragment {

    FloatingActionButton floatingActionButton;

    public AllInstructorsFragment() {
        // Required empty public constructor
    }


    public static AllInstructorsFragment newInstance() {
        AllInstructorsFragment fragment = new AllInstructorsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_instructors, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        RecyclerView recyclerView = getView().findViewById(R.id.allInstructorsRecyclerView);
        RepositoryForStudentOrganizer.Repository repo =
                new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());
        List<Instructor> instructors = repo.getmAllInstructors();
        final InstructorAdapter instructorAdapter = new InstructorAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(instructorAdapter);
        instructorAdapter.setmInstructor(instructors);

        floatingActionButton = getView().findViewById(R.id.addInstructorFloatingAction);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment instructorDetails = new InstructorDetailsFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.instructorActivityFragmentViewer, instructorDetails);
                fragmentTransaction.addToBackStack("InstructorDetailsview");
                fragmentTransaction.commit();
            }
        });
    }
}