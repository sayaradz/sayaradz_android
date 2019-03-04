package com.elbadri.apps.sayaradz.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elbadri.apps.sayaradz.R;
import com.elbadri.apps.sayaradz.adapter.FeatureDashboardWallpaperDashboard1CategoryAdapter;
import com.elbadri.apps.sayaradz.adapter.FeatureDashboardWallpaperDashboard1ItemAdapter;
import com.elbadri.apps.sayaradz.model.WallpaperCategory;
import com.elbadri.apps.sayaradz.model.WallpaperCategoryRepository;
import com.elbadri.apps.sayaradz.model.WallpaperItem;
import com.elbadri.apps.sayaradz.model.WallpaperItemRepository;

import java.util.List;

public class Dashboard extends Fragment {

    List<WallpaperItem> itemArrayList;
    List<WallpaperCategory> categoryList;
    FeatureDashboardWallpaperDashboard1CategoryAdapter wallpaperHome1CategoryAdapter;
    FeatureDashboardWallpaperDashboard1ItemAdapter itemAdapter;

    TextView viewAllCategoryTextView;
    TextView viewAllPhotoTextView;

    // RecyclerView
    RecyclerView categoryRecyclerView;
    RecyclerView photoRecyclerView;


    public static Dashboard newInstance() {
        return new Dashboard();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.feature_dashboard_wallpaper_dashboard_1_activity, container, false);

        initData();

        initUI(view);

        initDataBinding();

        initActions(view);

        return view;
    }


    //region Init Functions

    private void initData() {

        // get data
        itemArrayList = WallpaperItemRepository.getWallpaperItemList();

        categoryList = WallpaperCategoryRepository.getWallpaperCategoryList();

    }

    private void initUI(View view) {

//        initToolbar();

        wallpaperHome1CategoryAdapter = new FeatureDashboardWallpaperDashboard1CategoryAdapter(categoryList);

        itemAdapter = new FeatureDashboardWallpaperDashboard1ItemAdapter(itemArrayList);

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(mLayoutManager);
        categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        categoryRecyclerView.setNestedScrollingEnabled(false);

        // get Item recycler view
        photoRecyclerView = view.findViewById(R.id.photoRecyclerView);
        RecyclerView.LayoutManager mLayoutManagerForItems = new GridLayoutManager(view.getContext().getApplicationContext(), 2);

        photoRecyclerView.setLayoutManager(mLayoutManagerForItems);
        photoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        photoRecyclerView.setNestedScrollingEnabled(false);

        viewAllCategoryTextView = view.findViewById(R.id.viewAllCategoryTextView);
        viewAllPhotoTextView = view.findViewById(R.id.viewAllPhotoTextView);


    }

    private void initDataBinding() {
        // bind wallpaperHome2CategoryAdapter to recycler
        categoryRecyclerView.setAdapter(wallpaperHome1CategoryAdapter);

        // bind items
        photoRecyclerView.setAdapter(itemAdapter);
    }

    private void initActions(View myView) {
        wallpaperHome1CategoryAdapter.setOnItemClickListener((view, obj, position) -> Toast.makeText(myView.getContext().getApplicationContext(), "Clicked " + obj.name, Toast.LENGTH_SHORT).show());

        itemAdapter.setOnItemClickListener((view, obj, position) -> Toast.makeText(myView.getContext().getApplicationContext(), "Selected : " + obj.imageName, Toast.LENGTH_SHORT).show());

        viewAllCategoryTextView.setOnClickListener((View v) -> Toast.makeText(myView.getContext().getApplicationContext(), "Clicked View All Categories.", Toast.LENGTH_SHORT).show());

        viewAllPhotoTextView.setOnClickListener((View v) -> Toast.makeText(myView.getContext().getApplicationContext(), "Clicked View All Wallpapers.", Toast.LENGTH_SHORT).show());

    }


}
