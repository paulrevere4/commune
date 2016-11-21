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
 * Created by Rabi on 11/21/16.
 */

public class ResourceListCustomAdapter extends ArrayAdapter<String> {

    public ResourceListCustomAdapter(Context context, ArrayList<String> resource_names) {
        super(context, R.layout.resource_list_row, resource_names);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.resource_list_row, parent, false);

        String value = getItem(position);

        String[] values = value.split("/");

        String name = values[0];
        String details = values[1];

        TextView nameTextView = (TextView) customView.findViewById(R.id.resource_list_textView1);
        TextView detailsTextView = (TextView) customView.findViewById(R.id.resource_list_textView2);

        nameTextView.setText("Name: "+name);
        detailsTextView.setText("Details: "+details);

        return customView;
    }
}
