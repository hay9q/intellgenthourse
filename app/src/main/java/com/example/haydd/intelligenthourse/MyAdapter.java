package com.example.haydd.intelligenthourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int[] image;
    private String[] number;

    public MyAdapter(Context context, int[] image, String[] number) {
        super();
        this.context = context;
        this.image = image;
        this.number = number;
    }
    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return number.length;
    }

    public long getItemId(int i) {

        return 0;

    }

    public Object getItem(int i) {
        return null;

    }

    public View getView(int poisition, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup group = (ViewGroup) inflater.inflate(R.layout.items, null);
        TextView textView = (TextView) group.findViewById(R.id.number);
        ImageView imageView = (ImageView) group.findViewById(R.id.image);
        int resId = image[poisition];
        String name = number[poisition];
        imageView.setImageResource(resId);
        textView.setText(name);
        return group;
    }
}
