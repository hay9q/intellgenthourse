package com.example.haydd.intelligenthourse;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class WeatherActivity extends FragmentActivity {

    private Fragment[] fragments;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioGroup radioGroup;
    private RadioButton firstButton;
    private RadioButton secondButton;
    private RadioButton thirdButton;
    private RadioButton lastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather);

       /* actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.getCustomView().findViewById(R.id.close)
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (Values.socket != null) {
						Values.socket.close();
						Values.socket = null;
						Toast.makeText(WeatherActivity.this, "已断连接", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(WeatherActivity.this, "没有连接", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});

		actionBar.getCustomView().findViewById(R.id.end)
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = new MyDialog(WeatherActivity.this);
				dialog.show();
			}
		});*/
        fragments = new Fragment[4];
        fragmentManager = getSupportFragmentManager();
        fragments[0] = fragmentManager.findFragmentById(R.id.first);
        fragments[1] = fragmentManager.findFragmentById(R.id.second);
        fragments[2] = fragmentManager.findFragmentById(R.id.third);
        fragments[3] = fragmentManager.findFragmentById(R.id.forth);


        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(fragments[0])
                .hide(fragments[1])
                .hide(fragments[2])
                .hide(fragments[3]);

        fragmentTransaction.show(fragments[0]).commit();
        initFragment();
    }

    public void initFragment(){
        radioGroup = (RadioGroup) findViewById(R.id.all);
        firstButton = (RadioButton)findViewById(R.id.left);
        secondButton = (RadioButton)findViewById(R.id.center);
        thirdButton = (RadioButton)findViewById(R.id.right);
        lastButton = (RadioButton)findViewById(R.id.last);


        radioGroup.check(firstButton.getId());
        firstButton.setTextColor(Color.WHITE);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(fragments[0])
                        .hide(fragments[1])
                        .hide(fragments[2])
                        .hide(fragments[3]);

                firstButton.setTextColor(Color.YELLOW);
                secondButton.setTextColor(Color.YELLOW);
                thirdButton.setTextColor(Color.YELLOW);
                lastButton.setTextColor(Color.YELLOW);

                switch (checkedId) {
                    case R.id.left:
                        fragmentTransaction.show(fragments[0]).commit();
                        firstButton.setTextColor(Color.WHITE);
                        break;
                    case R.id.center:
                        fragmentTransaction.show(fragments[1]).commit();
                        secondButton.setTextColor(Color.WHITE);
                        break;
                    case R.id.right:
                        fragmentTransaction.show(fragments[2]).commit();
                        thirdButton.setTextColor(Color.WHITE);
                        break;
                    case R.id.last:
                        fragmentTransaction.show(fragments[3]).commit();
                        lastButton.setTextColor(Color.WHITE);
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (Values.socket != null) {
                Values.socket.close();
            }
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }
}
