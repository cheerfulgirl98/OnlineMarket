package com.sepideh.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.NotifItem;

import java.util.List;
import java.util.zip.Inflater;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyHolder> {

    List<NotifItem> notifItems;
    Context context;

    public MessagesAdapter(List<NotifItem> notifItems, Context context) {
        this.notifItems = notifItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.message_row,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.title.setText(notifItems.get(position).getTitle());
        holder.message.setText(notifItems.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return notifItems.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView title,message;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.txv_message_title);
            message=itemView.findViewById(R.id.txv_message_content);
        }
    }
}
