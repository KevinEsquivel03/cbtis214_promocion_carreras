package com.example.cbtis214_promocion_carreras;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.TimerTask;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private int[] bannerImages;

    public BannerAdapter(int[] bannerImages) {
        this.bannerImages = bannerImages;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        holder.imageView.setImageResource(bannerImages[position]);
    }

    @Override
    public int getItemCount() {
        return bannerImages.length;
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }
    }
}

    class BannerTimerTask extends TimerTask {

    private ViewPager2 viewPager;
    private int numBannerImages;
    private int currentPosition = 0;

    public BannerTimerTask(ViewPager2 viewPager, int numBannerImages) {
        this.viewPager = viewPager;
        this.numBannerImages = numBannerImages;
    }

    @Override
    public void run() {
        currentPosition = (currentPosition + 1) % numBannerImages;
        viewPager.setCurrentItem(currentPosition);
    }
}
