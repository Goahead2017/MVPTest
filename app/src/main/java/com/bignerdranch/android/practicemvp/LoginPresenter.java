package com.bignerdranch.android.practicemvp;

/**
 * P层接口
 */

public interface LoginPresenter {

    void controller(String schoolID,String cardID);
    void onDestroy();

}
