package com.example.jowisz;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.jowisz.Model.Basket;

public class MainActivity extends AppCompatActivity implements ProductsFragment.OnFragmentInteractionListener,
    ProfileFragment.OnFragmentInteractionListener, BasketFragment.OnFragmentInteractionListener,
        ChooseCategoryFragment.OnFragmentInteractionListener {


    Fragment currentFragment;
    private ProductsFragment productsFragment;
    private ProfileFragment profileFragment;
    private BasketFragment basketFragment;
    private ChooseCategoryFragment chooseCategoryFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Basket basket;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if(profileFragment == null) {
                        profileFragment = new ProfileFragment();
                        fragmentTransaction.add(R.id.container, profileFragment);
//                        fragmentTransaction.commit();
                    }
                    if(currentFragment != null && currentFragment != profileFragment){
                        fragmentTransaction.detach(currentFragment);
                    }
                    currentFragment = profileFragment;
                    fragmentTransaction.attach(profileFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_goods:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if(productsFragment == null) {
                        productsFragment = new ProductsFragment();
                        fragmentTransaction.add(R.id.container, productsFragment);
//                        fragmentTransaction.commit();
                    }
                    if(currentFragment != null && currentFragment != productsFragment){
                        fragmentTransaction.detach(currentFragment);
                    }
                    currentFragment = productsFragment;
                    fragmentTransaction.attach(productsFragment);
                    fragmentTransaction.commit();

//                    fragmentTransaction = fragmentManager.beginTransaction();
//                    if(chooseCategoryFragment == null) {
//                        chooseCategoryFragment = new ChooseCategoryFragment();
//                        fragmentTransaction.add(R.id.container, chooseCategoryFragment);
////                        fragmentTransaction.commit();
//                    }
//                    if(currentFragment != null && currentFragment != chooseCategoryFragment){
//                        fragmentTransaction.detach(currentFragment);
//                    }
//                    currentFragment = chooseCategoryFragment;
//                    fragmentTransaction.attach(chooseCategoryFragment);
//                    fragmentTransaction.commit();
                    return true;

                case R.id.navigation_basket:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if(basketFragment == null) {
                        basketFragment = new BasketFragment();
                        fragmentTransaction.add(R.id.container, basketFragment);
//                        fragmentTransaction.commit();
                    }
                    if(currentFragment != null && currentFragment != basketFragment){
                        fragmentTransaction.detach(currentFragment);
                    }
                    currentFragment = basketFragment;
                    fragmentTransaction.attach(basketFragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        basket = new Basket();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
