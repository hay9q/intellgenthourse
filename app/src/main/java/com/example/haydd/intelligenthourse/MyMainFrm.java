package com.example.haydd.intelligenthourse;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;

public class MyMainFrm extends Activity {

	EditText CameraIP,ControlIP,Port;
	Button Button_go;
	String videoUrl,controlUrl,port;
	public static String CameraIp;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mymainfrm);	
		
		CameraIP = (EditText) findViewById(R.id.editIP);
		ControlIP = (EditText) findViewById(R.id.ip);
		Port = (EditText) findViewById(R.id.port);
		
		Button_go = (Button) findViewById(R.id.button_go);
		
		videoUrl = CameraIP.getText().toString();
		controlUrl = ControlIP.getText().toString();
		port = Port.getText().toString();
		
		Button_go.requestFocusFromTouch();

        
		Button_go.setOnClickListener(new Button.OnClickListener() 
		{
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent intent = new Intent();
    			intent.putExtra("CameraIp", videoUrl);
    			intent.putExtra("ControlUrl", controlUrl);
    			intent.putExtra("Port", port);
    			
    			intent.putExtra("Is_Scale", true);
    			intent.setClass(MyMainFrm.this, MyVideo.class);
    			MyMainFrm.this.startActivity(intent);
    			finish();  
	            System.exit(0);  
    		}
    	});
    
	}

}


