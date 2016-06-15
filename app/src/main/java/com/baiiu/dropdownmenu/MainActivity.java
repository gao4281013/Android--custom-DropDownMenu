package com.baiiu.dropdownmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.dropdownmenu.entity.FilterUrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnFilterDoneListener,DropDownMenu.CallBackTcpLinstener{

    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    @Bind(R.id.mFilterContentView)
    RecyclerView recyclerView;

    private boolean isFollowClicked =false;
    private List<String> infoList;

    private DropDownMenu.CallBackTcpLinstener  callBackTcpLinstener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        initFilterDropDownView();

        initRecycleView();
    }

    private String[] itemName = new String[]{"1245","7845","79845","546546","79875","123456","79565","798456","79546","7894512","1234567","7845612"};
    private String[] itemName1 = new String[]{"79875","123456","79565","798456","79546","7894512","1234567","7845612"};

    private void initFilterDropDownView() {
        List<Map<String,String>> titleLists = new ArrayList<Map<String, String>>();
        String[] titleList = new String[]{"全部分类", "场地","关注优先"};
        Map<String,String> map1 = new HashMap<String, String>();
        map1.put("title",titleList[0]);
        map1.put("needPic","0");
        titleLists.add(map1);
        Map<String,String> map2 = new HashMap<String, String>();
        map2.put("title",titleList[1]);
        map2.put("needPic","0");
        titleLists.add(map2);
        Map<String,String> map3 = new HashMap<String, String>();
        map3.put("title",titleList[2]);
        map3.put("needPic","1");
        titleLists.add(map3);

        dropDownMenu.setMenuAdapter(new DropMenuAdapter(this, titleLists, this));
        dropDownMenu.setCallBackTcpLinstener(this);
    }

    public void initRecycleView(){
        infoList = Arrays.asList(itemName);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(new RecycleAdapter(MainActivity.this, infoList));
    }



    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        if (position != 3) {
            dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }

        dropDownMenu.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FilterUrl.instance().clear();
    }

    @Override
    public void handleCallBack() {
           if (!isFollowClicked) {
               dropDownMenu.close();
               isFollowClicked = !isFollowClicked;
               infoList = Arrays.asList(itemName1);
               recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayout.VERTICAL, false));
               recyclerView.setAdapter(new RecycleAdapter(MainActivity.this, infoList));
           }else{
               dropDownMenu.close();
               isFollowClicked = !isFollowClicked;
               infoList = Arrays.asList(itemName);
               recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayout.VERTICAL, false));
               recyclerView.setAdapter(new RecycleAdapter(MainActivity.this, infoList));                }
    }
}
