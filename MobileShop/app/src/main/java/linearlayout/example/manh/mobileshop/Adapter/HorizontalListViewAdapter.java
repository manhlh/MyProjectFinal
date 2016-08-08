package linearlayout.example.manh.mobileshop.Adapter;

/**
 * Created by MANH on 08-Aug-16.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import linearlayout.example.manh.mobileshop.R;

public class HorizontalListViewAdapter extends BaseAdapter {
    private int[] arrImg;
    private Context context;

    public HorizontalListViewAdapter(Context context, int[] arrImg) {
        this.arrImg = arrImg;
        this.context = context;
    }
    @Override
    public int getCount() {
        return arrImg.length;
    }

    @Override
    public Object getItem(int arg0) {
        return arrImg[arg0];
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int arg0, View convertView, ViewGroup arg2) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.gridview_item_sanpham,
                    null);
        }

        ImageView imgItem = (ImageView) convertView.findViewById(R.id.imgItem);
        imgItem.setImageResource(arrImg[arg0]);

        return convertView;
    }

}
