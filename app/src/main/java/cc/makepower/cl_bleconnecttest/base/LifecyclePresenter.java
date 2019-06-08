package cc.makepower.cl_bleconnecttest.base;

import android.support.annotation.NonNull;

public interface LifecyclePresenter<T> {

    /**
     * BasePresenter生命周期的开始
     *
     * @param v
     */
    void attachView(@NonNull T v);

    /**
     * BasePresenter生命周期的结束
     */
    void detachView();
}