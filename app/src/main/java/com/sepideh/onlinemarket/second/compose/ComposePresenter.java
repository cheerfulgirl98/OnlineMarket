package com.sepideh.onlinemarket.second.compose;

import java.io.IOException;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by pc on 5/9/2019.
 */

public class ComposePresenter implements ComposeContract.MyPresenter {

    ComposeContract.MyFragmentView myView;
    ComposeContract.MyModel myModel;

    String errorText;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ComposePresenter(ComposeContract.MyModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void attachView(ComposeContract.MyFragmentView view)
    {
        this.myView = view;
    }

    @Override
    public void detachView() {

        this.myView = null;
        if (compositeDisposable != null && compositeDisposable.size() > 0) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void sendComment(String productId, String userId, String star, String description) {
        myModel.sendComment(productId, userId, star, description).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {

                        myView.successfulCompose();
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
                            if (errorText.equals("you commented before")) {
                                myView.commentedBefore();
                            }
                        }
                        else if (e instanceof IOException)

                            myView.noServerConnection();
                    }
                });

    }
}
