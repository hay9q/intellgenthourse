package com.example.haydd.intelligenthourse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class ThirdFragment extends Fragment implements OnClickListener {

    private Button sendD;
    private Button sendZ;
    private Button sendRain;
    private Button sendCloud;
    private Button sendClear;
    private Button sendCloud2;
	private byte[] buffer = new byte[]{};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.thirdfragment, container, false);

        sendD = (Button) view.findViewById(R.id.sendd);
        sendZ = (Button) view.findViewById(R.id.sendz);
        sendClear = (Button) view.findViewById(R.id.sendrclear);
        sendCloud = (Button) view.findViewById(R.id.sendrcloud);
        sendRain = (Button) view.findViewById(R.id.sendrrain);
        sendCloud2 = (Button) view.findViewById(R.id.sendrcloud2);


        sendD.setOnClickListener(this);
        sendZ.setOnClickListener(this);
        sendClear.setOnClickListener(this);
        sendCloud.setOnClickListener(this);
        sendRain.setOnClickListener(this);
        sendCloud2.setOnClickListener(this);
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
            case R.id.sendd:
                buffer = new byte[]{'d'};
                if (sendMessage(buffer) == -1) {
                    Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "正在关闭，请稍后...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sendz:
                buffer = new byte[]{'z'};
                if (sendMessage(buffer) == -1) {
                    Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "正在关闭，请稍后...", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sendrclear:
                byte[] a = new byte[]{60,0,0};
                sendR(a);
                break;
            case R.id.sendrcloud:
                byte[] b = new byte[]{0,2,0};
                sendR(b);
                break;

            case R.id.sendrcloud2:
                byte[] c = new byte[]{127,2,0};
                sendR(c);
                break;

            case R.id.sendrrain:
                byte[] d = new byte[]{60,1,1};
                sendR(d);
                break;

		default:
			break;
		}

	}

    public void sendR(byte[] a){
        buffer = new byte[]{'r'};
        if (sendMessage(buffer) == -1) {
            Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "请稍后...", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getActivity(), "失败，请检查连接！", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
