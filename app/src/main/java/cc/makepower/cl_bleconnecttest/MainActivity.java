package cc.makepower.cl_bleconnecttest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private boolean accpetPermission = false;//有没有获取到所有的权限
    Button btn_StartTest;//开始测试的按钮
    BluetoothClient mClient;
    private String[] permissions = {Manifest.permission.LOCATION_HARDWARE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private List<SearchResult> searchResults;//搜索到的蓝牙设备结果集

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_StartTest = findViewById(R.id.btn_StartTest);
        mClient = new BluetoothClient(MainActivity.this);
        searchResults = new ArrayList<>();
        checkLocationPermission();


        btn_StartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!accpetPermission) {
                    checkLocationPermission();
                    return;
                }
                if (!mClient.isBluetoothOpened()) {
                    mClient.openBluetooth();
                } else {
                    mClient.stopSearch();
                    searchResults.clear();
                    SearchRequest request = new SearchRequest.Builder()
                            .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                            .searchBluetoothClassicDevice(10000) // 再扫经典蓝牙5s
                            .searchBluetoothLeDevice(5000)      // 再扫BLE设备2s
                            .build();
                    mClient.search(request, searchResponse);
                }
            }
        });
    }


    SearchResponse searchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {

        }

        @Override
        public void onDeviceFounded(SearchResult device) {
            searchResults.add(device);
        }

        @Override
        public void onSearchStopped() {
// TODO: 2019/6/5 搜索完成后 和 表格进行对比，哪些设备没有被搜索到
        }

        @Override
        public void onSearchCanceled() {

        }
    };


    private boolean checkPermission(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    /**
     * 申请权限
     */
    private void checkLocationPermission() {
        if (!checkPermission(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, 1001);
        } else {
            accpetPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001:
                boolean permissionAccept = true;
                for (int i = 0; i < grantResults.length; i++) {
                    permissionAccept &= grantResults[i] == PackageManager.PERMISSION_GRANTED;
                }
                if (permissionAccept) {
                    accpetPermission = true;
                } else {
                    System.exit(0);
                }
                break;
        }
    }

}
