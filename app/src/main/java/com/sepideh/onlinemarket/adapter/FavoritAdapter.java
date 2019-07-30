package com.sepideh.onlinemarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.second.activity.SecondActivity;
import com.sepideh.onlinemarket.sqlite.Favorit;
import com.sepideh.onlinemarket.utils.PublicMethods;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pc on 4/9/2019.
 */

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.MyViewHolder> {

    Context mContext;
    List<Favorit> favorits;

    public FavoritAdapter(Context mContext, List<Favorit> favorits) {
        this.mContext = mContext;
        this.favorits = favorits;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.favorit_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        Picasso.with(mContext).load(favorits.get(i).getUrl()).into(myViewHolder.proImage);


        myViewHolder.proBrand.setText(mContext.getString(R.string.brand) + "" + favorits.get(i).getBrand());
        if (!favorits.get(i).getModel().equals("")){
            myViewHolder.proModel.setVisibility(View.VISIBLE);
            myViewHolder.proModel.setText("مدل : " + favorits.get(i).getModel());
        }

        myViewHolder.proName.setText(favorits.get(i).getName());

        if (favorits.get(i).getPrice()!=0) {
            myViewHolder.proPrice.setVisibility(View.VISIBLE);
            myViewHolder.proPrice.setText(PublicMethods.changeToPersianNumber(String.format("%,d %s", favorits.get(i).getPrice(), mContext.getString(R.string.toman))));
            myViewHolder.proPrice.setPaintFlags(myViewHolder.proPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        int discount = Integer.parseInt(favorits.get(i).getDiscount());
        if (!favorits.get(i).getDiscount().equals("")) {
            myViewHolder.proDiscount.setVisibility(View.VISIBLE);
            myViewHolder.proDiscount.setText(PublicMethods.changeToPersianNumber(String.format("%,d %s", discount, mContext.getString(R.string.toman))));
        }

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteInterface.deleteClicked(favorits.get(i));
                favorits.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i,favorits.size());

            }
        });


        myViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Favorit selectedFavorit=favorits.get(i);
                ProductInfo productInfo=new ProductInfo();
                productInfo.setId(selectedFavorit.getProduct_id()).setName(selectedFavorit.getName())
                        .setBrand(selectedFavorit.getBrand()).setPrice(selectedFavorit.getPrice())
                        .setDiscount(selectedFavorit.getDiscount());

                Bundle bundle = new Bundle();
                bundle.putSerializable("selected_product", productInfo);

                //for activity
                Intent intent=new Intent(mContext,SecondActivity.class);
                intent.putExtra("selected_product_bundle",bundle);
                mContext.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return favorits.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parent;
        ImageView proImage;
        TextView proName, proBrand, proModel, proPrice, proDiscount,delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.rel_row_parent);
            proImage = itemView.findViewById(R.id.img_show_pic);
            proName = itemView.findViewById(R.id.txv_show_name);
            proBrand = itemView.findViewById(R.id.txv_show_brand);
            proModel = itemView.findViewById(R.id.txv_show_model);
            proPrice = itemView.findViewById(R.id.txv_show_price);
            proDiscount = itemView.findViewById(R.id.txv_show_discount);
            delete=itemView.findViewById(R.id.txv_favorit_delete);


        }
    }

    public DeleteInterface deleteInterface;
    public interface DeleteInterface{
        void deleteClicked(Favorit favorit);
    }
}
