package com.example.jowisz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jowisz.Model.Basket;

public class ProductsInBasketAdapter extends RecyclerView.Adapter<ProductsInBasketAdapter.ViewHolder> {
    private Basket basket;
    private LayoutInflater mInflater;
    private ProductsInBasketAdapter.ItemClickListener mClickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductsInBasketAdapter(Context context, Basket myDataset) {
        basket = myDataset;
        mInflater = LayoutInflater.from(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductsInBasketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = mInflater.inflate(R.layout.product_row, parent, false);

        return new ProductsInBasketAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProductsInBasketAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvProdName.setText(basket.getProducts().get(position).getName());
    }

    public void setClickListener(ProductsInBasketAdapter.ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return basket.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvProdName, tvTotalPrice, tvPriceXHowMany;

        ViewHolder(View itemView){
            super(itemView);

            tvProdName = itemView.findViewById(R.id.tvProductNameIB);
            tvTotalPrice = itemView.findViewById(R.id.tvPriceTotalIB);
            tvPriceXHowMany = itemView.findViewById(R.id.tvPriceUnitXNumb);

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
