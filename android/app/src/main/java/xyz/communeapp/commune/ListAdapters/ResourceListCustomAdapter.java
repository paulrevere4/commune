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
 * A custom list adapter to show resources in a list
 */
public class ResourceListCustomAdapter extends ArrayAdapter<String> {
    /**
     * Constructor for a list adapter
     *
     * @param context        background information
     * @param resource_names list of resource names
     */
    public ResourceListCustomAdapter(Context context, ArrayList<String> resource_names) {
        super(context, R.layout.resource_list_row, resource_names);
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
        View customView = inflater.inflate(R.layout.resource_list_row, parent, false);
        // Get list element at position
        String value = getItem(position);
        String[] values = value.split("/");
        String name = values[0];
        String details = values[1];

        // Get UI elements and set them to list adapter value
        TextView nameTextView = (TextView) customView.findViewById(R.id.resource_list_textView1);
        TextView detailsTextView = (TextView) customView.findViewById(R.id.resource_list_textView2);
        nameTextView.setText("Name: " + name);
        detailsTextView.setText("Details: " + details);

        return customView;
    }
}
