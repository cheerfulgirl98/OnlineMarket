package com.sepideh.onlinemarket.second.comments;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseView;
import com.sepideh.onlinemarket.data.Comment;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by pc on 4/26/2019.
 */

public interface CommentsContract {

    interface MyView extends BaseView {
        void showAllComments(List<Comment> comments);
        void voteBefore();
        void conectionError();
        void userNotLogin();
    }

    interface MyPresenter extends BasePresenter<MyView> {
        void getAllComments(String id);
        void vote(String voteTag,String commentId,String userId);
        void attachRow(CommentsContract.MyRowView myRowView);

    }

    interface MyModel {
        Single<List<Comment>> getAllComments(String id);
        Completable vote(String voteTag, String commentId, String userId);
    }

    interface MyRowView{

        void successfulVote();
    }

}
