package com.sepideh.onlinemarket.second.comments;

import com.sepideh.onlinemarket.data.Comment;

import java.io.IOException;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by pc on 4/26/2019.
 */

public class CommentsPresenter implements CommentsContract.MyPresenter{
    CommentsContract.MyView myView;
    CommentsContract.MyModel myModel;

    CommentsContract.MyRowView myRowView;

    CompositeDisposable compositeDisposable=new CompositeDisposable();
    String errorText;

    public CommentsPresenter(CommentsContract.MyModel myModel) {
        this.myModel = myModel;
    }


    @Override
    public void attachView(CommentsContract.MyView view) {


        this.myView=view;


    }

    @Override
    public void detachView() {

        this.myView=null;
        if (compositeDisposable != null && compositeDisposable.size() > 0) {
            compositeDisposable.clear();
        }
    }


    @Override
    public void getAllComments(String id) {
        myModel.getAllComments(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Comment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Comment> comments) {

                        myView.showAllComments(comments);
                    }

                    @Override
                    public void onError(Throwable e)
                    {


                    }
                });
    }

    @Override
    public void vote(String voteTag,String commentId,String userId) {
      if(userId!=null) {
          myModel.vote(voteTag, commentId, userId).subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new CompletableObserver() {
                      @Override
                      public void onSubscribe(Disposable d) {
                          compositeDisposable.add(d);
                      }

                      @Override
                      public void onComplete() {


                          myRowView.successfulVote();
                      }

                      @Override
                      public void onError(Throwable e) {

                          if (e instanceof HttpException) {
                              ResponseBody responseBody = ((HttpException) e).response().errorBody();

                              try {
                                  errorText = responseBody.string();
                              } catch (IOException e1) {
                                  e1.printStackTrace();
                              }


                              if (errorText.equals("you vote before"))
                                  myView.voteBefore();

                          } else if (e instanceof IOException) {

                              myView.conectionError();
                          }
                      }
                  });

      }else myView.userNotLogin();

    }

    @Override
    public void attachRow(CommentsContract.MyRowView myRowView) {
        this.myRowView=myRowView;
    }
}
