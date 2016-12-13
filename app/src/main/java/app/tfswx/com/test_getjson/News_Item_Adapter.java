package app.tfswx.com.test_getjson;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13 on 2016/12/6.
 */

public class News_Item_Adapter extends BaseAdapter{
    private Context context;
    private List<Image_News_Bean.ResultBean.DataBean> arrayList;

    News_Item_Adapter(Context context, List<Image_News_Bean.ResultBean.DataBean> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView( int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.news_item,viewGroup,false);
            viewHolder.iv_image = (ImageView) view.findViewById(R.id.iv_image);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tv_author = (TextView) view.findViewById(R.id.tv_author);
            viewHolder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            view.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) view.getTag();
        }

        //获取图片
        Picasso.with(context).load(arrayList.get(i).getThumbnail_pic_s()).into(viewHolder.iv_image);
        //获取标题
        viewHolder.tv_title.setText(arrayList.get(i).getTitle());
        //获取作者
        viewHolder.tv_author.setText(arrayList.get(i).getAuthor_name());
        //获取更新时间
        viewHolder.tv_date.setText(arrayList.get(i).getDate());
        return view;
    }

    private class ViewHolder{
        ImageView iv_image;
        TextView tv_title;
        TextView tv_author;
        TextView tv_date;
    }
}
