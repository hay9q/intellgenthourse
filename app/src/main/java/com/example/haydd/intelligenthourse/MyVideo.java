package com.example.haydd.intelligenthourse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;


import com.example.haydd.intelligenthourse.R;
import android.R.color;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MyVideo extends Activity
{
    private Button TakePhotos;

	public static String CameraIp;
	public static String CtrlIp;
	public static String CtrlPort;
    MySurfaceView r;
    private  Socket socket;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.myvideo);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        r = (MySurfaceView)findViewById(R.id.mySurfaceViewVideo);
        TakePhotos = (Button)findViewById(R.id.TakePhoto);
        
		Intent intent = getIntent();

		CameraIp = intent.getStringExtra("CameraIp");		
		CtrlIp= intent.getStringExtra("ControlUrl");		
		CtrlPort=intent.getStringExtra("Port");		

		r.GetCameraIP(CameraIp);
		InitSocket();

		TakePhotos.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(null!=Constant.handler)
				{
				 Message message = new Message();      
		            message.what = 1;
		            Constant.handler.sendMessage(message);  
				}
			}
			
		});
		
	}

  public void InitSocket()
  {
	  
			 try {
				socket = new Socket(InetAddress.getByName(CtrlIp),Integer.parseInt(CtrlPort));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/* try {
				socketWriter= socket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
  }
		
	public void onDestroy() 
	{
		super.onDestroy();
	
	}

	private long exitTime = 0;
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)   
    {  
		 if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)  
		 {  
		           
		         if((System.currentTimeMillis()-exitTime) > 2500)
                 {
					 Toast.makeText(getApplicationContext(), "再按一次退出",Toast.LENGTH_SHORT).show();
		        	 exitTime = System.currentTimeMillis();  
				 }  
		         else  
		         {  
		             finish();  
		             System.exit(0);  
		         }  
		                   
		         return true;  
		 }  
		 return super.onKeyDown(keyCode, event);  
    }  

}


