package com.example.haydd.intelligenthourse;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FirstFragment extends Fragment implements OnItemClickListener {
	private View view;
	private BluetoothAdapter bluetoothAdapter;
	private ListView deviceListView;
	private MyAdapter myAdapter;
	private List<String[]> deviceList = new ArrayList<String[]>();
	private String[] devices;
	private Button searchButton;
	private Button itselfButton;
	private TextView show;
	private ServerThread startServerThread;
	private UUID blue_comid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private BluetoothDevice device;
	private boolean hasregister = false;
	private DeviceReceiver mydevice = new DeviceReceiver();
	private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//startBluetooth();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.firstfragment, container,false);
		deviceListView = (ListView) view.findViewById(R.id.listView1);
		myAdapter = new MyAdapter(deviceList, getActivity());
		deviceListView.setAdapter(myAdapter);
		searchButton = (Button) view.findViewById(R.id.search);
		itselfButton = (Button) view.findViewById(R.id.itself);
		show = (TextView)view.findViewById(R.id.show);
		searchButton.setOnClickListener(new ClickMonitor());
		itselfButton.setOnClickListener(new ClickMonitor());
		deviceListView.setOnItemClickListener(this);
		searchButton.setText(" 搜索设备 ");
		startBluetooth();
		return view;
	}
	
	private void startBluetooth() {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (bluetoothAdapter == null) {
			Toast.makeText(getActivity(), "该设备无蓝牙功能", Toast.LENGTH_SHORT).show();
			// 确认�?启蓝�?
		} else if (!bluetoothAdapter.isEnabled()) {
			// 请求用户�?�?
			Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enable, 0);
			// 使蓝牙设备可�?
			Intent in = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			in.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 200);
			startActivity(in);
            //itselfButton.setText(" 关闭本机蓝牙 ");
			/*
			 * //直接�?启，不提�? bluetoothAdapter.enable();
			 */
		}
		if (bluetoothAdapter.isEnabled()) {
			itselfButton.setText(" 关闭本机蓝牙 ");
		} else {
			itselfButton.setText(" 打开本机蓝牙 ");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case Activity.RESULT_OK:
			itselfButton.setText(" 关闭本机蓝牙 ");
			// findDevice();
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

/*	public void setView() {
		deviceListView = (ListView) view.findViewById(R.id.listView1);
		myAdapter = new MyAdapter(deviceList, getActivity());
		deviceListView.setAdapter(myAdapter);
		searchButton = (Button) view.findViewById(R.id.search);
		itselfButton = (Button) view.findViewById(R.id.itself);
		searchButton.setOnClickListener(new ClickMonitor());
		itselfButton.setOnClickListener(new ClickMonitor());
		deviceListView.setOnItemClickListener(this);
		searchButton.setText(" 搜索设备 ");
	}*/

	class MyAdapter extends BaseAdapter {
		private List<String[]> list;
		private Context context;

		public MyAdapter(List<String[]> list, Context context) {
			this.list = list;
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ViewGroup group = (ViewGroup) inflater.inflate(R.layout.device_item, null);
			TextView name = (TextView) group.findViewById(R.id.textView1);
			TextView address = (TextView) group.findViewById(R.id.textView2);
			name.setTextColor(Color.BLACK);
			address.setTextColor(Color.BLACK);
			String[] ss = list.get(position);
			name.setText(ss[0]);
			address.setText(ss[1]);
			return group;
		}

	}

	@Override
	public void onStart() {
		// 注册蓝牙接收广播
		if (!hasregister) {
			hasregister = true;
			IntentFilter filterStart = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			IntentFilter filterEnd = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
			getActivity().registerReceiver(mydevice, filterStart);
			getActivity().registerReceiver(mydevice, filterEnd);
		}
		super.onStart();
	}

	@Override
	public void onDestroy() {

		if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
			bluetoothAdapter.cancelDiscovery();
		}
		if (hasregister) {
			hasregister = false;
			getActivity().unregisterReceiver(mydevice);
		}
		super.onDestroy();
	}

	/*
	 * private void findDevice() { // 获取可配对的蓝牙设备 Set<BluetoothDevice> device =
	 * bluetoothAdapter.getBondedDevices(); deviceList.clear();
	 * myAdapter.notifyDataSetChanged(); if (bluetoothAdapter != null &&
	 * bluetoothAdapter.isDiscovering()) { deviceList.clear();
	 * myAdapter.notifyDataSetChanged(); } if (device.size() > 0) { for
	 * (Iterator<BluetoothDevice> it = device.iterator(); it.hasNext();) {
	 * BluetoothDevice bd = it.next(); devices = new String[] { bd.getName(),
	 * bd.getAddress() }; deviceList.add(devices);
	 * myAdapter.notifyDataSetChanged(); } } else
	 * if(!bluetoothAdapter.isDiscovering()) { devices = new String[] { "",
	 * "没有可以配对的蓝牙设�?!" }; deviceList.add(devices);
	 * myAdapter.notifyDataSetChanged(); } }
	 */

	private class DeviceReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			// 搜索到新设备
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice bd = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// 搜索没有配对过的蓝牙设备
				devices = new String[] { bd.getName(), bd.getAddress() };
				deviceList.add(devices);
				myAdapter.notifyDataSetChanged();
				// 搜索结束
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				if (deviceListView.getCount() == 0) {
					devices = new String[] { "", "没有可以配对的蓝牙设备!" };
					deviceList.add(devices);
					myAdapter.notifyDataSetChanged();
				}
				searchButton.setText(" 重新搜索 ");
			}
		}

	}

	private class ClickMonitor implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.search:
				deviceListView.setVisibility(View.VISIBLE);
				show.setVisibility(View.INVISIBLE);
				if (bluetoothAdapter.isEnabled()) {
					if (bluetoothAdapter.isDiscovering()) {
						bluetoothAdapter.cancelDiscovery();
						searchButton.setText(" 重新搜索 ");
					} else {
						// findDevice();
						deviceList.clear();
						myAdapter.notifyDataSetChanged();
						bluetoothAdapter.startDiscovery();
						searchButton.setText(" 停止搜索 ");
					}
				} else {
					Toast.makeText(getActivity(), "请打开蓝牙设备！", Toast.LENGTH_SHORT).show();
				}

				break;
			case R.id.itself:
				if (!bluetoothAdapter.isEnabled()) {
					bluetoothAdapter.enable();
					itselfButton.setText(" 关闭本机蓝牙 ");
				} else if (bluetoothAdapter.isEnabled()) {
					bluetoothAdapter.disable();
					itselfButton.setText(" 打开本机蓝牙 ");
				}
				break;

			default:
				break;
			}

		}
	}
	
	private class ServerThread extends Thread {
		private String address;
		// InputStream is = null;
		
		public ServerThread(String address){
			this.address = address;
		}
		
		public void run() {
			device = mBluetoothAdapter.getRemoteDevice(address);
			try {
				Values.socket = device.createRfcommSocketToServiceRecord(blue_comid);
				Values.socket.connect();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		final String name = (deviceList.get(position))[0];
		final String msg = (deviceList.get(position))[1];
		if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
			bluetoothAdapter.cancelDiscovery();
			searchButton.setText(" 重新搜索 ");
		}
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle("是否连接该设备");
		dialog.setMessage(msg);
		dialog.setPositiveButton("connect", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				/*Intent intent = new Intent(getActivity(), BluetoothActivity.class);
				intent.putExtra("address", msg);
				startActivity(intent);*/
				dialog.dismiss();
				deviceListView.setVisibility(View.INVISIBLE);
				show.setVisibility(View.VISIBLE);
				show.setText("正在与 "+name+" 连接,请稍后...");
				startServerThread = new ServerThread(msg);
				startServerThread.start();
				try {
					startServerThread.sleep(2000);
                    long a = System.currentTimeMillis();
                    long b;

                    while(Values.socket == null) {
                        b = System.currentTimeMillis();
                        if ((b-a)>20000){
                            Toast.makeText(getActivity(),"超时，请重新连接...",Toast.LENGTH_SHORT).show();
                            show.setText("与 "+name+" 连接失败！");
                            break;
                        }
                    }
					if (Values.socket != null)
                        show.setText("与 "+name+" 连接成功！");
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					show.setText("与 "+name+" 连接失败！");
				}
			}
		});
		dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		dialog.show();
	}
}
