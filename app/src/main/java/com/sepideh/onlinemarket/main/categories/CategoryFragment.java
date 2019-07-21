package com.sepideh.onlinemarket.main.categories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.adapter.ExpandableCategoryAdapter;
import com.sepideh.onlinemarket.base.BaseFragment;
import com.sepideh.onlinemarket.children.ChildrenActivity;
import com.sepideh.onlinemarket.data.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 4/11/2019.
 */

public class CategoryFragment extends BaseFragment implements CategoryContract.MyView {

    private CategoryContract.MyPresenter myPresenter;
    private ExpandableListView listView;
    private ExpandableCategoryAdapter adapter;


    private List<String> categoryHeaders;
    private HashMap<String, List<String>> categoryChildren;
    private List<String> men, women, boys, girls;
    private int previousItem = -1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresenter = new CategoryPresenter(new CategoryModel());
    }

    @Override
    public void onStart() {
        super.onStart();
        myPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        myPresenter.detachView();
    }

    @Override
    public void setUpViews() {

        prepareListData();

        listView = rootView.findViewById(R.id.lsv_category);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {

                return false;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                int catHeader = groupPosition + 1;
                String catChild = categoryChildren.get(categoryHeaders.get(groupPosition)).get(childPosition);


                Intent intent = new Intent(getViewContext(), ChildrenActivity.class);
                intent.putExtra("catHeader", catHeader);
                intent.putExtra("catChild", catChild);
                listView.collapseGroup(groupPosition);
                startActivity(intent);

                return false;
            }
        });
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                if (groupPosition != previousItem)
                    listView.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_category;
    }


    private void prepareListData() {
        categoryHeaders = new ArrayList<String>();

        categoryHeaders.add(getString(R.string.men));
        categoryHeaders.add(getString(R.string.women));
        categoryHeaders.add(getString(R.string.girls));
        categoryHeaders.add(getString(R.string.boys));

        myPresenter.getChildern();

    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void childrenAreReady(List<Category> childern) {

        categoryChildren = new HashMap<String, List<String>>();

        men = new ArrayList<>();
        women = new ArrayList<>();
        boys = new ArrayList<>();
        girls = new ArrayList<>();

        for (Category category : childern) {
            switch (category.getCatHeader()) {
                case 1:
                    men.add(category.getCatChild());
                    break;
                case 2:
                    women.add(category.getCatChild());
                    break;
                case 3:
                    girls.add(category.getCatChild());
                    break;
                case 4:
                    boys.add(category.getCatChild());
                    break;

            }
        }


        categoryChildren.put(categoryHeaders.get(0), men);
        categoryChildren.put(categoryHeaders.get(1), women);
        categoryChildren.put(categoryHeaders.get(2), girls);
        categoryChildren.put(categoryHeaders.get(3), boys);

        //set adapter here because become shure the children list is ready before setAdapter
        adapter = new ExpandableCategoryAdapter(getViewContext(), categoryHeaders, categoryChildren);
        listView.setAdapter(adapter);

    }
}
