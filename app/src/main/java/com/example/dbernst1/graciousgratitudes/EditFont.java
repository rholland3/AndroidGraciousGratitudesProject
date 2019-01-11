package com.example.dbernst1.graciousgratitudes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class EditFont extends AppCompatActivity {

    public static final String FONT = "FONT";
    public static final String SELECTED_CURRENT_FONT = "SELECTED_CURRENT_FONT";
    private FontAdapter mAdapter;
    private int mSelectedFont; //is the one the user chooses now
    private final String mTEXT = "Thank You!";
    private int [] mFonts;
    private int mCurrentFont;  //the previously selected font will be default selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_font);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getIncomingData();

        //fill array
        mFonts=getFonts();

        GridView gridView = (GridView)findViewById(R.id.gridview);
        mAdapter = new FontAdapter(this, mFonts, mTEXT, mCurrentFont);
        gridView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedFont = mAdapter.getSelectedFont();
                finish();
            }
        });
    }

    private int[] getFonts()
    {
        int [] fonts = {
                R.font.andina_free,
                R.font.bloomsburg,
                R.font.hinted_elaine_sans_regular,
                R.font.kimberly,
                R.font.libre_baskerville_regular,
                R.font.martine_bold,
                R.font.prata_regular
        };
        return fonts;
    }

    private void getIncomingData()
    {
        Intent intent = getIntent();
        mCurrentFont = intent.getIntExtra(SELECTED_CURRENT_FONT, R.font.kimberly);
    }

    @Override
    public void finish()
    {
        Intent intentResults = new Intent();
        intentResults.putExtra(FONT, mSelectedFont);
        setResult(RESULT_OK, intentResults);
        super.finish();
    }
}
