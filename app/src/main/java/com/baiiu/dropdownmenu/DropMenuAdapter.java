package com.baiiu.dropdownmenu;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.baiiu.dropdownmenu.view.betterDoubleGrid.BetterDoubleGridView;
import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.adapter.MenuAdapter;
import com.baiiu.filter.adapter.SimpleLeftTextAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.DoubleListView;
import com.baiiu.filter.typeview.SingleGridView;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.CommonUtil;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.baiiu.dropdownmenu.entity.FilterType;
import com.baiiu.dropdownmenu.entity.FilterUrl;
import com.baiiu.dropdownmenu.view.doubleGrid.DoubleGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class DropMenuAdapter implements MenuAdapter{
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private List<Map<String,String>> titles;

    public DropMenuAdapter(Context context, List<Map<String,String>> titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
    }

    @Override
    public int getMenuCount() {
        return titles.size();
    }

    @Override
    public Map<String,String> getMenuTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getBottomMargin(int position) {
        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (position) {
            case 0:
                view = createSingleListView();
                break;
            case 1:
                view = createDoubleListView();
                break;
            case 2:
                 view = createSingleListView();
                break;
        }

        return view;
    }

    private View createSingleListView() {
        SingleListView<Map<String,String>> singleListView = new SingleListView<Map<String,String>>(mContext)
                .adapter(new SimpleLeftTextAdapter<Map<String,String>>(null, mContext) {
                    @Override
                    public String provideText(Map<String,String> map1) {
                        return map1.get("letf_text");
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }

                    @Override
                    public String provideRightText(Map<String,String> map1) {
                        return map1.get("right_text");
                    }
                })
                .onItemClick(new OnFilterItemClickListener<Map<String,String>>() {
                    @Override
                    public void onItemClick(Map<String,String> map) {
//                        FilterUrl.instance().singleListPosition = map1.get("abc");
//
//                        FilterUrl.instance().position = 0;
//                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<Map<String,String>> list = new ArrayList<>();
        Map<String,String> map ;
        for (int i = 0; i < 10; ++i) {
            map = new HashMap<String,String>();
            map.put("letf_text","left"+i);
            map.put("right_text","right"+i);
            list.add(map);
        }
        singleListView.setList(list, -1);
        return singleListView;
    }


    private View createDoubleListView() {
        DoubleListView<FilterType, Map<String,String>> comTypeDoubleListView = new DoubleListView<FilterType, Map<String,String>>(mContext)
                .leftAdapter(new SimpleTextAdapter<FilterType>(null, mContext) {
                    @Override
                    public String provideText(FilterType filterType) {
                        return filterType.desc;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 44), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                    }
                })
                .rightAdapter(new SimpleLeftTextAdapter<Map<String,String>>(null, mContext) {
                    @Override
                    public String provideText(Map<String,String> s) {
                        return s.get("left_text");
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {

                    }

                    @Override
                    public String provideRightText(Map<String, String> stringStringMap) {
                        return stringStringMap.get("right_text");
                    }

                    @Override
                    protected void initRightCheckedTextView(FilterCheckedTextView checkedTextView) {
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterType, Map<String,String>>() {
                    @Override
                    public List<Map<String,String>> provideRightList(FilterType item, int position) {
                        List<Map<String,String>> child = item.child;
                        if (CommonUtil.isEmpty(child)) {
                            FilterUrl.instance().doubleListLeft = item.desc;
                            FilterUrl.instance().doubleListRight = "";

                            FilterUrl.instance().position = 1;
                            FilterUrl.instance().positionTitle = item.desc;
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterType, Map<String,String>>() {
                    @Override
                    public void onRightItemClick(FilterType item, Map<String,String> map) {
                        FilterUrl.instance().doubleListLeft = item.desc;
                        FilterUrl.instance().doubleListRight = map.get("right_text");

                        FilterUrl.instance().position = 1;
                        FilterUrl.instance().positionTitle = map.get("right_text");

                        onFilterDone();
                    }

                });


        List<FilterType> list = new ArrayList<>();

        //第一项
        FilterType filterType = new FilterType();
        filterType.desc = "10";
        List<Map<String,String>> childList1 = new ArrayList<>();
        Map<String,String> map = null;
        for (int i = 0; i < 13; ++i) {
            map = new HashMap<String, String>();
            map.put("left_text","left"+i);
            map.put("right_text","right"+i);
            childList1.add(map);
        }
        filterType.child = childList1;
        list.add(filterType);

        //第二项
        filterType = new FilterType();
        filterType.desc = "11";
        List<Map<String,String>> childList = new ArrayList<>();
        for (int i = 0; i < 13; ++i) {
            map = new HashMap<String, String>();
            map.put("left_text","left"+i);
            map.put("right_text","right"+i);
            childList.add(map);
        }
        filterType.child = childList;
        list.add(filterType);

        //第三项
        filterType = new FilterType();
        filterType.desc = "12";
        List<Map<String,String>> childList2 = new ArrayList<Map<String,String>>();
        for (int i = 0; i < 3; ++i) {
            map = new HashMap<String, String>();
            map.put("left_text","left"+i);
            map.put("right_text","right"+i);
            childList2.add(map);
        }
        filterType.child = childList2;
        list.add(filterType);

        //初始化选中.
        comTypeDoubleListView.setLeftList(list, 1);
        comTypeDoubleListView.setRightList(list.get(1).child, -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }


//    private View createSingleGridView() {
//        SingleGridView<String> singleGridView = new SingleGridView<String>(mContext)
//                .adapter(new SimpleTextAdapter<String>(null, mContext) {
//                    @Override
//                    public String provideText(String s) {
//                        return s;
//                    }
//
//                    @Override
//                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
//                        checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
//                        checkedTextView.setGravity(Gravity.CENTER);
//                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
//                    }
//                })
//                .onItemClick(new OnFilterItemClickListener<String>() {
//                    @Override
//                    public void onItemClick(String item) {
//                        FilterUrl.instance().singleGridPosition = item;
//
//                        FilterUrl.instance().position = 2;
//                        FilterUrl.instance().positionTitle = item;
//
//                        onFilterDone();
//
//                    }
//                });
//
//        List<String> list = new ArrayList<>();
//        for (int i = 20; i < 39; ++i) {
//            list.add(String.valueOf(i));
//        }
//        singleGridView.setList(list, -1);
//
//
//        return singleGridView;
//    }


    private View createBetterDoubleGrid() {

        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }


        return new BetterDoubleGridView(mContext)
                .setmTopGridData(phases)
                .setmBottomGridList(areas)
                .setOnFilterDoneListener(onFilterDoneListener)
                .build();
    }


    private View createDoubleGrid() {
        DoubleGridView doubleGridView = new DoubleGridView(mContext);
        doubleGridView.setOnFilterDoneListener(onFilterDoneListener);


        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        doubleGridView.setTopGridData(phases);

        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }
        doubleGridView.setBottomGridList(areas);

        return doubleGridView;
    }


    private void onFilterDone() {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(0, "", "");
        }
    }

}
