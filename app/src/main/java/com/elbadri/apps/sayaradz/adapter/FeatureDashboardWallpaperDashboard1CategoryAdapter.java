package com.elbadri.apps.sayaradz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.elbadri.apps.sayaradz.R;
import com.elbadri.apps.sayaradz.model.WallpaperCategory;
import com.elbadri.apps.sayaradz.utils.Utils;

import java.util.List;

/**
 * Created by Panacea-Soft on 17/7/18.
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
public class FeatureDashboardWallpaperDashboard1CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WallpaperCategory> WallpaperCategoryArrayList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, WallpaperCategory obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public FeatureDashboardWallpaperDashboard1CategoryAdapter(List<WallpaperCategory> WallpaperCategoryArrayList) {
        this.WallpaperCategoryArrayList = WallpaperCategoryArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feature_dashboard_wallpaper_dashboard_1_category_item, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            WallpaperCategory WallpaperCategory = WallpaperCategoryArrayList.get(position);

            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            holder.itemNameTextView.setText(WallpaperCategory.name);

            Context context = holder.constraintLayout.getContext();

            int id = Utils.getDrawableInt(context, WallpaperCategory.imageName);
            Utils.setCircleImageToImageView(context, holder.itemImageView, id, 2, R.color.md_grey_200);

            if (itemClickListener != null) {
                holder.constraintLayout.setOnClickListener((View v) -> itemClickListener.onItemClick(v, WallpaperCategoryArrayList.get(position), position));
            }
        }
    }

    @Override
    public int getItemCount() {

        if (WallpaperCategoryArrayList != null) {

            return WallpaperCategoryArrayList.size();

        } else {
            return 0;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemNameTextView;
        ConstraintLayout constraintLayout;

        ItemViewHolder(View view) {
            super(view);

            itemImageView = view.findViewById(R.id.itemImageView);
            itemNameTextView = view.findViewById(R.id.itemNameTextView);
            constraintLayout = view.findViewById(R.id.constraintLayout);

        }
    }
}
