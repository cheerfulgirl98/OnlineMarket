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
import com.sepideh.onlinemarket.utils.PublicMethods;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pc on 4/9/2019.
 */

public class ChildProductAdapter extends RecyclerView.Adapter<ChildProductAdapter.MyViewHolder> {

    Context mContext;
    List<ProductInfo> suggestedProductList;

    public ChildProductAdapter(Context mContext, List<ProductInfo> suggestedProductList) {
        this.mContext = mContext;
        this.suggestedProductList = suggestedProductList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cat_product_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        Picasso.with(mContext).load(suggestedProductList.get(i).getUrl()).into(myViewHolder.proImage);


        myViewHolder.proBrand.setText(suggestedProductList.get(i).getBrand());
        if (!suggestedProductList.get(i).getModel().equals("")){
            myViewHolder.proModel.setVisibility(View.VISIBLE);
            myViewHolder.proModel.setText("مدل : " + suggestedProductList.get(i).getModel());
        }


        myViewHolder.proName.setText(suggestedProductList.get(i).getName());

        if (suggestedProductList.get(i).getPrice() != 0)

        {
            myViewHolder.proPrice.setVisibility(View.VISIBLE);
            myViewHolder.proPrice.setText(PublicMethods.changeToPersianNumber(String.format("%,d %s", suggestedProductList.get(i).getPrice(), mContext.getString(R.string.toman))));
            myViewHolder.proPrice.setPaintFlags(myViewHolder.proPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        int discount = Integer.parseInt(suggestedProductList.get(i).getDiscount());
        if (!suggestedProductList.get(i).getDiscount().equals(""))

        {
            myViewHolder.proDiscount.setVisibility(View.VISIBLE);
            myViewHolder.proDiscount.setText(PublicMethods.changeToPersianNumber(String.format("%,d %s", discount, mContext.getString(R.string.toman))));
        }
        myViewHolder.parent.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("selected_product", suggestedProductList.get(i));

                //for activity
                Intent intent=new Intent(mContext,SecondActivity.class);
                intent.putExtra("selected_product_bundle",bundle);
                mContext.startActivity(intent);

//                FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                DetailFragment fragmentDetail = new DetailFragment();
//                fragmentDetail.setArguments(bundle);
//                transaction.addToBackStack(null);
//                transaction.replace(R.id.frame_main_container, fragmentDetail);
//                transaction.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return suggestedProductList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout parent;
        ImageView proImage;
        TextView proName, proBrand, proModel, proPrice, proDiscount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.rel_row_parent);
            proImage = itemView.findViewById(R.id.img_show_pic);
            proName = itemView.findViewById(R.id.txv_show_name);
            proBrand = itemView.findViewById(R.id.txv_show_brand);
            proModel = itemView.findViewById(R.id.txv_show_model);
            proPrice = itemView.findViewById(R.id.txv_show_price);
            proDiscount = itemView.findViewById(R.id.txv_show_discount);


        }
    }
}
