package com.depressionscreening.yjj.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.depressionscreening.yjj.base.BaseActivity;
import com.depressionscreening.yjj.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private final int LOGIN_ACTIVITY_START_REQUEST_CODE = 1001;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if(!SPUtils.getInstance().getBoolean("token")){
            login();
        }
    }

    private void login(){
        startActivityForResult(new Intent(context, LoginActivity.class), LOGIN_ACTIVITY_START_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case LOGIN_ACTIVITY_START_REQUEST_CODE:

                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "登录");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                login();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}