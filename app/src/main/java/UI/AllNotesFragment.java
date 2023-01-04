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
import Entities.Note;
import Entities.Term;


public class AllNotesFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    int courseId;

    public AllNotesFragment() {
        // Required empty public constructor
    }


    public static AllNotesFragment newInstance(String param1, String param2) {
        AllNotesFragment fragment = new AllNotesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_notes, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Set the recyclerView
        RecyclerView recyclerView = getView().findViewById(R.id.notesRecyclerList);
        RepositoryForStudentOrganizer.Repository repo =
                new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());
        List<Note> notes=repo.getAllNotes();
        final NoteAdapter noteAdapter= new NoteAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.setmNotes(notes);

        //Set the filter spinner
        ArrayList<Course> courseArrayList = (ArrayList<Course>) repo.getmAllCourses(); //Get terms from repo, add them to the list
        Spinner filterNotesSpinner = (Spinner) getView().findViewById(R.id.filterNotesSpinner);
        ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, courseArrayList);
        filterNotesSpinner.setAdapter(courseArrayAdapter);
        int coursePosition = getItemPosition(courseId, courseArrayList);
        System.out.println("Term Position: " + coursePosition);

        if (coursePosition != -1){
            filterNotesSpinner.setSelection(coursePosition);
        }else{filterNotesSpinner.setSelection(0);}


        floatingActionButton = getView().findViewById(R.id.addInstructorFloatingAction);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment noteDetails = new NoteDetailsFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.notesActivityFragmentViewer, noteDetails);
                fragmentTransaction.addToBackStack("NoteDetailsview");
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