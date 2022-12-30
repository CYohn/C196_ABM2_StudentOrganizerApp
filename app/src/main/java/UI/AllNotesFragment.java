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
import Entities.Note;


public class AllNotesFragment extends Fragment {

    FloatingActionButton floatingActionButton;

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
        RecyclerView recyclerView = getView().findViewById(R.id.notesRecyclerList);
        RepositoryForStudentOrganizer.Repository repo =
                new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());
        List<Note> notes=repo.getAllNotes();
        final NoteAdapter noteAdapter= new NoteAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(noteAdapter);
        noteAdapter.setmNotes(notes);


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
}