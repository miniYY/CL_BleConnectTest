package cc.makepower.cl_bleconnecttest.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by CY on 2018/12/14 0014.
 * //                      _
 * //                     | |
 * //    _ __     ___     | |__    _   _    __ _
 * //   | '_ \   / _ \    | '_ \  | | | |  / _` |
 * //   | | | | | (_) |   | |_) | | |_| | | (_| |
 * //   |_| |_|  \___/    |_.__/   \__,_|  \__, |
 * //                                       __/ |
 * //                                      |___/
 */
public abstract class BaseFragment extends Fragment {
    public static final String TAG = BaseFragment.class.getSimpleName();

    private Unbinder bind;
    protected View mRootView;

    /**
     * 获取layout ID设置{@code onCreateView()}
     */
    protected abstract int getLayoutId();

    /**
     * 在{@code onViewCreated()}执行
     *
     * @param view
     * @param savedInstanceState
     */
    protected abstract void afterCreate(View view, Bundle savedInstanceState);

    /**
     * 初始化
     * {@code onCreateView()}执行
     * ps: bundle数据可以在这里获取
     */
    protected abstract void initOnCreateView();

//    /**
//     * 注入Injector
//     */
//    protected abstract void initInjector();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, mRootView);
//        initInjector();
        initOnCreateView();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterCreate(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void showToast(String mString){
        Toast.makeText(getActivity(), mString, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int resId){
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

}
