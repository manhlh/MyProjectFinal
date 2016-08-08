package linearlayout.example.manh.mobileshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import linearlayout.example.manh.mobileshop.Adapter.BaseAdapter;
import linearlayout.example.manh.mobileshop.Model.SanPham;
import linearlayout.example.manh.mobileshop.Util.RequestConstant;

public class HienThi_SanPham_Activity extends AppCompatActivity implements RequestConstant{
    GridView gvSanPham;
    ArrayList<SanPham> arrSanPham;

    BaseAdapter baseAdapter;
    ArrayList<String> arrTest;
    ArrayAdapter<SanPham> arrayAdapter;
    String DATABASE_NAME="MyProjectFinal.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi__san_pham_);

        processCopy();
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        gvSanPham = (GridView) findViewById(R.id.gvSanPham);
        arrSanPham = new ArrayList<>();
        arrTest = new ArrayList<>();

//        arrTest.addAll(Arrays.asList(getResources().getStringArray(R.array.arrayTest)));
//        arrayAdapter = new ArrayAdapter<String>(HienThi_SanPham_Activity.this,android.R.layout.simple_list_item_1,arrTest);
//        gvSanPham.setAdapter(arrayAdapter);
//        gvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), arrTest.get(i).toString(),Toast.LENGTH_LONG).show();
//            }
//        });
        getAllSanPham();
        ClickSanPham();
    }


    public void getAllSanPham(){
        arrSanPham.clear();
        Cursor c = database.query(TABLE_SANPHAM,null,null,null,null,null,null);
        while (c.moveToNext()){
            arrSanPham.add(new SanPham(
                    c.getInt(0),
                    c.getString(1),
                    c.getInt(2),
                    c.getInt(3),
                    c.getBlob(4),
                    c.getString(5),
                    c.getInt(6)
            ));
//            Log.d("TenSP", c.getBlob(4));
        }
       // adapter = new MyBaseAdapter(HienThi_SanPham_Activity.this,R.layout.gridview_item_sanpham,arrSanPham);
        baseAdapter = new BaseAdapter(arrSanPham,HienThi_SanPham_Activity.this,R.layout.gridview_item_sanpham);
        gvSanPham.setAdapter(baseAdapter);
        Log.d("arrSanPham", String.valueOf("Size: "+arrSanPham.size()));


    }

    private void ClickSanPham() {
        gvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int idSP = arrSanPham.get(i).getMaSP_Id();
                int FKHangSanXuat_Id = arrSanPham.get(i).getFKHangSanXuat_Id();
                //Log.d("clickIDSP", String.valueOf(idSP));
                Intent intent = new Intent(HienThi_SanPham_Activity.this, DetailSanPhamActivity.class);
                intent.putExtra("idSP",String.valueOf(idSP));
                intent.putExtra("FKHangSanXuat_Id", String.valueOf(FKHangSanXuat_Id));
                startActivity(intent);
            }
        });
    }

    //Setup SearchView & Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        MenuItem menuSearch = menu.findItem(R.id.mnu_search);
        SearchView searchView = (SearchView) menuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrSanPham.clear();
                Cursor c = database.rawQuery("select * from "+ TABLE_SANPHAM+ " where "+TENSP+" like ?", new String[]{"%"+ s +"%"});
                while (c.moveToNext()) {
                    arrSanPham.add(new SanPham(
                            c.getInt(0),
                            c.getString(1),
                            c.getInt(2),
                            c.getInt(3),
                            c.getBlob(4),
                            c.getString(5),
                            c.getInt(6)
                    ));
                    gvSanPham.setAdapter(baseAdapter);
                }
                Log.d("arrSearch","Size: "+ String.valueOf(arrSanPham.size()));


                if("".contains(s)){
                    arrSanPham.clear();
                    getAllSanPham();
                }
                return false;
            }
        });
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:

                return (true);
            case R.id.exit:
                finish();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private void processCopy() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Log.d("copyDB","Copying sucess from Assets folder");
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            // Path to the just created empty db
            String outFileName = getDatabasePath();
            // if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }

        catch(
                IOException e
                )

        {
            e.printStackTrace();
        }
    }
}
