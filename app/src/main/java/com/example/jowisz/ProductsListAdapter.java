package com.example.jowisz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {

    private String[] mDataset;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductsListAdapter(Context context, String[] myDataset) {
        mDataset = myDataset;
        mInflater = LayoutInflater.from(context);
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
        holder.tvName.setText(mDataset[position]);
        holder.tvProducer.setText("Producent");
        holder.tvCategory.setText("Moze myszka");
        holder.tvPrice.setText("50 zł");
        holder.tvAvaibility.setText("27");
    }

    public void setClickListener(ProductsListAdapter.ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName, tvCategory, tvPrice, tvAvaibility, tvProducer;

        ViewHolder(View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCategory = itemView.findViewById(R.id.tvProductCategory);
            tvAvaibility = itemView.findViewById(R.id.tvAvaibility);
            tvProducer = itemView.findViewById(R.id.tvProducer);

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

}
