package UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

import org.jetbrains.annotations.Nullable;

public class TermDetailsFragment extends Fragment {

    //private final Context context;

    private String termTitle;
    private String termStart;
    private String termEnd;
    private int termId;

    ImageButton closeBtn;
    EditText nameEditText;
    Button startBtn;
    Button endBtn;
    ImageButton saveBtn;
    ImageButton nextBtn;







    public TermDetailsFragment() {
        // Required empty public constructor
    }


//    public static TermDetailsFragment newInstance(final String param1, final String param2) {
//        final TermDetailsFragment fragment = new TermDetailsFragment();
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            termTitle = bundle.getString("termTitleValue");
            termStart = bundle.getString("termStartDateValue");
            termEnd = bundle.getString("termEndDateValue");
            termId = bundle.getInt("termId");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_term_details, container, false);

        return view;
    }


    public  void onViewCreated(final View view, @Nullable final Bundle savedInstanceState){
        Bundle bundle = getArguments();

        closeBtn = (ImageButton) getView().findViewById(R.id.closeAddTermsBtn);
        nameEditText = (EditText) getView().findViewById(R.id.termNameEditText);
        startBtn = (Button) getView().findViewById(R.id.termStartBtn);
        endBtn = (Button) getView().findViewById(R.id.termEndBtn);
        saveBtn = (ImageButton) getView().findViewById(R.id.termSaveBtn);
        nextBtn = (ImageButton) getView().findViewById(R.id.addTermNextBtn);

        if (bundle != null){
            nameEditText.setText(termTitle, TextView.BufferType.EDITABLE);
            startBtn.setText("Start: " + termStart);
            endBtn.setText("End: " + termEnd);
        }
        else{
            nameEditText.setText("Please Enter the Term Name(ex: Spring 2022)");
            startBtn.setText("Start Date");
            endBtn.setText("End Date");
        }

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment termList = new AllTermsListFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.termsFragmentContainerView, termList);
                fragmentTransaction.addToBackStack("TermListFragmentView");
                fragmentTransaction.commit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),CourseActivity.class);
                intent.putExtra("associatedTerm", termId);
                Context context = getContext();
                context.startActivity(intent);
            }

        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    
    //TODO: Make this button save the new Term, add it to the list of terms, and return to the All Terms fragment
    public void pressedSaveNewTermBtn(View view) {
    }


}