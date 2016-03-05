package com.uni.infocoming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.uni.infocoming.R;
import com.uni.infocoming.utils.TitleBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocateActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String EXTRA_LOCATE = "locateName";
    private LocationClient mLocationClient ;
    private BDLocationListener myListener ;
    private ListView lv_locate ;
    private List<Map<String,String>> locateList ;
    private SimpleAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);

        initView();
    }

    private void initView() {
        lv_locate = (ListView) findViewById(R.id.lv_locate);

        new TitleBuilder(LocateActivity.this)
                .setLeftImage(R.mipmap.ic_menu_back)
                .setLeftOnClickListener(this);
        locateList = new ArrayList<Map<String,String>>();
        Map map = new HashMap<String,String>();
        map.put("locate","不显示位置");
        locateList.add(map);

        mLocationClient = new LocationClient(this);
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        mLocationClient.start();
        adapter = new SimpleAdapter(this,locateList,R.layout.item_listview_simple,new String[]{"locate"},new int[]{R.id.tv_item});
        lv_locate.setAdapter(adapter);
        lv_locate.setOnItemClickListener(this);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(1);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listView = (ListView) parent;
        Map<String,String> map = new HashMap<String,String>();
        map = (Map<String, String>) parent.getItemAtPosition(position);
        String item = map.get("locate");
        Intent data = new Intent();
        data.putExtra(EXTRA_LOCATE,item);
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.titlebar_iv_left:
                //返回
                finish();
                break;
            default:
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            List<Poi> list = location.getPoiList();// POI数据
            Map<String,String> map ;
            for (Poi p :list){
                map = new HashMap<String,String>();
                map.put("locate",p.getName());
                locateList.add(map);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}
