package linearlayout.example.manh.mobileshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.sileria.android.view.HorzListView;

import java.net.URL;
import java.util.ArrayList;

import linearlayout.example.manh.mobileshop.Adapter.BaseAdapter;
import linearlayout.example.manh.mobileshop.Model.SanPham;
import linearlayout.example.manh.mobileshop.Util.RequestConstant;
import linearlayout.example.manh.mobileshop.customfonts.MyTextView;
import linearlayout.example.manh.mobileshop.slidercustom.ChildAnimationExample;
import linearlayout.example.manh.mobileshop.slidercustom.SliderLayout;

public class DetailSanPhamActivity extends AppCompatActivity implements RequestConstant,BaseSliderView.OnSliderClickListener {
    TextView tvTenSP, tvGiaSP;
    MyTextView btnMuaSP;
    String DATABASE_NAME="MyProjectFinal.sqlite";
    SQLiteDatabase database=null;
    SliderLayout mDemoSlider;
    URL u = null;
    HorzListView lv_horizontal_SPCungHang;
   public static String linkimage = null;
    Firebase root;
    String FKHangSanXuat_Id = null;
    ArrayList<SanPham> arrSanPhamCungHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_san_pham);
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Firebase.setAndroidContext(this);

        //anh xa
        init();

        //LAY ID TRUYEN TU MANIN
        String id = getIntent().getStringExtra("idSP");
        Log.d("idSend", "id::" + getIntent().getStringExtra("idSP"));
        FKHangSanXuat_Id = getIntent().getStringExtra("FKHangSanXuat_Id");
        Log.d("idSend", "FKHangSanXuat::" + FKHangSanXuat_Id);
        //FIREBASE SETUP
        root = new Firebase("https://mobile-shop.firebaseio.com/" + id);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                linkimage = dataSnapshot.getValue().toString();
                mDemoSlider = (SliderLayout)findViewById(R.id.slider);
                TextSliderView textSliderView = new TextSliderView(getApplicationContext());
                // initialize a SliderLayout
                textSliderView
                        //  .description(name)
                        .image(linkimage)
                        .setScaleType(BaseSliderView.ScaleType.CenterInside);
                mDemoSlider.addSlider(textSliderView);

                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mDemoSlider.setCustomAnimation(new ChildAnimationExample());
                mDemoSlider.setDuration(14000);
                mDemoSlider.addOnPageChangeListener(this);
                Log.d("link", linkimage);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        //DUYET DANH SACH SAN PHAM VOI ID
        try{
            String sql = "select * from "+TABLE_SANPHAM+" where "+PKMaSP_Id+" = ?";
            Log.d("sqlDetail", sql);
            Cursor c = database.rawQuery(sql,new String[]{id});
            while (c.moveToNext()){
                Log.d("getNameDetail", "Test: " + c.getString(1));
                setTitle(c.getString(1));
                tvTenSP.setText(c.getString(1));
                tvGiaSP.setText(String.valueOf(c.getInt(2)) + "$");
            }

        }catch (Exception e){
            Log.d("ERROR-Detail-SP", e.toString());
        }

        SanPhamCungHang();
        MuaSanPham();

    }

    private void MuaSanPham() {
        btnMuaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailSanPhamActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    private void SanPhamCungHang() {
        arrSanPhamCungHang = new ArrayList<>();
        String sql = "select * from "+TABLE_SANPHAM+" where "+RequestConstant.FKHangSanXuat_Id+" = ?";
        Log.d("SQL", sql);
        Cursor c = database.rawQuery(sql,new String[]{FKHangSanXuat_Id});
        Log.d("FKHangSanXuat_IdTest", "ssssssssssssss"+String.valueOf(FKHangSanXuat_Id));
        while (c.moveToNext()) {
            Log.d("NameSPCungHang", "NameSPCungHang::" + c.getString(1));
            arrSanPhamCungHang.add(new SanPham(
                    c.getInt(0),
                    c.getString(1),
                    c.getInt(2),
                    c.getInt(3),
                    c.getBlob(4),
                    c.getString(5),
                    c.getInt(6)
            ));
        }
        BaseAdapter adapter = new BaseAdapter(arrSanPhamCungHang,getApplicationContext(),R.layout.gridview_item_sanpham_cunghang);
        lv_horizontal_SPCungHang.setAdapter(adapter);

    }
    //End CreateView

    private void init() {
        tvTenSP = (TextView) findViewById(R.id.tvTenSPDetail);
        tvGiaSP = (TextView) findViewById(R.id.tvGiaSPDetail);
        lv_horizontal_SPCungHang = (HorzListView) findViewById(R.id.lv_horizontal_lvSPCungHang);
        btnMuaSP = (MyTextView) findViewById(R.id.btnMuaSP);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


}
