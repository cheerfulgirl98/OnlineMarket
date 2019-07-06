package com.sepideh.onlinemarket.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.UserInfo;
import com.sepideh.onlinemarket.second.comments.CommentsContract;
import com.sepideh.onlinemarket.data.Comment;

import java.util.List;

/**
 * Created by pc on 5/5/2019.
 */

public class CommentsDetailAdapter extends RecyclerView.Adapter<CommentsDetailAdapter.MyHolder> implements CommentsContract.MyRowView {

    private CommentsContract.MyPresenter myPresenter;
    private List<Comment> comments;
    private Context mContext;

    private String userId;
    private Comment clickedComment;
    private int clickedPosition;

    private String voteTag;


    public CommentsDetailAdapter(CommentsContract.MyPresenter myPresenter,List<Comment> comments, Context mContext) {
        this.myPresenter = myPresenter;
        this.comments = comments;
        this.mContext = mContext;
        UserInfo userInfo=Hawk.get(mContext.getString(R.string.loginUserInfoTag));
        if(userInfo!=null)
        userId= userInfo.getId();
        myPresenter.attachRow(this);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_comment_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        holder.username.setText(comments.get(position).getUserName());
        holder.date.setText(comments.get(position).getDate());
        holder.description.setText(comments.get(position).getDescription());
        holder.likeV.setText(comments.get(position).getLike());
        holder.dislikeV.setText(comments.get(position).getDislike());
        holder.simpleRatingBar.setRating(Float.parseFloat(comments.get(position).getStar()));
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedComment=comments.get(position);
                clickedPosition=position;
                String commentId=clickedComment.getId();
                voteTag="like";
                myPresenter.vote(voteTag,commentId,userId);
//                int like= Integer.parseInt(comments.get(position).getLike())+1;
//                comments.get(position).setLike(String.valueOf(like));
//                notifyItemChanged(position);
            }
        });

        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedComment=comments.get(position);
                clickedPosition=position;
                String commentId=clickedComment.getId();

                voteTag="dislike";

                myPresenter.vote(voteTag,commentId,userId);


            }
        });
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public void successfulVote() {
        if(voteTag.equals("like")){
            int like= Integer.parseInt(clickedComment.getLike())+1;
            comments.get(clickedPosition).setLike(String.valueOf(like));

        }
        else {
        int dislike= Integer.parseInt(clickedComment.getDislike())+1;
        comments.get(clickedPosition).setDislike(String.valueOf(dislike));}
        notifyDataSetChanged();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView username, date, description;
        ImageView like,dislike;
        TextView likeV, dislikeV;
        SimpleRatingBar simpleRatingBar;

        public MyHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.txv_comments_username);
            date = itemView.findViewById(R.id.txv_comments_date);
            description = itemView.findViewById(R.id.txv_comments_description);
            like=itemView.findViewById(R.id.img_comments_like);
            dislike=itemView.findViewById(R.id.img_comments_dislike);
            likeV=itemView.findViewById(R.id.txv_comments_likeV);
            dislikeV=itemView.findViewById(R.id.txv_comments_dislikeV);
            simpleRatingBar = itemView.findViewById(R.id.star_comments);

        }
    }

//    public onClick onClickInstance;
//    public interface onClick{
//         void onLikeClicked(String voteTag,String commentId);
//    }
}
