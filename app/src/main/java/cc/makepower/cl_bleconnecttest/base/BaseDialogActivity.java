package cc.makepower.cl_bleconnecttest.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: ATEX(YGQ)
 * @description:这里添加描述
 * @projectName: MakepeoDo
 * @date: 2016-10-11
 * @time: 11:31
 */
public abstract class BaseDialogActivity extends Activity implements BaseView {
    private Unbinder unBinder;
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
     * 注入presenter
     */
    /**
     * 注入presenter
     */
    protected abstract APresenter injectPresenter();
    private APresenter aPresenter;

    /**
     * 在{@code setContentView()}方法之前执行
     *
     * @param savedInstanceState
     */
    protected void beforeInCreate(Bundle savedInstanceState) {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInCreate(savedInstanceState);
        setContentView(getLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        unBinder= ButterKnife.bind(this);
        aPresenter=injectPresenter();
        if(aPresenter!=null){
            aPresenter.attachView(this);
        }
        afterInCreate(savedInstanceState);
    }

    @Override
    public void showProgress(String resources) {

    }

    @Override
    public void showToast(int message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {

        if (unBinder!=null) {
            unBinder.unbind();
        }
        if (aPresenter!=null) {
            aPresenter.unSubscribe();
        }
        if (aPresenter!=null){
            aPresenter.detachView();
        }
        super.onDestroy();
    }
    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
    @Override
    public void showProgress(int resources) {
    }
    @Override
    public void hideProgress() {
    }


//
//    protected void eventBusRegister() {
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
//    }
//
//    protected void eventBusUnRegister() {
//        if (EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().unregister(this);
//        }
//    }


}
