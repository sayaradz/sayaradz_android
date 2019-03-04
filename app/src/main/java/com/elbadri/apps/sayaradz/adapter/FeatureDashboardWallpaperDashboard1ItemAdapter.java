package com.elbadri.apps.sayaradz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.elbadri.apps.sayaradz.R;
import com.elbadri.apps.sayaradz.model.WallpaperItem;
import com.elbadri.apps.sayaradz.utils.Utils;

import java.util.List;

/**
 * Created by Panacea-Soft on 17/7/18.
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
public class FeatureDashboardWallpaperDashboard1ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WallpaperItem> wallpaperItemArrayList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, WallpaperItem obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public FeatureDashboardWallpaperDashboard1ItemAdapter(List<WallpaperItem> WallpaperItemArrayList) {
        this.wallpaperItemArrayList = WallpaperItemArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feature_dashboard_wallpaper_dashboard_1_item, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            WallpaperItem wallpaperItem = wallpaperItemArrayList.get(position);

            ItemViewHolder holder = (ItemViewHolder) viewHolder;

            holder.viewName.setText(wallpaperItem.viewCount);

            Context context = holder.holderCardView.getContext();

            int id = Utils.getDrawableInt(context, wallpaperItem.imageName);
            Utils.setImageToImageView(context, holder.itemImageView, id);

            if (itemClickListener != null) {
                holder.holderCardView.setOnClickListener((View v) -> itemClickListener.onItemClick(v, wallpaperItemArrayList.get(position), position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return wallpaperItemArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView viewName;
        CardView holderCardView;

        ItemViewHolder(View view) {
            super(view);

            itemImageView = view.findViewById(R.id.itemImageView);
            holderCardView = view.findViewById(R.id.holderCardView);
            viewName = view.findViewById(R.id.viewName);

        }
    }
}
