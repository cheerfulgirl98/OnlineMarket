package com.sepideh.onlinemarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.data.ProductInfo;
import com.sepideh.onlinemarket.data.Sabad;
import com.sepideh.onlinemarket.second.activity.SecondActivity;
import com.sepideh.onlinemarket.utils.PublicMethods;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pc on 4/9/2019.
 */

public class SabadAdapter extends RecyclerView.Adapter<SabadAdapter.MyViewHolder> {

    Context mContext;
    List<Sabad> sabads;

    int number;

    public SabadAdapter(Context mContext, List<Sabad> favorits) {
        this.mContext = mContext;
        this.sabads = favorits;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sabad_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

        Picasso.with(mContext).load(sabads.get(position).getUrl()).into(myViewHolder.proImage);


        myViewHolder.proBrand.setText(mContext.getString(R.string.brand) + "" + sabads.get(position).getBrand());
        if (!sabads.get(position).getModel().equals("")) {
            myViewHolder.proModel.setVisibility(View.VISIBLE);
            myViewHolder.proModel.setText(mContext.getString(R.string.model) + sabads.get(position).getModel());
        }

        myViewHolder.proName.setText(sabads.get(position).getName());
        myViewHolder.proNum.setText(String.valueOf(sabads.get(position).getNum()));

        if (sabads.get(position).getPrice() != 0) {
            myViewHolder.proPrice.setVisibility(View.VISIBLE);
            myViewHolder.proPrice.setText(PublicMethods.changeToPersianNumber(String.format("%,d %s", sabads.get(position).getPrice(), mContext.getString(R.string.toman))));
            myViewHolder.proPrice.setPaintFlags(myViewHolder.proPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        int discount = Integer.parseInt(sabads.get(position).getDiscount());
        if (!sabads.get(position).getDiscount().equals("")) {
            myViewHolder.proDiscount.setVisibility(View.VISIBLE);
            myViewHolder.proDiscount.setText(PublicMethods.changeToPersianNumber(String.format("%,d %s", discount, mContext.getString(R.string.toman))));
        }

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int proCost=(sabads.get(position).getNum() * (Integer.parseInt(sabads.get(position).getDiscount())));
                Log.d("deel", "onClick: "+proCost);
                clickInstance.deleteClicked(sabads.get(position).getPro_id(),proCost);
                sabads.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,sabads.size());



            }
        });


        myViewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickInstance.plusClicked(sabads.get(position));
                number = sabads.get(position).getNum();
                myViewHolder.proNum.setText(String.valueOf(number));
            }
        });

        myViewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int proNumV = Integer.parseInt(myViewHolder.proNum.getText().toString());
                if (proNumV > 1) {
                    clickInstance.minusClicked(sabads.get(position));
                    number = sabads.get(position).getNum();
                    myViewHolder.proNum.setText(String.valueOf(number));
                }
            }
        });

        myViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Sabad selectedProduct = sabads.get(position);
                ProductInfo productInfo = new ProductInfo();
                String proid = String.valueOf(selectedProduct.getPro_id());
                productInfo.setId(proid).setName(selectedProduct.getName())
                        .setBrand(selectedProduct.getBrand()).setModel(selectedProduct.getModel()).setPrice(selectedProduct.getPrice())
                        .setDiscount(selectedProduct.getDiscount());

                Bundle bundle = new Bundle();
                bundle.putSerializable("selected_product", productInfo);

                //for activity
                Intent intent = new Intent(mContext, SecondActivity.class);
                intent.putExtra("selected_product_bundle", bundle);
                mContext.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return sabads.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parent;
        ImageView proImage;
        TextView proName, proBrand, proModel, proNum, proPrice, proDiscount, delete;
        Button plus, minus;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.rel_row_parent);
            proImage = itemView.findViewById(R.id.img_sabad_pic);
            proName = itemView.findViewById(R.id.txv_sabad_name);
            proBrand = itemView.findViewById(R.id.txv_sabad_brand);
            proModel = itemView.findViewById(R.id.txv_sabad_model);
            proNum = itemView.findViewById(R.id.txv_sabad_num);
            proPrice = itemView.findViewById(R.id.txv_sabad_price);
            proDiscount = itemView.findViewById(R.id.txv_sabad_discount);
            delete = itemView.findViewById(R.id.txv_sabad_delete);
            plus = itemView.findViewById(R.id.btn_sabad_plus);
            minus = itemView.findViewById(R.id.btn_sabad_minus);


        }
    }

    public MyClicked clickInstance;

    public interface MyClicked {
        void plusClicked(Sabad sabad);

        void minusClicked(Sabad sabad);

        void deleteClicked(String pro_id,int proCost);
    }
}
