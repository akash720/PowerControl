package com.example.azura.powercontrol;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Component> {

    private char[] charSet = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k','l','m','n','o','p','q','r','s','t'};

    public CustomAdapter(Activity context, ArrayList<Component> components) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, components);

    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        listItemView.setLayerType(View.LAYER_TYPE_HARDWARE,null);

        ImageView mLightImageView = null;
        AnimationDrawable animation = null;
        // Get the {@link Earthquake} object located at this position in the list
        final Component currentComponent = getItem(position);

        int type;

        TextView typeTextView = listItemView.findViewById(R.id.component_type);

        type = currentComponent.getType();

        if(type == 0)
        {
            ImageView mFanImageView = listItemView.findViewById(R.id.component_image);
            mFanImageView.setImageResource(R.drawable.fan);
            animation = (AnimationDrawable) mFanImageView.getDrawable();
            typeTextView.setText(R.string.fan);
        }
        else{
            mLightImageView = listItemView.findViewById(R.id.component_image);
            boolean lightState = currentComponent.getState();
            if(lightState == Boolean.TRUE){
//                Drawable myDrawable = mContext.getResources().getDrawable(R.drawable.lighton);
                mLightImageView.setImageResource(R.drawable.lighton);
            }
            else{
                mLightImageView.setImageResource(R.drawable.lightoff);
            }
            typeTextView.setText(R.string.light);
        }

        TextView locationTextView = listItemView.findViewById(R.id.component_location);
        String locationString = currentComponent.getLocation();
        locationTextView.setText(locationString);

        final ToggleButton mToggleButton = listItemView.findViewById(R.id.toggle);
        boolean state = currentComponent.getState();
        if(state == Boolean.TRUE){
            mToggleButton.setChecked(Boolean.TRUE);
            if (animation != null) {
                animation.start();
            }
        }
        else{
            mToggleButton.setChecked(Boolean.FALSE);
        }

        final AnimationDrawable finalAnimation = animation;

        final ImageView finalMLightImageView = mLightImageView;
        mToggleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mToggleButton.isChecked()) {

                    // TODO: Set your SIM module's number in phoneNo variable

                    String phoneNo = "";
                    String message = "#" + charSet[position] + "1";

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, message, null, null);
                        Toast.makeText(getContext(), "SMS sent.",
                                Toast.LENGTH_LONG).show();
                        if (finalAnimation != null) {
                            finalAnimation.start();
                        }
                        if (finalMLightImageView != null) {
                            finalMLightImageView.setImageResource(R.drawable.lighton);
                        }

                    } catch (Exception e) {
                        Toast.makeText(getContext(),
                                "SMS failed, please check permissions and try again.",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    // TODO: Set your SIM module's number in phoneNo variable
                    String phoneNo = "";
                    String message = "#" + charSet[position] + "0";

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, message, null, null);
                        Toast.makeText(getContext(), "SMS sent.",
                                Toast.LENGTH_LONG).show();
                        if (finalAnimation != null) {
                            finalAnimation.stop();
                        }
                        if (finalMLightImageView != null) {
                            finalMLightImageView.setImageResource(R.drawable.lightoff);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(),
                                "SMS failed, please check permissions and try again.",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        });

        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                }
                else {
                    if (finalAnimation != null) {
                        finalAnimation.stop();
                    }
                    if (finalMLightImageView != null) {
                        finalMLightImageView.setImageResource(R.drawable.lightoff);
                    }
                }
            }
        });
        return listItemView;
    }
}
