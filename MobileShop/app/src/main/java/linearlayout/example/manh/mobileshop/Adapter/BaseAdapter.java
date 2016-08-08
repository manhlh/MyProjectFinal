package linearlayout.example.manh.mobileshop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import linearlayout.example.manh.mobileshop.Model.SanPham;
import linearlayout.example.manh.mobileshop.R;

/**
 * Created by MANH on 20-May-16.
 */
public class BaseAdapter extends android.widget.BaseAdapter {

    ArrayList<SanPham> arrSanPham;
    Context mcontext;
    int mlayout;

    public BaseAdapter(ArrayList<SanPham> accountArrayList, Context mcontext, int mlayout) {
        this.arrSanPham = accountArrayList;
        this.mcontext = mcontext;
        this.mlayout = mlayout;
    }

    public void clearData(){
        arrSanPham.clear();
    }

    @Override
    public int getCount() {
        return arrSanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowview = convertView;
        LayoutInflater inflater = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(rowview == null){
            ViewHolder viewHolder = new ViewHolder();
            rowview = inflater.inflate(mlayout,null);
            viewHolder.tvName = (TextView)rowview.findViewById(R.id.tvTenSP);
            viewHolder.tvGia = (TextView)rowview.findViewById(R.id.tvGiaSP);
            viewHolder.imgAvatar = (ImageView)rowview.findViewById(R.id.imgSanPham);
            rowview.setTag(viewHolder);
        }
        ViewHolder view = (ViewHolder)rowview.getTag();
        view.tvName.setText(arrSanPham.get(position).getTENSP());
        view.tvGia.setText(arrSanPham.get(position).getGIASP().toString()+"$");
        byte[] byteArray = arrSanPham.get(position).getHINHANHSP();
        Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
        view.imgAvatar.setImageBitmap(bm);
        return rowview;
    }

    public class ViewHolder{
        TextView tvName;
        TextView tvGia;
        ImageView imgAvatar;
    }

//    @Override
//    public Filter getFilter() {
//        if (valueFilter == null) {
//            valueFilter = new ValueFilter();
//        }
//        return valueFilter;
//    }

//    private class ValueFilter extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results = new FilterResults();
//
//            if (constraint != null && constraint.length() > 0) {
//                ArrayList<SanPham> filterList = new ArrayList<SanPham>();
//                for (int i = 0; i < arrSanPham.size(); i++) {
//                    if ( (arrSanPham.get(i).getTENSP().toUpperCase() )
//                            .contains(constraint.toString().toUpperCase())) {
//
//                        SanPham country = new SanPham(arrSanPham.get(i)
//                                .getMaSP_Id(), arrSanPham.get(i)
//                                .getTENSP(),  arrSanPham.get(i)
//                                .getGIASP() ,  arrSanPham.get(i)
//                                .getSOLUONGSP(), arrSanPham.get(i)
//                        .getHINHANHSP(), arrSanPham.get(i)
//                        .getTHONGTINSP());
//                        filterList.add(country);
//                    }
//                }
//
//
//                results.count = filterList.size();
//                results.values = filterList;
//            } else {
//                results.count = arrSanPham.size();
//                results.values = arrSanPham;
//            }
//            return results;
//
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint,FilterResults results) {
//            arrSanPham = (ArrayList<SanPham>) results.values;
//            notifyDataSetChanged();
//        }
//
//    }
}
