package com.alexz.donotdisturb.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexz.donotdisturb.R;
import com.alexz.donotdisturb.TriggerItem;

import java.util.List;

/**
 * Created by alex on 14.10.15.
 */
public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<TriggerItem> triggerItems;

    public RecyclerViewAdapter(List<TriggerItem> records) {
        this.triggerItems = records;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trigger_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TriggerItem triggerItem = triggerItems.get(i);
        viewHolder.title.setText(triggerItem.title);
    }

    @Override
    public int getItemCount() {
        return triggerItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}