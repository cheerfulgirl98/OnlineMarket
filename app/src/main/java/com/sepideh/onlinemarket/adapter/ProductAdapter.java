package com.sepideh.onlinemarket.adapter;

import android.content.Context;
import android.content.Intent;
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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    Context mContext;
    List<ProductInfo> suggestedProductList;

    public ProductAdapter(Context mContext, List<ProductInfo> suggestedProductList) {
        this.mContext = mContext;
        this.suggestedProductList = suggestedProductList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        Picasso.with(mContext).load(suggestedProductList.get(i).getUrl()).into(myViewHolder.proImage);


        if (!suggestedProductList.get(i).getBrand().equals("")) {
            myViewHolder.proBrand.setText(suggestedProductList.get(i).getBrand());
        } else {
            myViewHolder.proBrand.setText("مدل" + suggestedProductList.get(i).getModel());
        }
        myViewHolder.proName.setText(suggestedProductList.get(i).getName());

        int discount = Integer.parseInt(suggestedProductList.get(i).getDiscount());
        if (!suggestedProductList.get(i).getDiscount().equals("")) {
            myViewHolder.proDiscount.setVisibility(View.VISIBLE);
            myViewHolder.proDiscount.setText(PublicMethods.changeToPersianNumber(String.format("%,d %s", discount, mContext.getString(R.string.toman))));
        }
        myViewHolder.parent.setOnClickListener(new View.OnClickListener() {
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
//                transaction.replace(R.id.frame_main_container, fragmentDetail);
//                transaction.addToBackStack(null);
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
        TextView proName, proBrand, proDiscount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.rel_row_parent);
            proImage = itemView.findViewById(R.id.img_product);
            proName = itemView.findViewById(R.id.txv_name);
            proBrand = itemView.findViewById(R.id.txv_brand);
            proDiscount = itemView.findViewById(R.id.txv_discount);


        }
    }
}
