package com.ttu.spm.appsum.layoutSelector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ttu.spm.appsum.R;

/**
 * Created by Manohar on 10/5/2015.
 * Class to implement the Food selection module
 */
public class FoodLayout extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_layout);
    }
}