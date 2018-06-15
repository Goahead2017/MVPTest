package com.bignerdranch.android.practicemvp;

/**
 * M层接口
 */

public interface LoginModel {

    void login(String schoolID,String cardID,LoginFinished listener);

}
