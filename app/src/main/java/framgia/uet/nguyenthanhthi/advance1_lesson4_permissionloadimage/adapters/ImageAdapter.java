package framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.R;

/**
 * Created by thanhthi on 02/11/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<String> mPaths;
    private LayoutInflater mInflater;

    public ImageAdapter(List<String> paths) {
        mPaths = paths;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mPaths.get(position));
    }

    @Override
    public int getItemCount() {
        return mPaths != null ? mPaths.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageLoad;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageLoad = itemView.findViewById(R.id.image_load);
        }

        public void bindData(String path) {
            if (path == null) return;
            Glide.with(itemView.getContext())
                    .load(path)
                    .into(mImageLoad);
        }
    }
}
