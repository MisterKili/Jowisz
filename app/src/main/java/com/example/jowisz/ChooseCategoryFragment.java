package com.example.jowisz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jowisz.Model.Category;
import com.example.jowisz.Model.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

    private OnFragmentInteractionListener mListener;
    private ListView listView;

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
        mCategories.add(new Category(2, "Klawiatury"));
        mCategories.add(new Category(1, "Laptopy"));
        mCategories.add(new Category(3, "Myszy"));

        listView = rootView.findViewById(R.id.lvCategoryList);

        CategoryArrayAdapter adapter = new CategoryArrayAdapter(getContext(), mCategories);
//        ArrayList<String> valuesArray = new ArrayList<>();
//        valuesArray.add("Klawiatury");
//        valuesArray.add("Laptopy");
//        valuesArray.add("Myszy");

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, valuesArray);

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
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, productsFragment);
                mainActivity.currentFragment = productsFragment;
                fragmentTransaction.commit();
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
