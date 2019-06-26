package com.sepideh.onlinemarket.second.comments;

import com.sepideh.onlinemarket.data.Comment;
import com.sepideh.onlinemarket.utils.Constant;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by pc on 4/26/2019.
 */

public class CommentsModel implements CommentsContract.MyModel {
    @Override
    public Single<List<Comment>> getAllComments(String id) {
        return Constant.apiService.getAllComments(id);
    }

    @Override
    public Completable vote(String voteTag, String commentId, String userId) {
        return Constant.apiService.voteToComment(voteTag,commentId,userId);
    }
}
