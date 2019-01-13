package com.example.dbernst1.graciousgratitudes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

public class EditBackground extends AppCompatActivity {

    public static final String LAYOUT = "LAYOUT";
    public static final String BACKGROUND_IMAGE_LAND = "BACKGROUND_IMAGE_LAND";
    public static final String BACKGROUND_IMAGE_PORT = "BACKGROUND_IMAGE_PORT";
    public static final String SELECTED_P_CURRENT_BACKGROUND = "SELECTED_P_CURRENT_BACKGROUND";
    public static final String SELECTED_L_CURRENT_BACKGROUND = "SELECTED_L_CURRENT_BACKGROUND";

    private int mOrientation;
    private int [] mBackgrounds;
    private int mSelectedBackground;
    private int mCurrentBackground; //the current chosen one from the previous time the user entered this screen
                                    // (the one that is displayed in main activity if the orientation is the same)
    private BackGroundAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_background);
        setUpToolbar();

        //set orientation of the background
        getIncomingData();

        //make array of images according to desired layout
        if(mOrientation==R.id.portrait)
        {
            mBackgrounds= getPBackgrounds();
        }
        else
        {
            mBackgrounds = getLBackgrounds();
        }


        GridView gridView = findViewById(R.id.gridview);
        mAdapter = new BackGroundAdapter(this, mBackgrounds, mCurrentBackground);
        gridView.setAdapter(mAdapter);



        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedBackground = mAdapter.getSelectedBackground();
              finish();
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed(); //make the back arrow act like the back button on the android screen
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getIncomingData()
    {
        Intent intent = getIntent();
        mOrientation = intent.getIntExtra(LAYOUT, R.id.portrait);

        //based on the orientation, get the correct background to be selected by default
       if(mOrientation==R.id.landscape)
       {
           mCurrentBackground = intent.getIntExtra(SELECTED_L_CURRENT_BACKGROUND, R.drawable.ltexturedpinkpetal);
       }else
       {
           mCurrentBackground = intent.getIntExtra(SELECTED_P_CURRENT_BACKGROUND, R.drawable.ptexturedpinkpetal);
       }
    }

    @Override
    public void finish()
    {
        //only set the result_ok if the background was selected
        if(mSelectedBackground != 0) {
            Intent intentResults = new Intent();
            if (mOrientation == R.id.landscape) {
                intentResults.putExtra(BACKGROUND_IMAGE_LAND, mSelectedBackground);
            } else {
                intentResults.putExtra(BACKGROUND_IMAGE_PORT, mSelectedBackground);
            }
            setResult(RESULT_OK, intentResults);
        }
        super.finish();

    }

    private int[] getPBackgrounds()
    {
        int [] pbackgrounds = {
                R.drawable.pblueflowerframe,
                R.drawable.pbluepeacock,
                R.drawable.ptexturedpinkpetal,
                R.drawable.pgoldtexture,
                R.drawable.pflowerframe,
                R.drawable.pbrownflower,
                R.drawable.pblueflowers
        };
        return pbackgrounds;
    }

    private int[] getLBackgrounds()
    {
        int [] lbackgrounds = {
                R.drawable.lblueflowerframe,
                R.drawable.lbluepeacock,
                R.drawable.ltexturedpinkpetal,
                R.drawable.lgoldtexture,
                R.drawable.lflowerframe,
                R.drawable.lbrownflower,
                R.drawable.lblueflowers
        };
        return lbackgrounds;
    }

}
