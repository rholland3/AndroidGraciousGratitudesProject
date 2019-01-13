package com.example.dbernst1.graciousgratitudes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditCardText extends AppCompatActivity {

    public static final String CARD_TEXT = "CARD_TEXT";
    public static final String SELECTED_CARD_TEXT = "SELECTED_CARD_TEXT";
    private String mCurrentCardText;
    private EditText mTextEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        setUpToolbar();

        mTextEditor= findViewById(R.id.edit_card_text);
        getIncomingData();
        mTextEditor.setText(mCurrentCardText);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mCurrentCardText= mTextEditor.getText().toString();
               finish();
            }
        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        Intent intent  = getIntent();
        mCurrentCardText =intent.getStringExtra(CARD_TEXT);
    }

    @Override
    public void finish()
    {
        if(mCurrentCardText != null) {
            Intent intentResults = new Intent();
            intentResults.putExtra(SELECTED_CARD_TEXT, mCurrentCardText);
            setResult(RESULT_OK, intentResults);
        }
        super.finish();
    }

}
