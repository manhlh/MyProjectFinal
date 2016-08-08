package linearlayout.example.manh.mobileshop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.sileria.android.view.HorzListView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import linearlayout.example.manh.mobileshop.Adapter.HorizontalListViewAdapter;
import linearlayout.example.manh.mobileshop.Model.SanPham;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    List<SanPham> lstSanPham;
    Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        lstSanPham = new ArrayList<>();
//        new read_json().execute("http://canhocaocap.wap.sh/db.json");
//        img = (ImageView) findViewById(R.id.imgViewDetailSP);
//        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo mData = connManager.getActiveNetworkInfo();
//
//        if(mData != null && mData.isConnected()){
//            Toast.makeText(getApplicationContext(),"Co ket noi internet",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(getApplicationContext(),"kooooooooo ket noi internet",Toast.LENGTH_LONG).show();
//        }

        // listview don gian
        HorzListView listview = (HorzListView) findViewById(R.id.horizontal_lv);
        String[] arrString = { "Android", "Windown Phone", "IOS", "Mac",
                "Linux" };
        ArrayAdapter<String> adapterString = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arrString);
        listview.setAdapter(adapterString);

        // listview Item la anh
        HorzListView listviewImg = (HorzListView) findViewById(R.id.horizontal_lvImg);
        int[] arrImg = { R.drawable.galaxys7, R.drawable.iconsearch, R.drawable.galaxys7};
        HorizontalListViewAdapter adapterImg = new HorizontalListViewAdapter(
                this, arrImg);
        listviewImg.setAdapter(adapterImg);


    }

    //Read json
    class read_json extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            JSONObject root;
            String chuoiJson = get_data_from_url(params[0]);
            try {
                root = new JSONObject(chuoiJson);
                JSONObject obj = root.getJSONObject("SanPham");
                URL u = new URL(obj.getString("hinh"));
                 bmp = BitmapFactory.decodeStream(u.openConnection().getInputStream());

                Log.d("json", obj.getString("hinh"));


            } catch (Exception e) {
                e.printStackTrace();
                {
                    e.printStackTrace();
                }
                {
                    e.printStackTrace();
                }
            }
            return chuoiJson;
        }

        @Override
        protected void onPostExecute(String s) {
            img.setImageBitmap(bmp);
            super.onPostExecute(s);
        }
    }
    //get data from url
    private static String get_data_from_url(String theUrl)
    {
        StringBuilder content = new StringBuilder();
        try
        {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
}
