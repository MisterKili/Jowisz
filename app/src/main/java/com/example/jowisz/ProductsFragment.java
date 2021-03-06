package com.example.jowisz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import com.example.jowisz.Data.Api;
import com.example.jowisz.Data.RequestHandler;
import com.example.jowisz.Model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProductsFragment extends Fragment implements ProductsListAdapter.ItemClickListener{

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private List<Product> mProducts;

    private String categoryName;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerViewProducts;
    private ProductsListAdapter adapter;

    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {;
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);

        mProducts = new ArrayList<>();

        recyclerViewProducts = (RecyclerView) rootView.findViewById(R.id.rvProducts);
//        recyclerViewProducts.setHasFixedSize(true);
//        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getContext()));

//        mProducts.add(new Product(1, "Mysz Tracer", "swietna myszka, polecam",
//                25.0, "", 20, "Tracer", "Myszki"));
//        mProducts.add(new Product(1, "Klawiatura Tracer", "swietna myszka, polecam",
//                25.0, "", 20, "Tracer", "Myszki"));
//        mProducts.add(new Product(1, "jbasdhs Tracer", "swietna myszka, polecam",
//                25.0, "", 20, "Tracer", "Myszki"));
//        mProducts.add(new Product(1, "fsdfds Tracer", "swietna myszka, polecam",
//                25.0, "", 20, "Tracer", "Myszki"));
//        mProducts.add(new Product(1, "Mysfdsfadz Tracer", "swietna myszka, polecam",
//                25.0, "", 20, "Tracer", "Myszki"));
//        mProducts.add(new Product(1, "Klawiatura Tracer", "swietna myszka, polecam",
//                25.0, "", 20, "Tracer", "Myszki"));


//        readProducts();

        if(getArguments() != null) {
            int categoryID = getArguments().getInt("categoryID");
            readProductsFromCategory(categoryID);
        }else{
            readProducts();
        }


        SearchView searchView = rootView.findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
        recyclerViewProducts.invalidate();

        return rootView;
    }

    public void onItemClick(View view, int position){
//        Toast.makeText(getContext(), "Kliknieto"+position, Toast.LENGTH_SHORT).show();

//        Intent intent= new Intent(getContext(),ProductActivity.class);
        Product product =  mProducts.get(position);

        FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        productDetailsFragment.setArguments(bundle);
        ft.replace(android.R.id.content, productDetailsFragment);
        ft.addToBackStack(null);
        ft.commit();

//        intent.putExtra("product", product);
//        startActivity(intent);
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

    //inner class to perform network request extending an AsyncTask
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("aaa","W onPostExecute, s= "+s);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Log.d("aaa", "objectJSON jest nie jest errorem");
                    //refreshing the product list after every operation
                    //so we get an updated list
                    JSONArray jarray = object.getJSONArray("sprzet");
                    Log.d("aaa", "arr0: "+jarray.get(0).toString());
                    refreshProductList(object.getJSONArray("sprzet"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    private void readProducts() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_SPRZET, null,
                CODE_GET_REQUEST);
        request.execute();
        Log.d("aaa", "W read, size: "+mProducts.size());
    }

    private void readProductsFromCategory(int catID) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_SPRZET_Z_KATEGORII + catID, null, CODE_GET_REQUEST);
        request.execute();
        Log.d("aaa", "W read, size: "+mProducts.size());
    }

    private void refreshProductList(JSONArray prod) throws JSONException {
        Log.d("aaa", "Poczatek refresh, size: "+mProducts.size());
        //clearing previous heroes
        mProducts.clear();
        System.out.println(mProducts.toString());
        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < prod.length(); i++) {
            //getting each product object
            JSONObject obj = prod.getJSONObject(i);

            //adding the product to the list
            mProducts.add(new Product(
                    obj.getInt("idSpr"),
                    obj.getString("nazwa"),
                    obj.getString("opis"),
                    obj.getDouble("cena"),
                    obj.getString("zdjecie"),
                    obj.getInt("liczbaSztuk"),
                    obj.getString("producent"),
                    obj.getString("typ")
            ));
        }
        System.out.println(mProducts.toString());
        //creating the adapter and setting it to the listview
        recyclerViewProducts.setHasFixedSize(true);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewProducts.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        adapter = new ProductsListAdapter(getContext(), mProducts);
        adapter.setClickListener(this);
        recyclerViewProducts.setAdapter(adapter);
        recyclerViewProducts.invalidate();
        Log.d("aaa", "koniec refresh, size: "+mProducts.size());
    }
}
