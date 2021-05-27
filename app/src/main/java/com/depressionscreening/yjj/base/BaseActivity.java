package com.depressionscreening.yjj.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.blankj.utilcode.util.LogUtils;
import com.depressionscreening.yjj.R;
import com.depressionscreening.yjj.databinding.ActivityMainBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;


public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    protected Context context;
    protected T binding;
    protected Menu menu;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        if(binding != null){
            setContentView(binding.getRoot());
        }else {
            throw new IllegalStateException("反射加载Binding失败");
        }
        context = this;
        initView();
        initListener();
        initData();
    }

    @SuppressWarnings("unchecked")
    protected T getBinding(){
        try{
            Class<T> tClass = (Class<T>)((ParameterizedType) Objects.requireNonNull(getClass().getGenericSuperclass())).getActualTypeArguments()[0];
            Method method = tClass.getMethod("inflate", LayoutInflater.class);
            return (T) method.invoke(null, getLayoutInflater());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            LogUtils.e(e.toString());
            return null;
        }
    }

    protected void initView(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null && !(binding instanceof ActivityMainBinding) && isDefaultShowBackButton()){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    protected boolean isDefaultShowBackButton() {
        return true;
    }

    protected abstract void initListener();

    protected abstract void initData();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
