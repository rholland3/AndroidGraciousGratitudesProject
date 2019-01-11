package com.example.dbernst1.graciousgratitudes;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BackGroundAdapter extends BaseAdapter
{
     private final RadioGroup mRadioGroup;
     private Context mContext;
     private int [] mImages;
     private LayoutInflater mInflater;
     private int mSelectedPosition;
     private RadioButton mSelectedRadioButton;
     private int mCurrentBackground; //the one that was selected from the last time...

     public BackGroundAdapter(Context c, int [] images, int currentBackground)
     {
         mSelectedPosition=-1;
         mContext=c;
         mImages=images;
         mInflater=(LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
         mRadioGroup = new RadioGroup(mContext);
         mCurrentBackground = currentBackground;
     }

    @Override
    public int getCount()
    {
        return mImages.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

         return mImages[position];
    }


    /**
     * method adapted from:
     * https://stackoverflow.com/questions/12781830/radio-group-implementation-on-grid-view-in-android
     *
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int POSITION = position;
         View view = convertView;
         Holder holder;
         if(view==null)
         {
             view = mInflater.inflate(R.layout.background_image, null);
             holder = new Holder();
             holder.image = (ImageView)view.findViewById(R.id.current_image);
             holder.radioButton = (RadioButton)view.findViewById(R.id.current_radio);
             view.setTag(holder);
         }
         else holder = (Holder)view.getTag();

         //display the background images
        holder.image.setImageResource(mImages[POSITION]);

            //if this instance of the background_image in the gridview is the one that was selected before
        //i.e it equals the mCurrentBackground we want to set it to checked

            if(mImages[POSITION]==mCurrentBackground)
            {
                mRadioGroup.check(holder.radioButton.getId());
               // holder.radioButton.setChecked(true);
                mSelectedPosition = POSITION;
                mSelectedRadioButton = holder.radioButton;
            }


            //on click situation for the radio buttons
        holder.radioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ((POSITION != mSelectedPosition && mSelectedRadioButton != null)) {
                    mSelectedRadioButton.setChecked(false);
                }

                mSelectedPosition = POSITION;
                mSelectedRadioButton = (RadioButton) v;
            }
        });

        if (mSelectedPosition != POSITION) {
            holder.radioButton.setChecked(false);
        } else {
            holder.radioButton.setChecked(true);
            if (mSelectedRadioButton != null && holder.radioButton != mSelectedRadioButton) {
               mSelectedRadioButton = holder.radioButton;
            }
        }
         return view;
    }

    public int getSelectedBackground()
    {
        return mImages[mSelectedPosition];
    }

    private class Holder {

        private ImageView image;
        private RadioButton radioButton;
    }
}
