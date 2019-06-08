package cc.makepower.cl_bleconnecttest.base;

public interface BaseView {
    void showToast(int message);
    void showToast(String message);
    void showProgress(int resources);
    void showProgress(String resources);

    void hideProgress();
}
