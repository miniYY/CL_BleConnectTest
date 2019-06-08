package cc.makepower.cl_bleconnecttest.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cxj on 2018/3/10.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {


    private APresenter aPresenter;

    /**
     * 获取layout ID设置{@code setContentView()}
     */
    protected abstract int getLayoutId();

    /**
     * 在{@code setContentView()}方法之后执行
     *
     * @param savedInstanceState
     */
    protected abstract void afterInCreate(Bundle savedInstanceState);

    /**
     * 在{@code setContentView()}方法之前执行
     *
     * @param savedInstanceState
     */
    protected void beforeInCreate(Bundle savedInstanceState) {
    }

    /**
     * 注入presenter
     */
    protected abstract APresenter injectPresenter();

    private Unbinder unBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beforeInCreate(savedInstanceState);
        setContentView(getLayoutId());
//        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
//        }
        unBinder = ButterKnife.bind(this);
        aPresenter = injectPresenter();
        if (aPresenter != null) {
            aPresenter.attachView(this);
        }
        afterInCreate(savedInstanceState);
    }
    @Override
    protected void onDestroy() {
        if (unBinder != null) {
            unBinder.unbind();
        }
        if (aPresenter != null) {
            aPresenter.unSubscribe();
        }
        if (aPresenter != null) {
            aPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void showToast(int message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(int resources) {

    }

    @Override
    public void showProgress(String resources) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public Resources getResources() {
        //系统修改不影响app
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

}
