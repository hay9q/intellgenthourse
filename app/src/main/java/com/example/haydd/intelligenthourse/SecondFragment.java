package com.example.haydd.intelligenthourse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondFragment extends Fragment implements OnClickListener {

    private Button sendR1;
    private Button sendR2;
    private Button sendR3;
    private Button sendR4;
    private Button sendR5;
    private Button sendR6;
    private TextView time;

    private String weather;
    private String[] strings = new String[200];
    private byte[] send = new byte[]{};
	private byte[] buffer = new byte[]{};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.secondfragment, container, false);

        SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日");
        Date current = new Date(System.currentTimeMillis());
        String str = formater.format(current);

        sendR1 = (Button) view.findViewById(R.id.sendNetr1);
        sendR2 = (Button) view.findViewById(R.id.sendNetr2);
        sendR3 = (Button) view.findViewById(R.id.sendNetr3);
        sendR4 = (Button) view.findViewById(R.id.sendNetr4);
        sendR5 = (Button) view.findViewById(R.id.sendNetr5);
        sendR6 = (Button) view.findViewById(R.id.sendNetr6);
        time = (TextView) view.findViewById(R.id.time);

        time.setText("现在是："+str);
        sendR1.setOnClickListener(this);
        sendR2.setOnClickListener(this);
        sendR3.setOnClickListener(this);
        sendR4.setOnClickListener(this);
        sendR5.setOnClickListener(this);
        sendR6.setOnClickListener(this);
		return view;
	}

	private int sendMessage(byte[] msg) {
		if (Values.socket == null) {
			return -1;
		} else {
			OutputStream os;
			try {
				os = Values.socket.getOutputStream();
				os.write(buffer);
				return 1;
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				return -1;
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sendNetr1:
			new Thread(startThread("52889/count_1")).start();
            Toast.makeText(getActivity(),"即将演示今天的天气，如果演示失败，请检查你的蓝牙连接是否正常...",Toast.LENGTH_SHORT).show();
            break;
		case R.id.sendNetr2:
            new Thread(startThread("52889/count_2")).start();
            Toast.makeText(getActivity(),"即将演示明天的天气，如果演示失败，请检查你的蓝牙连接是否正常...",Toast.LENGTH_SHORT).show();
            break;
		case R.id.sendNetr3:
            new Thread(startThread("52889/count_3")).start();
            Toast.makeText(getActivity(),"即将演示后天的天气，如果演示失败，请检查你的蓝牙连接是否正常...",Toast.LENGTH_SHORT).show();
            break;
		case R.id.sendNetr4:
            new Thread(startThread("52889/count_4")).start();
            Toast.makeText(getActivity(),"即将演示第四天的天气，如果演示失败，请检查你的蓝牙连接是否正常...",Toast.LENGTH_SHORT).show();
            break;
		case R.id.sendNetr5:
            new Thread(startThread("52889/count_5")).start();
            Toast.makeText(getActivity(),"即将演示第五天的天气，如果演示失败，请检查你的蓝牙连接是否正常...",Toast.LENGTH_SHORT).show();
            break;
		case R.id.sendNetr6:
            new Thread(startThread("52889/count_6")).start();
            Toast.makeText(getActivity(),"即将演示第六天的天气，如果演示失败，请检查你的蓝牙连接是否正常...",Toast.LENGTH_SHORT).show();
            break;
		default:
			break;
		}

	}

    public void sendR(byte[] a){
        buffer = new byte[]{'r'};
        if (sendMessage(buffer) == -1) {
         //   Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
        } else {
            buffer = new byte[]{a[0]};
            if (sendMessage(buffer) == 1)
            {
                buffer = new byte[]{a[1]};
                if (sendMessage(buffer)==1)
                {
                    buffer = new byte[]{a[2]};
                    if (sendMessage(buffer)==1)
                    {
                      //  Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                    }else {
                     //   Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                   // Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
                }
            }else {
               // Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Runnable startThread(final String id){
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                InputStream inputStream = null;
                //http://weather.raychou.com/?/detail/58367
                try{//http://weather.raychou.com/?/detail/58367/count_1
                    URL infoUrl = new URL("http://weather.raychou.com/?/detail/"+id);
                    URLConnection connection = infoUrl.openConnection();
                    HttpURLConnection httpConnection = (HttpURLConnection)connection;
                    int responseCode = httpConnection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        inputStream = httpConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                        String line = null;
                        int i = 0;
                        while((line = reader.readLine()) != null){
                            // buffer.append(line+"\n");
                            strings[i++] = line.toString();
                            //  System.out.println(strings.length);
                        }
                        for (int j = 0;j<strings.length - 1;j++){
                            String reg = "<li class=\"tianqi\">";
                            if (strings[j] == null){
                                break;
                            }
                            if (strings[j].contains(reg)){
                                //    System.out.println(strings[j]);
                                //    System.out.println(strings[j+1]);
                                weather = strings[j];
                            //    System.out.println(weather);
                           //     System.out.println("..........................");
                             //   fengxiang = strings[j+1];
                            }
                        }
                        if (weather.contains("雨")){
                          //  System.out.println(weather+".........1");
                            send = new byte[]{60,1,1};
                            sendR(send);
                            Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                        }else if (weather.contains("云")){
                        //    System.out.println(weather);
                            send = new byte[]{0,2,0};
                            sendR(send);
                            Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                        }else if(weather.contains("雾")){
                            send = new byte[]{127,2,0};
                            sendR(send);
                            Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                        } else if(weather.contains("晴")){
                         //   System.out.println(weather);
                            send = new byte[]{60,0,0};
                            sendR(send);
                            Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                        }
                        //   System.out.println(buffer.toString());
                   //     System.out.println(weather+"  "+fengxiang);
                   //     System.out.println("..............................");
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        return runnable;
    }

}
