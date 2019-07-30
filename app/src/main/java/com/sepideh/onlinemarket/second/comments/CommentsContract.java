package com.sepideh.onlinemarket.second.comments;

import com.sepideh.onlinemarket.base.BasePresenter;
import com.sepideh.onlinemarket.base.BaseFragmentView;
import com.sepideh.onlinemarket.data.Comment;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by pc on 4/26/2019.
 */

public interface CommentsContract {

    interface MyFragmentView extends BaseFragmentView {
        void showAllComments(List<Comment> comments);
        void voteBefore();

        void userNotLogin();
    }

    interface MyPresenter extends BasePresenter<MyFragmentView> {
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
