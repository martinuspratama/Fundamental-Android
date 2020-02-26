package com.azhara.sub1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        MainFragement mMainFragment = new MainFragement();

        Fragment fragment = mFragmentManager.findFragmentByTag(MainFragement.class.getSimpleName());
        if (!(fragment instanceof MainFragement)){
            mFragmentTransaction.add(R.id.container, mMainFragment, MainFragement.class.getSimpleName());
            Log.d("List View", "Fragment Name : " + MainFragement.class.getSimpleName());
            mFragmentTransaction.commit();
        }

    }
}
