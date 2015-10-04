package net.nikonorov.translator.translator;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by vitaly on 30.09.15.
 */
public class RVSettingAdapter extends RecyclerView.Adapter<RVSettingAdapter.CardViewHolder> {


    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        CardView cv;
        TextView dirs;
        ImageView langFrom;
        ImageView langTo;
        CardViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView)itemView.findViewById(R.id.cv);
            dirs = (TextView)itemView.findViewById(R.id.lang_direction);
            langFrom = (ImageView)itemView.findViewById(R.id.lang_from);
            langTo = (ImageView)itemView.findViewById(R.id.lang_to);
        }

        @Override
        public void onClick(View view) {
            Settings.DIRECTION = dirs.getText().toString();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "You select "+Settings.DIRECTION, duration);
            toast.show();

        }
    }

    List<Direction> directions;
    static Context context;

    RVSettingAdapter(List<Direction> persons, Context context){
        this.directions = persons;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.settings_card, viewGroup, false);
        CardViewHolder pvh = new CardViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder personViewHolder, int i) {
        personViewHolder.dirs.setText(directions.get(i).direction);
        personViewHolder.langFrom.setImageResource(directions.get(i).langFrom);
        personViewHolder.langTo.setImageResource(directions.get(i).langTo);
    }

    @Override
    public int getItemCount() {
        return directions.size();
    }

}
