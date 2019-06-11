package com.example.jowisz;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jowisz.Data.Api;
import com.example.jowisz.Data.RequestHandler;
import com.example.jowisz.Model.Category;
import com.example.jowisz.Model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChooseCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChooseCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCategoryFragment extends Fragment {

    private ArrayList<Category> mCategories;

    CategoryArrayAdapter adapter;
    private OnFragmentInteractionListener mListener;
    private ListView listView;

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    public ChooseCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment ChooseCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseCategoryFragment newInstance() {
        ChooseCategoryFragment fragment = new ChooseCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_choose_category, container, false);

        mCategories = new ArrayList<>();

        listView = rootView.findViewById(R.id.lvCategoryList);

        readCategories();

        adapter = new CategoryArrayAdapter(getContext(), mCategories);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int categoryID = mCategories.get(position).getCategoryID();
                Bundle bundle = new Bundle();
                bundle.putInt("categoryID", categoryID);
                bundle.putString("categoryName", mCategories.get(position).getCategoryName());

                ProductsFragment productsFragment = new ProductsFragment();
                productsFragment.setArguments(bundle);

                MainActivity mainActivity = (MainActivity) getActivity();

                FragmentManager fragmentManager = mainActivity.fragmentManager;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, productsFragment);
                mainActivity.categoryChosen = true;
                mainActivity.currentFragment = productsFragment;
                fragmentTransaction.commit();
            }
        });
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
                    JSONArray jarray = object.getJSONArray("kategorie");
                    Log.d("aaa", "arr0: "+jarray.get(0).toString());
                    refreshCategoryList(object.getJSONArray("kategorie"));
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

    private void readCategories() {
        ChooseCategoryFragment.PerformNetworkRequest request = new ChooseCategoryFragment.
                PerformNetworkRequest(Api.URL_READ_KATEGORIE, null, CODE_GET_REQUEST);
        request.execute();
        Log.d("aaa", "W read, size: "+ mCategories.size());
    }

    private void refreshCategoryList(JSONArray prod) throws JSONException {
        Log.d("aaa", "Poczatek refresh, size: "+ mCategories.size());
        //clearing previous heroes
        mCategories.clear();
        System.out.println(mCategories.toString());
        //traversing through all the items in the json array
        //the json we got from the response
        for (int i = 0; i < prod.length(); i++) {
            //getting each product object
            JSONObject obj = prod.getJSONObject(i);

            //adding the product to the list
            mCategories.add(new Category(
                    obj.getInt("IdTyp"),
                    obj.getString("NazwaTyp")
            ));
        }
        System.out.println(mCategories.toString());
        adapter = new CategoryArrayAdapter(getContext(), mCategories);
        listView.setAdapter(adapter);
        Log.d("aaa", "koniec refresh, size: "+mCategories.size());
    }
}
