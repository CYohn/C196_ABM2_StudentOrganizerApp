package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.List;

import Database.RepositoryForStudentOrganizer;
import Entities.Term;


public class AllTermsListFragment extends Fragment {


    public AllTermsListFragment() {
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
        return inflater.inflate(R.layout.fragment_all_terms_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = getView().findViewById(R.id.termRecyclerView);
        RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());
        List<Term>terms=repo.getmAllTerms();
        final TermsAdapter termsAdapter= new TermsAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(termsAdapter);
        termsAdapter.setTerms(terms);
    }


}