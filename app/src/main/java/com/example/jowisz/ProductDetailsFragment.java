package com.example.jowisz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jowisz.Model.Product;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {

    private TextView name;
    private TextView description;
    private TextView price;
    private TextView amount;
    TextView howManyNumber;
    private Button addToBusket;
    private Button bPlus;
    private Button bMinus;
    Product product;

    private OnFragmentInteractionListener mListener;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Intent intent = getActivity().getIntent();
//        final Product product = (Product) intent.getSerializableExtra("product");

        View rootView = inflater.inflate(R.layout.fragment_product_details, container, false);

        Bundle bundle = getArguments();
        product = (Product) bundle.getSerializable("product");

        name = rootView.findViewById(R.id.nameProduct);
        description = rootView.findViewById(R.id.descriptionProduct);
        price = rootView.findViewById(R.id.priceProduct);
        amount = rootView.findViewById(R.id.amountProduct);
        howManyNumber = rootView.findViewById(R.id.howManyNumb);
        addToBusket = rootView.findViewById(R.id.addProduct);
        bPlus = rootView.findViewById(R.id.buttonPlus);
        bMinus = rootView.findViewById(R.id.buttonMinus);

        name.setText(product.getName());
        description.setText(product.getDescription());
        price.setText("Cena: "+Double.toString(product.getPriceUnit()));
        amount.setText("Dostępnych: " + product.getAvaibility());

        if(product.getAvaibility() > 0){
//            amount.setText("Dostępny");
            amount.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            amount.setText("Niedostępny");
            amount.setTextColor(getResources().getColor(R.color.red));
        }

        bPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.increaseHowMany();
                howManyNumber.setText(Integer.toString(product.getHowMany()));
            }
        });

        bMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.decreaseHowMany();
                howManyNumber.setText(Integer.toString(product.getHowMany()));
            }
        });

        addToBusket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(product.getAvaibility()>0){

                    if(product.getHowMany() != 0) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.basket.putProduct(product);
                        Toast.makeText(getContext(), "Dodano do koszyka", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Towaru nie ma w magazynie", Toast.LENGTH_SHORT).show();
                    amount.setText("Niedostępny");
                    amount.setTextColor(getResources().getColor(R.color.red));
                }

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
