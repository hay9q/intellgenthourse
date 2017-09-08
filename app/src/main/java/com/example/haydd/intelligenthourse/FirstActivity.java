package com.example.haydd.intelligenthourse;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


public class FirstActivity extends ActionBarActivity {

    private int[] image = {R.drawable.weather,R.drawable.lock};
    private String[] name = {"天气模块","视频监控"};
    private MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        GridView grid = (GridView)findViewById(R.id.grid);
        MyAdapter myAdapter = new MyAdapter(this,image,name);
        grid.setAdapter(myAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent();
                intent.setClass(FirstActivity.this,WeatherActivity.class);
                startActivity(intent);*/
                switch (position){
                    case 0:
                        Intent intent = new Intent();
                        intent.setClass(FirstActivity.this,WeatherActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent();
                        intent2.setClass(FirstActivity.this,MyMainFrm.class);
                        startActivity(intent2);
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialog = new MyDialog(FirstActivity.this);
            dialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyDialog extends AlertDialog implements DialogInterface.OnClickListener{

        protected MyDialog(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            setIcon(R.drawable.icoo);
            setTitle("提示");
            setMessage("您确定要退出intelligent house吗？");
            setButton(BUTTON_POSITIVE, "确定", this);
            setButton(BUTTON_NEUTRAL, "取消", this);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            switch (which) {
                case BUTTON_POSITIVE:
                    finish();
                    break;
                case BUTTON_NEUTRAL:
                    break;
                default:
                    break;
            }
        }

    }
}
