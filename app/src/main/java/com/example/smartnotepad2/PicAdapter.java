package com.example.smartnotepad2;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.FoodViewHolder> {

    private List<Pic> mPics;
    private Context mContext;
    private Random mRandom = new Random();
    private RecyclerView mRecyclerView;

    public PicAdapter(Context context) {
        mPics = new ArrayList<>();
        mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    private Pic getRandomFood() {
        HashMap<String, Integer> sDefaultNamesAndIds = new HashMap<>();
        sDefaultNamesAndIds.put("Yang JingJun", R.drawable.a);

        sDefaultNamesAndIds.put("Yu RunZe", R.drawable.c);


        Pic pic = new Pic("Yang JingJun", sDefaultNamesAndIds.get("Yang JingJun"));

        if (mPics.size() != 0) {
            for (int i = 0; i < mPics.size(); i++) {
                if (sDefaultNamesAndIds.containsKey(mPics.get(i).getmName())) {
                    sDefaultNamesAndIds.remove(mPics.get(i).getmName());
                }
            }
        }

        String names[] = sDefaultNamesAndIds.keySet().toArray(new String[0]);
        if (names.length != 0) {
            String randomFoodName = names[mRandom.nextInt(names.length)];
            int randomFoodId = sDefaultNamesAndIds.get(randomFoodName);
            pic = new Pic(randomFoodName, randomFoodId);
        }

        return pic;
    }

    public void addFood() {
        mPics.add(0, getRandomFood());

        notifyItemInserted(0);
        notifyItemRangeChanged(0, mPics.size());

        mRecyclerView.getLayoutManager().scrollToPosition(0);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        final Pic Pic = mPics.get(position);
        String name = Pic.getmName();
        int id = Pic.getmId();


        holder.PicName.setText(name);
        holder.PicImage.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return mPics.size();
    }

    private void deleteFood(int position) {
        mPics.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(0, mPics.size());
    }


    class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView PicName;
        private ImageView PicImage;


        public FoodViewHolder(View itemView) {
            super(itemView);
            PicName = itemView.findViewById(R.id.view_name);
            PicImage = itemView.findViewById(R.id.food_view_image);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteFood(getAdapterPosition());
                    return false;
                }
            });
        }
    }



}