package com.bignerdranch.android.practicemvp;

/**
 * P层
 */

public class LoginPresenterDo implements LoginPresenter,LoginFinished{

    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterDo(LoginView loginView){
        this.loginView = loginView;
        this.loginModel = new LoginModelDo();
    }

    @Override
    public void controller(String schoolID, String cardID) {
        if(loginView != null){
            loginView.showProgress();
        }

        loginModel.login(schoolID, cardID,this);

    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onFailure() {
        loginView.loginFailure();
        loginView.hideProgress();
    }

    @Override
    public void onSuccess() {
        loginView.loginSuccess();
        loginView.hideProgress();
    }
}
