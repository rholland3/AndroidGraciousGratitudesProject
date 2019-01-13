package com.example.dbernst1.graciousgratitudes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String LAYOUT = "LAYOUT";
    public static final String BACKGROUND_IMAGE_LAND = "BACKGROUND_IMAGE_LAND";
    public static final String BACKGROUND_IMAGE_PORT = "BACKGROUND_IMAGE_PORT";
    public static final String SELECTED_L_CURRENT_BACKGROUND = "SELECTED_L_CURRENT_BACKGROUND";
    public static final String SELECTED_P_CURRENT_BACKGROUND = "SELECTED_P_CURRENT_BACKGROUND";
    public static final String SELECTED_BACKGROUND = "SELECTED_BACKGROUND";
    public static final String SELECTED_CURRENT_FONT = "SELECTED_CURRENT_FONT";
    public static final String FONT = "FONT";
    public static final String SELECTED_CARD_TEXT = "SELECTED_CARD_TEXT";
    public static final String CARD_TEXT = "CARD_TEXT";
    public static final String SELECTED_CURRENT_ORIENTATION = "SELECTED_CURRENT_ORIENTATION";
    private final int REQUEST_CODE_BACKGROUND = 564;
    private final int REQUEST_CODE_FONT = 563;
    private final int REQUEST_CODE_TEXT = 562;

    private ImageView mBackground_image;
    private TextView mFont_view;
    private EditText mTextEditor;
    private RadioGroup mBackgroundLayout;

    private int mCurrentFont;
    private int mChosenBackground;
    private int mPCurrentBackground; //default portrait
    private int mLCurrentBackground;//default landscape
    private int mCurrentOrientation;
    private String mCurrentText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startNewCard();
        setUpFAB();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //save the current values across lifecycle

        outState.putInt(SELECTED_CURRENT_ORIENTATION, mCurrentOrientation);

        if(mChosenBackground == 0)
        {
            if(mCurrentOrientation==R.id.portrait)
                mChosenBackground=R.drawable.ptexturedpinkpetal;
            else
                mChosenBackground=R.drawable.ltexturedpinkpetal;
        }
        outState.putInt(SELECTED_BACKGROUND, mChosenBackground);
        outState.putInt(SELECTED_CURRENT_FONT, mCurrentFont);

        //get the text from the text editor in case the user changed it from the main page without going to full screen mode
        mCurrentText  = mTextEditor.getText().toString();
        outState.putString(SELECTED_CARD_TEXT, mCurrentText);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mChosenBackground= savedInstanceState.getInt(SELECTED_BACKGROUND);
        mCurrentFont = savedInstanceState.getInt(SELECTED_CURRENT_FONT);
        mCurrentText = savedInstanceState.getString(SELECTED_CARD_TEXT);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void setUpFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //here goes the code to call DisplayCardLand/Port depending on the orientation
                //that page displays the completed card and allows the user to save it
                launchCard();
            }
        });
    }

    private void startNewCard() {
        //set the current values - also only for the first time,
        // the rest of the times that this activity is called these variables will be set when the data comes back from the earlier activity
        //in the onActivityResults

        android.support.v7.preference.PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean defaultOrientationPortrait = sharedPreferences.getBoolean(Settings.KEY_PREF_ORIENTATION_SWITCH, false);

        mLCurrentBackground = R.drawable.ltexturedpinkpetal;
        mPCurrentBackground = R.drawable.ptexturedpinkpetal;
        mCurrentFont = R.font.kimberly;
        mCurrentOrientation = R.id.portrait; //default
        mCurrentText= getString(R.string.default_thank_you_card);

        mTextEditor = findViewById(R.id.step3_preview);
        mBackground_image=findViewById(R.id.step1_preview);
        mFont_view = findViewById(R.id.step2_preview);
        mBackgroundLayout = findViewById(R.id.background_image_layout);
       if(defaultOrientationPortrait)
       {
           mBackgroundLayout.check(R.id.portrait);
       }else
       {
           mBackgroundLayout.check(R.id.landscape);
       }

        //only sets the background the first time loads
        mBackground_image.setImageResource(mPCurrentBackground); //since default is portrait
        //only set font on the first on create
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), mCurrentFont);
        mFont_view.setTypeface(typeface);

        //set default text
        mTextEditor.setText(mCurrentText);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //check if resultCode is RESULT_OK -
        //no need for this since it will always be ok because of the default selected option
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_BACKGROUND:
                    //get the background image user chose
                    //place the default image if they chose nothing
                    if (mCurrentOrientation == R.id.landscape) {
                        mChosenBackground = data.getIntExtra(BACKGROUND_IMAGE_LAND, R.drawable.ltexturedpinkpetal);
                        mLCurrentBackground = mChosenBackground;
                    } else {
                        mChosenBackground = data.getIntExtra(BACKGROUND_IMAGE_PORT, R.drawable.ptexturedpinkpetal);
                        mPCurrentBackground = mChosenBackground;
                    }

                    mBackground_image.setImageResource(mChosenBackground);
                    break;
                case REQUEST_CODE_FONT:
                    mCurrentFont = data.getIntExtra(FONT, R.font.kimberly);
                    Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), mCurrentFont);
                    mFont_view.setTypeface(typeface);
                    break;
                case REQUEST_CODE_TEXT:
                    mCurrentText = data.getStringExtra(SELECTED_CARD_TEXT);
                    mTextEditor.setText(mCurrentText);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchBackground(View view)
    {
        //send in the selected orientation
        Intent intent = new Intent(this, EditBackground.class);
        intent.putExtra(LAYOUT, mCurrentOrientation);
       //send in the current selected background so it will be checked in the next activity
        intent.putExtra(SELECTED_P_CURRENT_BACKGROUND, mPCurrentBackground);
        intent.putExtra(SELECTED_L_CURRENT_BACKGROUND, mLCurrentBackground);
        startActivityForResult(intent, REQUEST_CODE_BACKGROUND);
    }

    public void launchFont(View view)
    {
        //send in the selected font so it will be checked in the next activity
        Intent intent = new Intent(this, EditFont.class);
        intent.putExtra(SELECTED_CURRENT_FONT, mCurrentFont);
        startActivityForResult(intent, REQUEST_CODE_FONT);
    }

    private void launchCard() {
        Intent intent;
        //send in the current background, text and font
        intent = new Intent(this, DisplayCard.class);
        if(mChosenBackground == 0)
        {
            if(mCurrentOrientation==R.id.portrait)
                mChosenBackground=R.drawable.ptexturedpinkpetal;
            else
                mChosenBackground=R.drawable.ltexturedpinkpetal;
        }
        intent.putExtra(SELECTED_BACKGROUND, mChosenBackground);
        intent.putExtra(SELECTED_CURRENT_FONT, mCurrentFont);

        //get the text from the text editor in case the user changed it from the main page without going to full screen mode
        mCurrentText  = mTextEditor.getText().toString();
        intent.putExtra(SELECTED_CARD_TEXT, mCurrentText);

        startActivity(intent);
    }

    public void launchText(View view)
    {
        Intent intent = new Intent(this, EditCardText.class);
        mCurrentText = mTextEditor.getText().toString();
        intent.putExtra(CARD_TEXT, mCurrentText);
        startActivityForResult(intent, REQUEST_CODE_TEXT);
    }

    public void showAbout(MenuItem item) {
        //show the user the 'about' message
       Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }


    //on click for the menu option to start a new card
    public void newCard(MenuItem item) {
        //call the start new card method to reset all the options to their default values
        startNewCard();
    }

    //on clicks for the Radio Buttons
    public void setLandscape(View view) {
        mCurrentOrientation = R.id.landscape;
    }

    public void setPortrait(View view) {
        mCurrentOrientation = R.id.portrait;
    }
}
