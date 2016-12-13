package app.tfswx.com.test_getjson;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by 13 on 2016/12/8.
 */

public class News_PagerAdapter extends PagerAdapter {
    ArrayList<View> viewArrayList;
    public News_PagerAdapter(ArrayList<View> viewArrayList){
        this.viewArrayList = viewArrayList;
    }
    @Override
    public int getCount() {
        return viewArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewArrayList.get(position));
        return viewArrayList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewArrayList.get(position));
    }
}
