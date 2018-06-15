package com.bignerdranch.android.practicemvp;

/**
 * V层接口
 */

public interface LoginView {

    void showProgress();
    void hideProgress();
    void loginFailure();
    void loginSuccess();

}
