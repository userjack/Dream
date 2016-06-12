package com.denny.dream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.denny.dream.adapter.RecommandAdapater;
import com.denny.dream.bean.TitleModal;
import com.denny.dream.data.DataInterface;
import com.denny.dream.data.Utils;
import com.denny.dream.data.WebURL;
import com.denny.dream.widget.header.HeaderADView;
import com.denny.dream.widget.header.HeaderTitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Activity context;
    @Bind(R.id.ls)
    ListView ls;
    private FragmentTransaction manager;
    private List<Map<String, Object>> adsData;
    //    解析首页数据
    private List<Map<String, Object>> list_info;
    private List<TitleModal> titleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager = getSupportFragmentManager().beginTransaction();
        initTitleData();
        setListView();

    }

    private void setListView() {
        RecommandAdapater adapter = new RecommandAdapater(this,titleData);
        ls.setAdapter(adapter);
    }

    private void initTitleData() {
        titleData = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebURL.BASE_JSON_URL).addConverterFactory(GsonConverterFactory.create()).build();
        DataInterface apiService = retrofit.create(DataInterface.class);
        Call<List<TitleModal>> call = apiService.reQuestTitle(Utils.getDataTime());
        call.enqueue(new Callback<List<TitleModal>>() {
            @Override
            public void onResponse(Response<List<TitleModal>> response, Retrofit retrofit) {
                titleData = response.body();
                Log.i("tag", "adsData===****" + titleData.size());
                if (titleData.size() > 7){
                    for (int i = titleData.size()-1; i > 7;i--){
                        titleData.remove(i);
                    }
                }
                HeaderADView headerADView = new HeaderADView(context);
                headerADView.fillView(titleData, ls);

                HeaderTitleView headerTitleView = new HeaderTitleView(MainActivity.this);
                headerTitleView.fillView(titleData, ls);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        String[] str = new String[]{"推荐", "栏目", "直播", "我的"};
        for (int i = 0; i < str.length; i++) {
            menu.add(str[i]);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        String title = item.getTitle().toString();
        if (title.equals("推荐")) {

        } else if (title.equals("栏目")) {

        } else if (title.equals("直播")) {
            startActivity(new Intent(this,CustomActivity.class));
        } else if (title.equals("我的")) {

        }
        setTitle(title);

        return true;
    }
}
