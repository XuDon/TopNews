package app.tfswx.com.test_getjson;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener,SwipeRefreshLayout.OnRefreshListener{


    RequestQueue requestQueue;
    //http://v.juhe.cn/toutiao/index
    //http://118.114.241.94:7777/cd/api
    //http://125.71.206.32:777/cd/api
    //http:// 125.71.206.32:8085/ApiService.asmx;
    String URL="http://v.juhe.cn/toutiao/index";
    String TYPE="";//top shehui yule tiyu  junshi keji caijing shishang guonei guoji
    String APPKEY= "&key=13e92b9589254a0850213b8a21f85bf6";
    RadioGroup rg_menu;
    //RadioButton按钮
    RadioButton rb_top,rb_shehui,rb_yule,rb_tiyu,rb_junshi,rb_keji,rb_caijing,rb_shishang,rb_guonei,rb_guoji;
    ListView lv_top,lv_shehui,lv_yule,lv_tiyu,lv_junshi,lv_keji,lv_caijing,lv_shishang,lv_guonei,lv_guoji;
    //ListDataBean集合
    List<Image_News_Bean.ResultBean.DataBean> dataBeanList_top,dataBeanList_shehui,dataBeanList_yule,dataBeanList_tiyu,
            dataBeanList_junshi,dataBeanList_keji,dataBeanList_caijing,dataBeanList_shishang,dataBeanList_guonei,dataBeanList_guoji;
    ArrayList<View> views;
    ViewPager vp_showNews;
    //Pager_views
    View view_top,view_shehui,view_yule,view_tiyu,view_junshi,view_keji,view_caijing,view_shishang,view_guonei,view_guoji;
    News_Item_Adapter item_adapter;
    News_PagerAdapter pagerAdapter;
    StringRequest stringRequest;
    ProgressDialog dialog;
    SwipeRefreshLayout refreshLayout_top;
    long exitTime=0;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //取消刷新
            if(msg.what==0x123){
                refreshLayout_top.setRefreshing(false);
                Toast.makeText(MainActivity.this, "刷新完毕", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        netWork();
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        bindViews();
        //View集合
        views = new ArrayList<>();
        views.add(view_top);
        views.add(view_shehui);
        views.add(view_yule);
        views.add(view_tiyu);
        views.add(view_keji);
        views.add(view_junshi);
        views.add(view_caijing);
        views.add(view_shishang);
        views.add(view_guonei);
        views.add(view_guoji);
        pagerAdapter = new News_PagerAdapter(views);
        vp_showNews.setAdapter(pagerAdapter);

        //RadioGroup设置监听
        radioButtonListenner();
        //ViewPager设置监听
        viewPagerListenner();
        //为ViewPager设置监听
        listViewListenner();
        refreshListenner();

    }
    //绑定视图
    private void bindViews() {

        rg_menu = (RadioGroup) findViewById(R.id.rg_menu);
        rb_top = (RadioButton) findViewById(R.id.rb_top);
        rb_shehui = (RadioButton) findViewById(R.id.rb_shehui);
        rb_yule = (RadioButton) findViewById(R.id.rb_yule);
        rb_tiyu = (RadioButton) findViewById(R.id.rb_tiyu);
        rb_junshi = (RadioButton) findViewById(R.id.rb_junshi);
        rb_keji = (RadioButton) findViewById(R.id.rb_keji);
        rb_caijing = (RadioButton) findViewById(R.id.rb_caijing);
        rb_shishang = (RadioButton) findViewById(R.id.rb_shishang);
        rb_guonei = (RadioButton) findViewById(R.id.rb_guonei);
        rb_guoji = (RadioButton) findViewById(R.id.rb_guoji);

        view_top = getLayoutInflater().inflate(R.layout.pager_top,null,false);
        view_shehui = getLayoutInflater().inflate(R.layout.pager_shehui,null,false);
        view_yule = getLayoutInflater().inflate(R.layout.pager_yule,null,false);
        view_tiyu = getLayoutInflater().inflate(R.layout.pager_tiyu,null,false);
        view_keji = getLayoutInflater().inflate(R.layout.pager_keji,null,false);
        view_junshi = getLayoutInflater().inflate(R.layout.pager_junshi,null,false);
        view_caijing = getLayoutInflater().inflate(R.layout.pager_caijing,null,false);
        view_shishang = getLayoutInflater().inflate(R.layout.pager_shishang,null,false);
        view_guonei = getLayoutInflater().inflate(R.layout.pager_guonei,null,false);
        view_guoji = getLayoutInflater().inflate(R.layout.pager_guoji,null,false);

        refreshLayout_top = (SwipeRefreshLayout) view_top.findViewById(R.id.srl_top);

        lv_top = (ListView) view_top.findViewById(R.id.lv_top);
        lv_shehui = (ListView) view_shehui.findViewById(R.id.lv_shehui);
        lv_yule = (ListView) view_yule.findViewById(R.id.lv_yule);
        lv_tiyu = (ListView) view_tiyu.findViewById(R.id.lv_tiyu);
        lv_junshi = (ListView) view_junshi.findViewById(R.id.lv_junshi);
        lv_keji = (ListView) view_keji.findViewById(R.id.lv_keji);
        lv_caijing = (ListView) view_caijing.findViewById(R.id.lv_caijing);
        lv_shishang = (ListView) view_shishang.findViewById(R.id.lv_shishang);
        lv_guonei = (ListView) view_guonei.findViewById(R.id.lv_guonei);
        lv_guoji = (ListView) view_guoji.findViewById(R.id.lv_guoji);

        vp_showNews = (ViewPager) findViewById(R.id.vp_showNews);

        //为ViewPager设置监听
        vp_showNews.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(MainActivity.this, "页码："+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
//请求方法
    private void request_top(String url){
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//
//                new Response.Listener<JSONObject>() {
//                    @Override//成功返回json数据
//                    public void onResponse(JSONObject jsonObject) {
//                        Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
//                        //Log.w("********MainActivity**",jsonObject.toString());
////                        Gson gson = new GsonBuilder().create();
////                        news_bean = gson.fromJson(jsonObject.toString(),Image_News_Bean.class);
////                        arrayList =  news_bean.getResult().getData();
////                        //取得所有item项
////                        Toast.makeText(MainActivity.this, arrayList.size(), Toast.LENGTH_SHORT).show();
//////                        for(int i=0;i<arrayList.size();i++){
//////                            arrayList.add(arrayList.get(i));
//////                        }
//                        tv_showJson.setText(jsonObject.toString());
//                    }
//
//                }, new Response.ErrorListener() {
//            @Override//返回json数据失败
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
//                Log.e("MainActivity",volleyError.getMessage()+"");
//            }
//        })
                stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        Gson gson = new GsonBuilder().create();
                        //反序列化
                        Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                        //返回的item项的总数
                        int itemSize = news_bean.getResult().getData().size();
                        //获取DataBean集合
                        List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                        //提取数据
                        //stringBuffer_top = new StringBuffer();
                        dataBeanList_top = new ArrayList<>();
                        for(int i=0;i<itemSize;i++){
                            //获取单个DataBean
                            Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
//                            stringBuffer_top.append("新闻"+(i+1)+":\n"
//                                    +"标题："+dataBean.getTitle()
//                                    +"\n时间："+dataBean.getDate()
//                                    +"\n发布者："+dataBean.getAuthor_name());
//                            stringBuffer_top.append("\n");
//                            tv_top.setText(stringBuffer_top);
                            //为数组每项添加数据
                            dataBeanList_top.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                                    dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                        }
                        item_adapter = new News_Item_Adapter(getApplicationContext(),dataBeanList_top);
                        //为lv_top设置Adapter
                        lv_top.setAdapter(item_adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity",volleyError.getMessage()+"");
                    }
                });
                requestQueue.add(stringRequest);
    }
    private void request_shehui(String url){
        stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                Gson gson = new GsonBuilder().create();
                //反序列化
                Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                //返回的item项的总数
                int itemSize = news_bean.getResult().getData().size();
                //获取DataBean集合
                List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                //提取数据
                //stringBuffer_shehui = new StringBuffer();
                dataBeanList_shehui = new ArrayList<>();
                for(int i=0;i<itemSize;i++){
                    //获取单个DataBean
                    Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
                    dataBeanList_shehui.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                            dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                }
                item_adapter = new News_Item_Adapter(getApplicationContext(),dataBeanList_shehui);
                //为lv_shehui设置Adapter
                lv_shehui.setAdapter(item_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity",volleyError.getMessage()+"");
            }
        });
        requestQueue.add(stringRequest);
    }
    private void request_yule(String url){
        stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                Gson gson = new GsonBuilder().create();
                //反序列化
                Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                //返回的item项的总数
                int itemSize = news_bean.getResult().getData().size();
                //获取DataBean集合
                List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                //提取数据
                //stringBuffer_yule = new StringBuffer();
                dataBeanList_yule = new ArrayList<>();
                for(int i=0;i<itemSize;i++){
                    //获取单个DataBean
                    Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
                    dataBeanList_yule.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                            dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                }
                item_adapter = new News_Item_Adapter(getApplicationContext(),dataBeanList_yule);
                //为lv_yule设置Adapter
                lv_yule.setAdapter(item_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity",volleyError.getMessage()+"");
            }
        });
        requestQueue.add(stringRequest);
    }
    private void request_tiyu(String url){
        stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                Gson gson = new GsonBuilder().create();
                //反序列化
                Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                //返回的item项的总数
                int itemSize = news_bean.getResult().getData().size();
                //获取DataBean集合
                List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                //提取数据
                //stringBuffer_tiyu = new StringBuffer();
                dataBeanList_tiyu = new ArrayList<>();
                for(int i=0;i<itemSize;i++){
                    //获取单个DataBean
                    Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
                    dataBeanList_tiyu.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                            dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                }
                item_adapter = new News_Item_Adapter(getApplicationContext(),dataBeanList_tiyu);
                //为lv_tiyu设置Adapter
                lv_tiyu.setAdapter(item_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity",volleyError.getMessage()+"");
            }
        });
        requestQueue.add(stringRequest);
    }
    private void request_junshi(String url){
        stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                Gson gson = new GsonBuilder().create();
                //反序列化
                Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                //返回的item项的总数
                int itemSize = news_bean.getResult().getData().size();
                //获取DataBean集合
                List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                //提取数据
                //stringBuffer_tiyu = new StringBuffer();
                dataBeanList_junshi = new ArrayList<>();
                for(int i=0;i<itemSize;i++){
                    //获取单个DataBean
                    Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
                    dataBeanList_junshi.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                            dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                }
                item_adapter = new News_Item_Adapter(getApplicationContext(),dataBeanList_junshi);
                //为lv_tiyu设置Adapter
                lv_junshi.setAdapter(item_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity",volleyError.getMessage()+"");
            }
        });
        requestQueue.add(stringRequest);
    }
    private void request_keji(String url){
        stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                Gson gson = new GsonBuilder().create();
                //反序列化
                Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                //返回的item项的总数
                int itemSize = news_bean.getResult().getData().size();
                //获取DataBean集合
                List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                //提取数据
                //stringBuffer_tiyu = new StringBuffer();
                dataBeanList_keji = new ArrayList<>();
                for(int i=0;i<itemSize;i++){
                    //获取单个DataBean
                    Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
                    dataBeanList_keji.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                            dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                }
                item_adapter = new News_Item_Adapter(getApplicationContext(),dataBeanList_keji);
                //为lv_tiyu设置Adapter
                lv_keji.setAdapter(item_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity",volleyError.getMessage()+"");
            }
        });
        requestQueue.add(stringRequest);
    }
    private void request_caijing(String url){
        stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                Gson gson = new GsonBuilder().create();
                //反序列化
                Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                //返回的item项的总数
                int itemSize = news_bean.getResult().getData().size();
                //获取DataBean集合
                List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                //提取数据
                //stringBuffer_tiyu = new StringBuffer();
                dataBeanList_caijing = new ArrayList<>();
                for(int i=0;i<itemSize;i++){
                    //获取单个DataBean
                    Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
                    dataBeanList_caijing.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                            dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                }
                item_adapter = new News_Item_Adapter(getApplicationContext(),dataBeanList_caijing);
                //为lv_tiyu设置Adapter
                lv_caijing.setAdapter(item_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity",volleyError.getMessage()+"");
            }
        });
        requestQueue.add(stringRequest);
    }
    private void request_shishang(String url){
        stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                Gson gson = new GsonBuilder().create();
                //反序列化
                Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                //返回的item项的总数
                int itemSize = news_bean.getResult().getData().size();
                //获取DataBean集合
                List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                //提取数据
                dataBeanList_shishang = new ArrayList<>();
                for(int i=0;i<itemSize;i++){
                    //获取单个DataBean
                    Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
                    dataBeanList_shishang.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                            dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                }
                item_adapter = new News_Item_Adapter(getApplicationContext(),dataBeanList_shishang);
                //为lv_tiyu设置Adapter
                lv_shishang.setAdapter(item_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity",volleyError.getMessage()+"");
            }
        });
        requestQueue.add(stringRequest);
    }
    private void request_guonei(String url){
        stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                Gson gson = new GsonBuilder().create();
                //反序列化
                Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                //返回的item项的总数
                int itemSize = news_bean.getResult().getData().size();
                //获取DataBean集合
                List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                //提取数据
                dataBeanList_guonei = new ArrayList<>();
                for(int i=0;i<itemSize;i++){
                    //获取单个DataBean
                    Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
                    dataBeanList_guonei.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                            dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                }
                item_adapter = new News_Item_Adapter(getApplicationContext(),dataBeanList_guonei);
                //为lv_tiyu设置Adapter
                lv_guonei.setAdapter(item_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity",volleyError.getMessage()+"");
            }
        });
        requestQueue.add(stringRequest);
    }
    private void request_guoji(String url){
        stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Toast.makeText(MainActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                Gson gson = new GsonBuilder().create();
                //反序列化
                Image_News_Bean news_bean = gson.fromJson(s,Image_News_Bean.class);
                //返回的item项的总数
                int itemSize = news_bean.getResult().getData().size();
                //获取DataBean集合
                List<Image_News_Bean.ResultBean.DataBean> list = news_bean.getResult().getData();
                //提取数据
                dataBeanList_guoji = new ArrayList<>();
                for(int i=0;i<itemSize;i++){
                    //获取单个DataBean
                    Image_News_Bean.ResultBean.DataBean dataBean = list.get(i);
                    dataBeanList_guoji.add(new Image_News_Bean.ResultBean.DataBean(dataBean.getTitle(),
                            dataBean.getDate(),dataBean.getAuthor_name(),dataBean.getThumbnail_pic_s(),dataBean.getUrl()));
                }
                item_adapter = new News_Item_Adapter(getApplicationContext(),dataBeanList_guoji);
                //为lv_tiyu设置Adapter
                lv_guoji.setAdapter(item_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.cancel();
                Toast.makeText(MainActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity",volleyError.getMessage()+"");
            }
        });
        requestQueue.add(stringRequest);
    }
    //RadioGroup设置监听
    private void radioButtonListenner(){
        rg_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.rb_top:
                        TYPE="?type=top";
                        String url_top = URL+TYPE+APPKEY;
                        //viewpager显示页
                        vp_showNews.setCurrentItem(0);
                        if(lv_top.getCount()==0){
                            dialog = ProgressDialog.show(MainActivity.this,"提示","数据加载中...",false,true);
                            //Toast.makeText(MainActivity.this, "listview数据为空，加载数据", Toast.LENGTH_SHORT).show();
                            request_top(url_top);
                        }
                        else Toast.makeText(MainActivity.this, "listview数据不为空，不加载数据", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.rb_shehui:
                        TYPE="?type=shehui";
                        String url_shehui = URL+TYPE+APPKEY;
                        vp_showNews.setCurrentItem(1);
                        if(lv_shehui.getCount()==0){
                            dialog = ProgressDialog.show(MainActivity.this,"提示","数据加载中...",false,true);
                            //Toast.makeText(MainActivity.this, "listview数据为空，加载数据", Toast.LENGTH_SHORT).show();
                            request_shehui(url_shehui);
                        }
                        else Toast.makeText(MainActivity.this, "listview数据不为空，不加载数据", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_yule:
                        TYPE="?type=yule";
                        String url_yule = URL+TYPE+APPKEY;
                        vp_showNews.setCurrentItem(2);
                        if(lv_yule.getCount()==0){
                            dialog = ProgressDialog.show(MainActivity.this,"提示","数据加载中...",false,true);
                            //Toast.makeText(MainActivity.this, "listview数据为空，加载数据", Toast.LENGTH_SHORT).show();
                            request_yule(url_yule);
                        }
                        else Toast.makeText(MainActivity.this, "listview数据不为空，不加载数据", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.rb_tiyu:
                        TYPE="?type=tiyu";
                        String url_tiyu = URL+TYPE+APPKEY;
                        vp_showNews.setCurrentItem(3);
                        if(lv_tiyu.getCount()==0){
                            dialog = ProgressDialog.show(MainActivity.this,"提示","数据加载中...",false,true);
                            //Toast.makeText(MainActivity.this, "listview数据为空，加载数据", Toast.LENGTH_SHORT).show();
                            request_tiyu(url_tiyu);
                        }
                        else Toast.makeText(MainActivity.this, "listview数据不为空，不加载数据", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.rb_keji:
                        TYPE="?type=keji";
                        String url_keji = URL+TYPE+APPKEY;
                        vp_showNews.setCurrentItem(4);
                        if(lv_keji.getCount()==0){
                            dialog = ProgressDialog.show(MainActivity.this,"提示","数据加载中...",false,true);
                            //Toast.makeText(MainActivity.this, "listview数据为空，加载数据", Toast.LENGTH_SHORT).show();
                            request_keji(url_keji);
                        }
                        else Toast.makeText(MainActivity.this, "listview数据不为空，不加载数据", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.rb_junshi:
                        TYPE="?type=junshi";
                        String url_junshi = URL+TYPE+APPKEY;
                        vp_showNews.setCurrentItem(5);
                        if(lv_junshi.getCount()==0){
                            dialog = ProgressDialog.show(MainActivity.this,"提示","数据加载中...",false,true);
                            //Toast.makeText(MainActivity.this, "listview数据为空，加载数据", Toast.LENGTH_SHORT).show();
                            request_junshi(url_junshi);
                        }
                        else Toast.makeText(MainActivity.this, "listview数据不为空，不加载数据", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.rb_caijing:
                        TYPE="?type=caijing";
                        String url_caijing = URL+TYPE+APPKEY;
                        vp_showNews.setCurrentItem(6);
                        if(lv_caijing.getCount()==0){
                            dialog = ProgressDialog.show(MainActivity.this,"提示","数据加载中...",false,true);
                            //Toast.makeText(MainActivity.this, "listview数据为空，加载数据", Toast.LENGTH_SHORT).show();
                            request_caijing(url_caijing);
                        }
                        else Toast.makeText(MainActivity.this, "listview数据不为空，不加载数据", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.rb_shishang:
                        TYPE="?type=shishang";
                        String url_shishang = URL+TYPE+APPKEY;
                        vp_showNews.setCurrentItem(7);
                        if(lv_shishang.getCount()==0){
                            dialog = ProgressDialog.show(MainActivity.this,"提示","数据加载中...",false,true);
                            //Toast.makeText(MainActivity.this, "listview数据为空，加载数据", Toast.LENGTH_SHORT).show();
                            request_shishang(url_shishang);
                        }
                        else Toast.makeText(MainActivity.this, "listview数据不为空，不加载数据", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.rb_guonei:
                        TYPE="?type=guonei";
                        String url_guonei = URL+TYPE+APPKEY;
                        vp_showNews.setCurrentItem(8);
                        if(lv_guonei.getCount()==0){
                            dialog = ProgressDialog.show(MainActivity.this,"提示","数据加载中...",false,true);
                            //Toast.makeText(MainActivity.this, "listview数据为空，加载数据", Toast.LENGTH_SHORT).show();
                            request_guonei(url_guonei);
                        }
                        else Toast.makeText(MainActivity.this, "listview数据不为空，不加载数据", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.rb_guoji:
                        TYPE="?type=guoji";
                        String url_guoji = URL+TYPE+APPKEY;
                        vp_showNews.setCurrentItem(9);
                        if(lv_guoji.getCount()==0){
                            dialog = ProgressDialog.show(MainActivity.this,"提示","数据加载中...",false,true);
                            //Toast.makeText(MainActivity.this, "listview数据为空，加载数据", Toast.LENGTH_SHORT).show();
                            request_guoji(url_guoji);
                        }
                        else Toast.makeText(MainActivity.this, "listview数据不为空，不加载数据", Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        });
    }
    //ListView设置监听
    private void listViewListenner(){
        lv_top.setOnItemClickListener(this);
        lv_shehui.setOnItemClickListener(this);
        lv_yule.setOnItemClickListener(this);
        lv_tiyu.setOnItemClickListener(this);
        lv_junshi.setOnItemClickListener(this);
        lv_keji.setOnItemClickListener(this);
        lv_caijing.setOnItemClickListener(this);
        lv_shishang.setOnItemClickListener(this);
        lv_guonei.setOnItemClickListener(this);
        lv_guoji.setOnItemClickListener(this);

        lv_top.setOnScrollListener(this);
        lv_shehui.setOnScrollListener(this);
        lv_yule.setOnScrollListener(this);
        lv_tiyu.setOnScrollListener(this);
        lv_junshi.setOnScrollListener(this);
        lv_keji.setOnScrollListener(this);
        lv_caijing.setOnScrollListener(this);
        lv_shishang.setOnScrollListener(this);
        lv_guonei.setOnScrollListener(this);
        lv_guoji.setOnScrollListener(this);
    }
    //ViewPager设置监听
    private void viewPagerListenner(){
        vp_showNews.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initAllRadioButton();
                switch (position){
                    case 0:
                        rb_top.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.rb_Selected));
                        break;
                    case 1:
                        rb_shehui.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.rb_Selected));
                        break;
                    case 2:
                        rb_yule.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.rb_Selected));
                        break;
                    case 3:
                        rb_tiyu.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.rb_Selected));
                        break;
                    case 4:
                        rb_keji.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.rb_Selected));
                        break;
                    case 5:
                        rb_junshi.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.rb_Selected));
                        break;
                    case 6:
                        rb_caijing.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.rb_Selected));
                        break;
                    case 7:
                        rb_shishang.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.rb_Selected));
                        break;
                    case 8:
                        rb_guonei.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.rb_Selected));
                        break;
                    case 9:
                        rb_guoji.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.rb_Selected));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            private void initAllRadioButton(){
                rb_top.setChecked(false);
                rb_shehui.setChecked(false);
                rb_yule.setChecked(false);
                rb_tiyu.setChecked(false);
                rb_junshi.setChecked(false);
                rb_keji.setChecked(false);
                rb_caijing.setChecked(false);
                rb_shishang.setChecked(false);
                rb_guonei.setChecked(false);
                rb_guoji.setChecked(false);
                rb_top.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.black));
                rb_shehui.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.black));
                rb_yule.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.black));
                rb_tiyu.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.black));
                rb_junshi.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.black));
                rb_keji.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.black));
                rb_caijing.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.black));
                rb_shishang.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.black));
                rb_guonei.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.black));
                rb_guoji.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.black));
            }
        });
    }
    //SwipeRefreshLayout设置监听
    private void refreshListenner(){
        refreshLayout_top.setOnRefreshListener(this);
    }
    //itemClick事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent;
        switch (adapterView.getId()){
            case R.id.lv_top:
                intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",dataBeanList_top.get(i).getUrl());
                startActivity(intent);
                //Toast.makeText(MainActivity.this, dataBeanList_top.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.lv_shehui:
                intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",dataBeanList_shehui.get(i).getUrl());
                startActivity(intent);
                //Toast.makeText(MainActivity.this, dataBeanList_shehui.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.lv_yule:
                intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",dataBeanList_yule.get(i).getUrl());
                startActivity(intent);
                //Toast.makeText(MainActivity.this, dataBeanList_yule.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.lv_tiyu:
                intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",dataBeanList_tiyu.get(i).getUrl());
                startActivity(intent);
                //Toast.makeText(MainActivity.this, dataBeanList_tiyu.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.lv_junshi:
                intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",dataBeanList_junshi.get(i).getUrl());
                startActivity(intent);
                //Toast.makeText(MainActivity.this, dataBeanList_tiyu.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.lv_keji:
                intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",dataBeanList_keji.get(i).getUrl());
                startActivity(intent);
                //Toast.makeText(MainActivity.this, dataBeanList_tiyu.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.lv_caijing:
                intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",dataBeanList_caijing.get(i).getUrl());
                startActivity(intent);
                //Toast.makeText(MainActivity.this, dataBeanList_tiyu.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.lv_shishang:
                intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",dataBeanList_shishang.get(i).getUrl());
                startActivity(intent);
                //Toast.makeText(MainActivity.this, dataBeanList_tiyu.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.lv_guonei:
                intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",dataBeanList_guonei.get(i).getUrl());
                startActivity(intent);
                //Toast.makeText(MainActivity.this, dataBeanList_tiyu.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.lv_guoji:
                intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url",dataBeanList_guoji.get(i).getUrl());
                startActivity(intent);
                //Toast.makeText(MainActivity.this, dataBeanList_tiyu.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
    //再按一次退出
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-exitTime>2000){
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime= System.currentTimeMillis();
        }
        else{super.onBackPressed();}
    }
    //检测网络是否可用
    public void netWork(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager.getActiveNetworkInfo()!=null){
            Toast.makeText(this, "点击上方新闻项查看该类新闻", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "网络连接不可用", Toast.LENGTH_SHORT).show();
        }
    }
    //ListView的滚动状态监听
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if(i== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
            if(absListView.getLastVisiblePosition()==absListView.getCount()-1){
                Toast.makeText(MainActivity.this, "已经到最下面了哦", Toast.LENGTH_SHORT).show();
                //loadData();
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void onRefresh() {
        Toast.makeText(MainActivity.this, "开始刷新了", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(0x123,3000);
            }
        }).start();
    }
}
