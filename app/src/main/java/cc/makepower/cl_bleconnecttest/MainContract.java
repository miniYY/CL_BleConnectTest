package cc.makepower.cl_bleconnecttest;

import java.util.ArrayList;

import cc.makepower.cl_bleconnecttest.base.BaseView;
import cc.makepower.cl_bleconnecttest.bean.DeviceNameBean;

public interface MainContract {
    interface View extends BaseView{

        void fectchLocalXMLsDeviceListCallBack(ArrayList<DeviceNameBean> deviceNameBeans);
    }
}
