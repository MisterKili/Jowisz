package com.example.jowisz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jowisz.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {

    private List<Product> mProducts;
    private List<Product> mProductsCopy = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductsListAdapter(Context context, List<Product> products) {
        mProducts = products;
        mInflater = LayoutInflater.from(context);
//        mProducts = new ArrayList<>();
        mProductsCopy.addAll(products);
        System.out.println(mProductsCopy.toString());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = mInflater.inflate(R.layout.product_row, parent, false);

        return new ProductsListAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvName.setText(mProducts.get(position).getName());
        holder.tvProducer.setText(mProducts.get(position).getProducer());
        holder.tvCategory.setText(mProducts.get(position).getCategory());
        holder.tvPrice.setText(String.valueOf(mProducts.get(position).getPriceUnit()));
        holder.tvAvaibility.setText(String.valueOf(mProducts.get(position).getAvaibility()));
        holder.imageView.setImageResource(R.drawable.jupiter);
    }

    public void setClickListener(ProductsListAdapter.ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName, tvCategory, tvPrice, tvAvaibility, tvProducer;
        ImageView imageView;

        ViewHolder(View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCategory = itemView.findViewById(R.id.tvProductCategory);
            tvAvaibility = itemView.findViewById(R.id.tvAvaibility);
            tvProducer = itemView.findViewById(R.id.tvProducer);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void filter(String text) {
        mProducts.clear();
        if(text.isEmpty()){
            mProducts.addAll(mProductsCopy);
        } else{
            text = text.toLowerCase();
            for(Product item: mProductsCopy){
                if(item.name.toLowerCase().contains(text)) {
                    mProducts.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

}