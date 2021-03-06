package com.yohoho.pxman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author Zaaach on 2015/12/6.
 * 灵感来自：http://androidpixels.net/
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.id_main_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.id_btn_convert)
    Button mConvertBtn;

    private MainAdapter mAdapter;
    private EditText currentEditText;

    private int currentDpiType = DpiType.LDPI;
    private boolean isDpValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mConvertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (currentEditText == null) return;
                String input = currentEditText.getText().toString();
                Log.e("onClick", "当前输入数值： " + input);
                if (TextUtils.isEmpty(input)){
                    Toast.makeText(MainActivity.this, "请输入数值", Toast.LENGTH_SHORT).show();
                    return;
                }
                double mdpiValue = isDpValue ? Double.valueOf(input) : getMdpiValue(Double.valueOf(input), currentDpiType);
                mAdapter.changeDataByMdpiValue(mdpiValue);
            }
        });
    }

    private void initData() {
        mAdapter = new MainAdapter(this);
        mAdapter.setOnFocusedListener(new MainAdapter.OnFocusedListener() {
            @Override
            public void onFocused(EditText editText, boolean hasFocus, int dpiType, boolean isDp) {
                currentEditText = editText;
                currentDpiType = dpiType;
                isDpValue = isDp;
                Log.e("onFocused", hasFocus + "");
            }
        });
    }

    private double getMdpiValue(double inputValue, int dpi){
        double result = inputValue;
        switch (dpi){
            case DpiType.LDPI:
                result = inputValue * 4/3;
                break;
            case DpiType.MDPI:
                break;
            case DpiType.HDPI:
                result = inputValue * 2/3;
                break;
            case DpiType.XHDPI:
                result = inputValue / 2;
                break;
            case DpiType.XXHDPI:
                result = inputValue / 3;
                break;
            case DpiType.XXXHDPI:
                result = inputValue / 4;
                break;
        }
        return result;
    }
}
