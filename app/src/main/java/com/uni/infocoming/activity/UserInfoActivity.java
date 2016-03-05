package com.uni.infocoming.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.uni.infocoming.BaseActivity;
import com.uni.infocoming.R;
import com.uni.infocoming.utils.TitleBuilder;
import com.uni.infocoming.utils.ToastUtils;

public class UserInfoActivity extends BaseActivity {

    private TextView tv_showdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();
    }

    private void initView() {
        new TitleBuilder(this)
                .setLeftImage(R.mipmap.logo);
        tv_showdialog = (TextView) findViewById(R.id.tv_showdialog);
    }

    public void showdialog(View view){
        final EditText et = new EditText(this);

        new MaterialDialog.Builder(this)
                .title("Demo")
                .content("this is a demo dialog")
                .positiveText("ok")
                .negativeText("cancel")
                .items(R.array.list)
                .itemsCallback(new MaterialDialog.ListCallback(){

                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        ToastUtils.showToast(UserInfoActivity.this,charSequence,0);
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback(){

                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {

                        ToastUtils.showToast(UserInfoActivity.this,materialDialog+""+dialogAction,0);
                    }
                })
                .show();

    }
}
