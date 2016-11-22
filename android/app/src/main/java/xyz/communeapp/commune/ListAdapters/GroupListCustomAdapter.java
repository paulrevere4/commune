package xyz.communeapp.commune.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.communeapp.commune.R;

/**
 * A custom list adapter to show items on a list view
 */
public class GroupListCustomAdapter extends ArrayAdapter<String> {

    /**
     * Constructor for a list adapter
     *
     * @param context backgroun information
     * @param groups list of group names
     */
    public GroupListCustomAdapter(Context context, ArrayList<String> groups) {
        // Set the layout for the list item
        super(context, R.layout.group_list_row, groups);
    }

    /**
     * Creates an returns a list item view to be displayed on a list view
     *
     * @param position    position of the item in the list view
     * @param convertView view
     * @param parent      parent view
     * @return list item view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.group_list_row, parent, false);
        // Get list element at position
        String os = getItem(position);
        // Get UI elements and set them to list adapter value
        TextView textView1 = (TextView) customView.findViewById(R.id.groups_list_textView1);
        textView1.setText(os);

        return customView;
    }
}
