package example.caojiehang.com.graduationproject.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;


public class BaseFragment extends Fragment {
    protected Context mContext;
    public BaseFragment() {

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
    }


}
