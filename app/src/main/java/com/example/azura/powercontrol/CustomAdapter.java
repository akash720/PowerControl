package com.example.azura.powercontrol;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class CustomAdapter extends ArrayAdapter<Component> {

    private Context mContext = getContext();

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
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Component currentComponent = getItem(position);

        int type;

        ImageView mImageView = listItemView.findViewById(R.id.component_image);
        TextView typeTextView = listItemView.findViewById(R.id.component_type);

        type = currentComponent.getType();
        if(type == 0)
        {
            Drawable myDrawable = mContext.getResources().getDrawable(R.drawable.fan);
            mImageView.setImageDrawable(myDrawable);
            typeTextView.setText(R.string.fan);
        }
        else{
            Drawable myDrawable = mContext.getResources().getDrawable(R.drawable.light);
            mImageView.setImageDrawable(myDrawable);
            typeTextView.setText(R.string.light);
        }

        TextView locationTextView = listItemView.findViewById(R.id.component_location);
        String locationString = currentComponent.getLocation();
        locationTextView.setText(locationString);

        ToggleButton mToggleButton = listItemView.findViewById(R.id.toggle);
        boolean state = currentComponent.getState();
        if(state == Boolean.TRUE){
            mToggleButton.setChecked(Boolean.TRUE);
        }
        else{
            mToggleButton.setChecked(Boolean.FALSE);
        }

        return listItemView;
    }
}
