package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.zybooks.c196_abm2_charity_yohn.R;

public class TermDetailsFragment extends Fragment {

    public TermDetailsFragment() {
        // Required empty public constructor
    }


    public static TermDetailsFragment newInstance(String param1, String param2) {
        TermDetailsFragment fragment = new TermDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_term_details, container, false);;


        

        return view;
    }
    
    //TODO: Make this button save the new Term, add it to the list of terms, and return to the All Terms fragment
    public void pressedSaveNewTermBtn(View view) {
    }
}