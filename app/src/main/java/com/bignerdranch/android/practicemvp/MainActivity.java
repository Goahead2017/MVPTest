package com.bignerdranch.android.practicemvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,LoginView {

    private ProgressBar progressBar;
    private EditText et1;
    private EditText et2;
    private Button button;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //让第一个EditText首先获取焦点
        et1 = findViewById(R.id.et1);
        et1.setFocusable(true);
        et1.setFocusableInTouchMode(true);
        et1.requestFocus();
        et1.requestFocusFromTouch();

        et2 = findViewById(R.id.et2);

        //设置点击实现登录操作
        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        //动态加载进度条
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        loginPresenter = new LoginPresenterDo(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                String schoolID = et1.getText().toString();
                String cardID = et2.getText().toString();
                loginPresenter.controller(schoolID,cardID);
                break;
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loginFailure() {
        Toast.makeText(this,"账号或密码错误请重新输入",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
    }
}
