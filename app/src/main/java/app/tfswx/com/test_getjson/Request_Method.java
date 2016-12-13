package app.tfswx.com.test_getjson;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13 on 2016/12/12.
 */

public class Request_Method {
    String url;
    static List<Image_News_Bean.ResultBean.DataBean> dataBean_List;
    ListView listView;
    Context context;
    News_Item_Adapter item_adapter;

    public Request_Method(Context context,String url, List<Image_News_Bean.ResultBean.DataBean> dataBean_List, ListView listView,News_Item_Adapter item_adapter){
        this.context=context;
        this.dataBean_List=dataBean_List;
        this.url=url;
        this.listView=listView;
        this.item_adapter = item_adapter;
    }
    public void doRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(context, "返回成功", Toast.LENGTH_SHORT).show();
                Gson gson = new GsonBuilder().create();
                //反序列化
                Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                //返回的item项的总数
                int itemSize = news_bean.getResult().getData().size();
                //获取DataBean集合
                List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                //提取数据
                //stringBuffer_tiyu = new StringBuffer();
                dataBean_List = new ArrayList<>();
                for(int i=0;i<itemSize;i++){
                    //获取单个DataBean
                    Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
                    dataBean_List.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                            dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                }
                item_adapter = new News_Item_Adapter(context,dataBean_List);
                //为lv_tiyu设置Adapter
                listView.setAdapter(item_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "返回失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity",volleyError.getMessage()+"");
            }
        });
        requestQueue.add(stringRequest);
    }
}
