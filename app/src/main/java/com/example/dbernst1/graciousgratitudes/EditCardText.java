package com.example.dbernst1.graciousgratitudes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextEditor= findViewById(R.id.edit_card_text);
        getIncomingData();
        mTextEditor.setText(mCurrentCardText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mCurrentCardText= mTextEditor.getText().toString();
               finish();
            }
        });
    }

    private void getIncomingData()
    {
        Intent intent  = getIntent();
        mCurrentCardText =intent.getStringExtra(CARD_TEXT);
    }

    @Override
    public void finish()
    {
        Intent intentResults = new Intent();
        intentResults.putExtra(SELECTED_CARD_TEXT, mCurrentCardText);
        setResult(RESULT_OK, intentResults);
        super.finish();
    }

}
