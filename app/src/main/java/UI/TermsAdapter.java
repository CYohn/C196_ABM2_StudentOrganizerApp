package UI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.List;

import Entities.Term;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermsViewHolder> {

    private List<Term>mTerms;
    private final Context context;
    private final LayoutInflater mInflator;

    TermsAdapter(Context context){
        mInflator= LayoutInflater.from(context);
        this.context=context;
    }


    class TermsViewHolder extends RecyclerView.ViewHolder{
       private final TextView DBTermTitle;
       private final TextView DBTermStartDate;
       private final TextView DBTermEndDate;


       private TermsViewHolder(View itemView){
           super(itemView);
           DBTermTitle = itemView.findViewById(R.id.dbTermTitle);
           DBTermStartDate = itemView.findViewById(R.id.dbTermStartDate);
           DBTermEndDate = itemView.findViewById(R.id.dbTermEndDate);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int position=getAdapterPosition();
                   final Term selectedTerm = mTerms.get(position);

                   Fragment termDetails = new TermDetailsFragment();
                   FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();

                   Bundle bundle = new Bundle();
                   bundle.putString("termTitleValue", selectedTerm.getTermTitle());
                   bundle.putString("termStartDateValue", selectedTerm.getTermStartDate());
                   bundle.putString("termEndDateValue", selectedTerm.getTermEndDate());
                   bundle.putInt("termId", (selectedTerm.getTermId()));
                   termDetails.setArguments(bundle);
                   fragmentTransaction.replace(R.id.termsFragmentContainerView, termDetails);
                   fragmentTransaction.addToBackStack("TermDetailsFragmentView");
                   fragmentTransaction.commit();
               }
           });
       }
    }


    //Inflate the list item
    @NonNull
    @Override
    public TermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.term_list_item_layout,parent,false);
        return new TermsViewHolder(itemView);
    }

    //Put things on the text view

    @Override
    public void onBindViewHolder(@NonNull TermsViewHolder holder, int position) {
        if(mTerms!=null){
            Term selectedTerm = mTerms.get(position);
            String nameValue = selectedTerm.getTermTitle();
            String startDateValue = selectedTerm.getTermStartDate();
            String endDateValue = selectedTerm.getTermEndDate();
            holder.DBTermTitle.setText(nameValue);
            holder.DBTermStartDate.setText(startDateValue);
            holder.DBTermEndDate.setText(endDateValue);
        }
        else{
            holder.DBTermTitle.setText("No Term Found");
            holder.DBTermStartDate.setText("N/A");
            holder.DBTermEndDate.setText("N/A");
        }
    }
    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mTerms!=null){
            return mTerms.size();
        }
        else return 0;
    }

}
