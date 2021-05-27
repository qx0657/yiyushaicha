package com.depressionscreening.yjj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.NumberPicker;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.depressionscreening.yjj.base.BaseActivity;
import com.depressionscreening.yjj.databinding.ActivityRegisterBinding;
import com.depressionscreening.yjj.model.UserModel;
import com.depressionscreening.yjj.utils.ShakeUtil;
import com.depressionscreening.yjj.utils.ToastTool;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    @Override
    protected void initView() {
        super.initView();
        setNumberPickerDividerColor(binding.npAgeRegister);
    }

    @Override
    protected void initListener() {
        binding.submitButtonRegister.setOnClickListener(v -> {
            String uid = Objects.requireNonNull(binding.tieUidRegister.getText()).toString();
            if(TextUtils.isEmpty(uid)){
                ToastTool.warning("请输入账号");
                ShakeUtil.shake(context, binding.tilUidRegister);
                binding.submitButtonRegister.showError(1200);
                binding.tieUidRegister.requestFocus();
                return;
            }
            String pwd = Objects.requireNonNull(binding.tiePwdRegister.getText()).toString();
            if(TextUtils.isEmpty(pwd)){
                ToastTool.warning("请输入密码");
                ShakeUtil.shake(context, binding.tilPwdRegister);
                binding.submitButtonRegister.showError(1200);
                binding.tiePwdRegister.requestFocus();
                return;
            }
            String name = Objects.requireNonNull(binding.tieNameRegister.getText()).toString();
            if(TextUtils.isEmpty(name)){
                ToastTool.warning("请输入姓名");
                ShakeUtil.shake(context, binding.tieNameRegister);
                binding.submitButtonRegister.showError(1200);
                binding.tieNameRegister.requestFocus();
                return;
            }
            UserModel.Sex sex = UserModel.Sex.UNKNOWN;
            if(binding.rgSexRegister.getCheckedRadioButtonId()==binding.rbMaleRegister.getId()){
                sex = UserModel.Sex.Male;
            }else if(binding.rgSexRegister.getCheckedRadioButtonId()==binding.rbFemaleRegister.getId()){
                sex = UserModel.Sex.Female;
            }
            if(sex == UserModel.Sex.UNKNOWN){
                ToastTool.warning("请选择性别");
                ShakeUtil.shake(context, binding.rgSexRegister);
                binding.submitButtonRegister.showError(1200);
                return;
            }
            new UserModel().register(uid, pwd, name, sex, binding.npAgeRegister.getValue(), new UserModel.OnRegisterListener() {
                @Override
                public void registerSuccess() {
                    binding.submitButtonRegister.showSucceed();
                    ThreadUtils.runOnUiThreadDelayed(()-> {
                        binding.submitButtonRegister.reset();
                        RegisterSuccess(uid, pwd);
                    }, 500);
                }

                @Override
                public void registerError(String e) {
                    ToastTool.error(e);
                    binding.submitButtonRegister.showError(1200);
                }
            });

        });
    }

    @Override
    protected void initData() {
        binding.npAgeRegister.setMaxValue(150);
        binding.npAgeRegister.setMinValue(1);
        binding.npAgeRegister.setValue(22);
        //账号默认打开数字键盘，但不限制
        binding.tieUidRegister.setRawInputType(Configuration.KEYBOARD_QWERTY);
        //账号输入框自动获取焦点
        binding.tieUidRegister.requestFocus();
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

    private void RegisterSuccess(String uid, String pwd){
        Intent intent = new Intent();
        intent.putExtra("login_uid", uid);
        intent.putExtra("login_pwd", pwd);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        try {
            //设置分割线的颜色值
            Field mSelectionDivider = NumberPicker.class.getDeclaredField("mSelectionDivider");
            mSelectionDivider.setAccessible(true);
            mSelectionDivider.set(numberPicker, new ColorDrawable(Color.parseColor("#888888")));
            //设置分割线高度
            Field mSelectionDividerHeight = NumberPicker.class.getDeclaredField("mSelectionDividerHeight");
            mSelectionDividerHeight.setAccessible(true);
            mSelectionDividerHeight.set(numberPicker, ConvertUtils.dp2px(1));
        } catch (IllegalArgumentException | IllegalAccessException | Resources.NotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
