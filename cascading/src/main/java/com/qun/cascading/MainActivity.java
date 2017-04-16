package com.qun.cascading;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String DB_NAME = "city.s3db";
    private EditText mEtAddress;
    private Button mBtn;
    private List<Address> provinceList = new ArrayList<>();
    private List<Address> cityList = new ArrayList<>();
    private List<Address> districtList = new ArrayList<>();
    private PopupWindow mPopupWindow;
    private ListView mLvProvince;
    private ListView mLvCity;
    private ListView mLvDistrict;
    private AddressAdapter mProvinceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtAddress = (EditText) findViewById(R.id.et_address);
        mBtn = (Button) findViewById(R.id.btn);
        initDB();
        initPopupView();
        initProvince();
    }

    /**
     * 1. 需要将assets目录下的数据库文件拷贝到/data/data/包名/databases/目录下才能被以很简单的API访问
     * SQLiteDatabase.openDatabase()
     */
    private void initDB() {
        //data/data/包名/databases/
        try {
            //注意：系统默认没有databases目录，因此需要先创建这个目录才能在这个目录下创建文件
            File dbDir = new File("/data/data/" + getPackageName() + "/databases");
            if (!dbDir.exists()) {
                dbDir.mkdirs();
            }
            InputStream is = getAssets().open(DB_NAME);
            File dbFile = new File(dbDir, DB_NAME);
            FileOutputStream fos = new FileOutputStream(dbFile);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initProvince() {
        provinceList.clear();
        SQLiteDatabase database = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select code,name from province", null);
        while (cursor.moveToNext()) {
            Address address = new Address();
            address.code = cursor.getString(0);
//            address.name = cursor.getString(1);
            //解决数据库乱码
            //获取字节数组
            byte[] blob = cursor.getBlob(1);
            try {
                address.name = new String(blob, "gbk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            provinceList.add(address);
        }
        cursor.close();
        database.close();
        //将数据展示到ListView上
        mProvinceAdapter = new AddressAdapter(provinceList);
        mLvProvince.setAdapter(mProvinceAdapter);
    }

    private void initPopupView() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_layout, null);
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mLvProvince = (ListView) popupView.findViewById(R.id.lv_province);
        mLvCity = (ListView) popupView.findViewById(R.id.lv_city);
        mLvDistrict = (ListView) popupView.findViewById(R.id.lv_district);
    }

    public void selectAddress(View view) {
        //popupView依附的锚点View
        mPopupWindow.showAsDropDown(mBtn, 0, 0);
    }
    
}
