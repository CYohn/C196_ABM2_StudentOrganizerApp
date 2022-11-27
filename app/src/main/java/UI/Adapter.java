package UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.List;

import Entities.Term;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Term>mTerms;
    private final Context context;
    private final LayoutInflater mInflator;
    private Adapter(Context context){
        mInflator= LayoutInflater.from(context);
        this.context=context;
    }

    
    class ViewHolder extends RecyclerView.ViewHolder{
       private final TextView ListItemView;
       private ViewHolder(View itemView){
           super(itemView);
           ListItemView = itemView.findViewById(R.id.list_item);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int position=getAdapterPosition();
                   final Term selectedTerm=mTerms.get(position);
                   Intent intent = new Intent(context,TermDetailsActivity.class);

                   intent.putExtra("termTitleView", selectedTerm.getTermTitle());
                   intent.putExtra("termStartDateView", selectedTerm.getTermStartDate());
                   intent.putExtra("termEndDateView", selectedTerm.getTermEndDate());
                   context.startActivity(intent);


               }
           });
       }
    }



    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflator.inflate(R.layout.list_item_layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        if(mTerms!=null){
            Term selectedTerm=mTerms.get(position);
            String name=selectedTerm.getTermTitle();
            holder.ListItemView.setText(name);
        }
        else{
            holder.ListItemView.setText("No term name");
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
