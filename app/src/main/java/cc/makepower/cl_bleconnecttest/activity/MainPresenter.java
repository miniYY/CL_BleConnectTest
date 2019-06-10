package cc.makepower.cl_bleconnecttest.activity;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

import cc.makepower.cl_bleconnecttest.base.APresenter;
import cc.makepower.cl_bleconnecttest.bean.DeviceNameBean;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jxl.Sheet;
import jxl.Workbook;

public class MainPresenter extends APresenter<MainContract.View> {

    private Context context;
    public MainPresenter(Context context) {
        this.context=context;
    }
    /**
     * 获取 excel 表格中的数据,不能在主线程中调用
     *
     * @param xlsName excel 表格的名称
     * @param index   第几张表格中的数据
     */
    private ArrayList<DeviceNameBean> getXlsData(File xlsName, int index) {
        ArrayList<DeviceNameBean> countryList = new ArrayList<DeviceNameBean>();
        try {
            Workbook workbook = Workbook.getWorkbook(xlsName);
            Sheet sheet = workbook.getSheet(index);

            int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();


            for (int i = 0; i < sheetRows; i++) {
                countryList.add(new DeviceNameBean(sheet.getCell(0,i).getContents()));
            }

            workbook.close();

        } catch (Exception e) {
          e.printStackTrace();
        }

        return countryList;
    }

    public void fectchLocalXMLsDeviceList(final File file){
        Observable.create(new ObservableOnSubscribe<ArrayList<DeviceNameBean>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<DeviceNameBean>> e) {

                e.onNext(getXlsData(file,0));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<DeviceNameBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        setSubscription(d);
                    }

                    @Override
                    public void onNext(ArrayList<DeviceNameBean> integer) {
                        mView.fectchLocalXMLsDeviceListCallBack(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.fectchLocalXMLsDeviceListCallBack(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
