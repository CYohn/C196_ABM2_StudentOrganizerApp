package UI;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

    }

    public void pressedListAllTermsBtn(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.termsFragmentContainerView, new AllTermsListFragment());
        fragmentTransaction.addToBackStack("allTermsListFragmentView");
        fragmentTransaction.commit();
    }

    public void pressedAddTermsBtn(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.termsFragmentContainerView, new AddTermFragment());
        fragmentTransaction.addToBackStack("allTermsListFragmentView");
        fragmentTransaction.commit();
    }
}