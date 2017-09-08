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

public class ForthFragment extends Fragment implements OnClickListener {

    private Button baiweather;
    private Button yeweather;
    private String weather;
    private TextView time;
    private String[] strings = new String[400];
    private byte[] send = new byte[]{};
	private byte[] buffer = new byte[]{};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.forthfragment, container, false);

        SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日");
        Date current = new Date(System.currentTimeMillis());
        String str = formater.format(current);

        baiweather = (Button) view.findViewById(R.id.bai);
        yeweather = (Button) view.findViewById(R.id.ye);
        time = (TextView) view.findViewById(R.id.forth_time);
        time.setText("现在是："+str);
        baiweather.setOnClickListener(this);
        yeweather.setOnClickListener(this);
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
            case R.id.bai:
                new Thread(startThread("<h2>白天</h2>")).start();
                Toast.makeText(getActivity(),"即将演示今天白天天气，如果演示失败，请检查你的蓝牙连接是否正常...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ye:
                 new Thread(startThread("<h2>夜间</h2>")).start();
                Toast.makeText(getActivity(),"即将演示今天夜间天气，如果演示失败，请检查你的蓝牙连接是否正常...",Toast.LENGTH_SHORT).show();
                break;
		default:
			break;
		}

	}

    public void sendR(byte[] a){
        buffer = new byte[]{'r'};
        if (sendMessage(buffer) == -1) {
          //  Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
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
                  //      Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                    }else {
                    //    Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
                    }
                }else {
              //      Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
                }
            }else {
              //  Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Runnable startThread(final String str){
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                InputStream inputStream = null;
                try{//http://weather.raychou.com/?/detail/58367
                    URL infoUrl = new URL("http://www.weather.com.cn/weather1d/101160101.shtml");
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
                            if (i == 400) break;
                            //   System.out.println(line);

                        }
                        for (int j = 0;j<400;){
                            if (strings[j].contains("<h1>")&&strings[j+1].contains(str)){
                                weather = strings[j+3];
                           //     System.out.println(strings[j]+":"+strings[j+3]);
                                j=j+3;
                            }else{
                                j++;
                            }
                        }

                        if (weather.contains("雨")){
                            //  System.out.println(weather+".........1");
                            send = new byte[]{60,1,1};
                            sendR(send);
                            Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                        }else if (weather.contains("云")){
                             //   System.out.println(weather);
                            send = new byte[]{0,2,0};
                            sendR(send);
                            Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                        }else if(weather.contains("雾")){
                            send = new byte[]{127,2,0};
                            sendR(send);
                            Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                        }else if(weather.contains("晴")){
                            //   System.out.println(weather);
                            send = new byte[]{60,0,0};
                            sendR(send);
                            Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                        }
                   /* for (int j = 0;j<strings.length - 1;j++){
                        String reg = "<li class=\"tianqi\">";
                        if (strings[j] == null){
                            break;
                        }
                        if (strings[j].contains(reg)){
                        //    System.out.println(strings[j]);
                        //    System.out.println(strings[j+1]);
                            weather = strings[j];
                            fengxiang = strings[j+1];
                           // break;
                        }
                    }

                 //   System.out.println(buffer.toString());
                  System.out.println(weather+"  "+fengxiang);*/
                        System.out.println("..............................");
                    }


                }catch (Exception e){
                //    Toast.makeText(getActivity(),"网络异常...",Toast.LENGTH_SHORT).show();
                }
            }
        };
        return runnable;
    }
}
