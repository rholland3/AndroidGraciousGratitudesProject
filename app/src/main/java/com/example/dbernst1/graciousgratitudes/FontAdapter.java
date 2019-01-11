package com.example.dbernst1.graciousgratitudes;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

class FontAdapter extends BaseAdapter
{
    private final RadioGroup mRadioGroup;
    private Context mContext;
    private int [] mFonts;
    private LayoutInflater mInflater;
    private int mSelectedPosition;
    private RadioButton mSelectedRadioButton;
    private String mText;
    private int mCurrentFont; //was chosen last time

    public FontAdapter(Context c, int [] fonts, String text, int currentFont)
    {
        mSelectedPosition=-1;
        mContext=c;
        mFonts=fonts;
        mInflater=(LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        mRadioGroup = new RadioGroup(mContext);
        mText = text;
        mCurrentFont = currentFont;
    }

    @Override
    public int getCount()
    {
        return mFonts.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

        return mFonts[position];
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
            view = mInflater.inflate(R.layout.font, null);
            holder = new Holder();
            holder.textView = (TextView)view.findViewById(R.id.current_font);
            holder.radioButton = (RadioButton)view.findViewById(R.id.current_font_radio);
            view.setTag(holder);
        }
        else holder = (Holder)view.getTag();
        holder.textView.setText(mText);
        Typeface typeface = ResourcesCompat.getFont(mContext, mFonts[POSITION]);
        holder.textView.setTypeface(typeface);

        if(mFonts[POSITION]== mCurrentFont)
        {
            mRadioGroup.check(holder.radioButton.getId());
           //holder.radioButton.setChecked(true);
            mSelectedPosition = POSITION;
            mSelectedRadioButton = holder.radioButton;
        }

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

        if (mSelectedPosition != position) {
            holder.radioButton.setChecked(false);
        } else {
            holder.radioButton.setChecked(true);
            if (mSelectedRadioButton != null && holder.radioButton != mSelectedRadioButton) {
                mSelectedRadioButton = holder.radioButton;
            }
        }
        return view;
    }

    public int getSelectedFont()
    {
        return mFonts[mSelectedPosition];
    }

    private class Holder {

        private TextView textView;
        private RadioButton radioButton;
    }
}
