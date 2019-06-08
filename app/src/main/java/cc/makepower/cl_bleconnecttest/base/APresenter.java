package cc.makepower.cl_bleconnecttest.base;

import android.support.annotation.NonNull;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;


/**
 * @author: ATEX(YGQ)
 * @description:这里添加描述
 * @projectName: MakepeoDo
 * @date: 2016-10-11
 * @time: 16:56
 */
public abstract class APresenter<T> implements LifecyclePresenter<T> {

    protected T mView;
    protected Disposable subscription;


    public void setSubscription(Disposable subscription) {
        this.subscription = subscription;
    }

    /**
     * 根据捕获的错误返回相应的提示
     *
     * @param e
     * @return
     */
    public String showError(Throwable e) {
        e.printStackTrace();
        if (e instanceof RuntimeException) {

            return "运行异常" + e.getMessage();
        }
        if (e instanceof ConnectException) {
            return "网络连接异常";
        }
        if (e instanceof UnknownHostException) {
            return "网络异常";
        }
        if (e instanceof NoRouteToHostException) {
            return "网络连接异常";
        }
        if (e instanceof SocketTimeoutException) {
            return "网络连接超时";
        }


        return "未知错误:" + e.toString();
    }

    @Override
    public void attachView(@NonNull T v) {
        this.mView = v;
    }

    @Override
    public void detachView() {
//        this.mView = null;//不将view置为null  看看
    }

    public void unSubscribe() {
        if (subscription != null) {
            subscription.dispose();
        }
//        subscription.cancel();
    }
}