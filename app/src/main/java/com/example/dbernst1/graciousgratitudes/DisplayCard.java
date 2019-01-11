package com.example.dbernst1.graciousgratitudes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class DisplayCard extends AppCompatActivity {


    private static final String TAG = "DISPLAY_CARD_ERROR";
    private ImageView mImageView;
    private TextView mTextView;
    private int mFont;
    private int mBackground;
    private String mText;
    private FrameLayout mCard;
    private Bitmap mSavedCard;

    public static final String SELECTED_BACKGROUND = "SELECTED_BACKGROUND";
    public static final String SELECTED_CURRENT_FONT = "SELECTED_CURRENT_FONT";
    public static final String SELECTED_CARD_TEXT = "SELECTED_CARD_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageView = findViewById(R.id.final_card_background);
        mTextView = findViewById(R.id.final_card_text);

        getIncomingData();

        mImageView.setImageResource(mBackground);
        mTextView.setText(mText);
        Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), mFont);
        mTextView.setTypeface(typeface);

        mCard = findViewById(R.id.final_card);


    }

    private void getIncomingData() {

        Intent intent = getIntent();
        mBackground = intent.getIntExtra(SELECTED_BACKGROUND, R.drawable.ltexturedpinkpetal);
        mFont = intent.getIntExtra(SELECTED_CURRENT_FONT, R.font.kimberly);
        mText = intent.getStringExtra(SELECTED_CARD_TEXT);
    }

    public void saveCard(View view) {
        //save the view of this card to the photos gallery
        mSavedCard = takeScreenshot(mCard);

        if(mSavedCard!=null)
        {
            String storedImage = saveCardToGallery(mSavedCard);

            if(storedImage!=null)
            {
                Toast.makeText(getApplicationContext(), "Card saved successfully, Check gallery ", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(getApplicationContext(), "Card was not saved, please check that permissions are enabled for storage.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public String saveCardToGallery(Bitmap card)
    {
        String storedImage = MediaStore.Images.Media.insertImage(getContentResolver(), card,
                UUID.randomUUID().toString()+"png", "thank you card");
        return storedImage;
    }

    public void saveAndShareCard(View view) {
        //save the card
        mSavedCard = takeScreenshot(mCard);
        if(mSavedCard!=null)
        {
            //first need to save the image
            String storedImage = saveCardToGallery(mSavedCard);

            if(storedImage!=null)
            {
                //if image saved properly, allow user to share it using his other apps
                Uri uri = Uri.parse(storedImage); //get the uri so we can share it
                shareImageUri(uri);
            }
        }
    }

    /**
     * credit for this method:
     * https://stackoverflow.com/questions/33222918/sharing-bitmap-via-android-intent
     * Shares the PNG image from Uri.
     * @param uri Uri of image to share.
     */
    private void shareImageUri(Uri uri){
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/png");
            startActivity(intent);
        }


   public Bitmap takeScreenshot(View v)
    {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return b;
    }

}
