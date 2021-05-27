package com.depressionscreening.yjj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.ThreadUtils;
import com.depressionscreening.yjj.R;
import com.depressionscreening.yjj.base.BaseActivity;
import com.depressionscreening.yjj.databinding.ActivityLoginBinding;
import com.depressionscreening.yjj.utils.ShakeUtil;
import com.depressionscreening.yjj.utils.ToastTool;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private final int REGISTER_ACTIVITY_START_REQUEST_CODE = 1101;

    @Override
    protected void initListener() {
        binding.submitButtonLogin.setOnClickListener(v -> {
            String uid = Objects.requireNonNull(binding.tieUidLogin.getText()).toString();
            if(TextUtils.isEmpty(uid)){
                ToastTool.warning("请输入账号");
                ShakeUtil.shake(context, binding.tilUidLogin);
                binding.submitButtonLogin.showError(1200);
                binding.tieUidLogin.requestFocus();
                return;
            }
            String pwd = Objects.requireNonNull(binding.tiePwdLogin.getText()).toString();
            if(TextUtils.isEmpty(pwd)){
                ToastTool.warning("请输入密码");
                ShakeUtil.shake(context, binding.tilPwdLogin);
                binding.submitButtonLogin.showError(1200);
                binding.tiePwdLogin.requestFocus();
                return;
            }

        });
    }

    @Override
    protected void initData() {
        //默认选中
        binding.rgIdentityLogin.check(R.id.rb_doctor_login);
        //账号默认打开数字键盘，但不限制
        binding.tieUidLogin.setRawInputType(Configuration.KEYBOARD_QWERTY);
        //账号输入框自动获取焦点
        binding.tieUidLogin.requestFocus();
        // 延迟打开软键盘
        new Timer().schedule(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "注册");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                startActivityForResult(new Intent(context, RegisterActivity.class), REGISTER_ACTIVITY_START_REQUEST_CODE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case REGISTER_ACTIVITY_START_REQUEST_CODE:
                    if(data!=null){
                        String login_uid = data.getStringExtra("login_uid");
                        String login_pwd = data.getStringExtra("login_pwd");
                        binding.tieUidLogin.setText(login_uid);
                        binding.tiePwdLogin.setText(login_pwd);
                        ThreadUtils.runOnUiThreadDelayed(()-> binding.submitButtonLogin.performClick(), 200);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
