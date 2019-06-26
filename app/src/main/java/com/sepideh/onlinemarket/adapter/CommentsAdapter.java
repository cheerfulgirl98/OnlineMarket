package com.sepideh.onlinemarket.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.Comment;

import java.util.List;

/**
 * Created by pc on 4/27/2019.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyHolder>  {
    List<Comment> commentsList;
    Context context;

    public CommentsAdapter(List<Comment> commentsList, Context context) {
        this.commentsList = commentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.comment_row,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.username.setText(commentsList.get(position).getUserName());
        holder.date.setText(commentsList.get(position).getDate());
        holder.description.setText(commentsList.get(position).getDescription());
//        holder.like.setText(commentsList.get(position).getLike());
      //  holder.dislike.setText(commentsList.get(position).getDislike());
        holder.simpleRatingBar.setRating(Float.parseFloat(commentsList.get(position).getStar()));
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView username,date,description;
        TextView like,dislike;
        SimpleRatingBar simpleRatingBar;
        public MyHolder(View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.txv_comments_username);
            date=itemView.findViewById(R.id.txv_comments_date);
            description=itemView.findViewById(R.id.txv_comments_description);
         //   like=itemView.findViewById(R.id.txv_comments_likeV);
           // dislike=itemView.findViewById(R.id.txv_comments_dislikeV);
            simpleRatingBar=itemView.findViewById(R.id.star_comments);
        }
    }
}
