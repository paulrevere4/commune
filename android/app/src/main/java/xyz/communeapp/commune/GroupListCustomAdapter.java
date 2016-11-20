package xyz.communeapp.commune;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rabi on 11/18/16.
 */

class GroupListCustomAdapter extends ArrayAdapter<String> {

    GroupListCustomAdapter(Context context, ArrayList<String> groups){
        super(context, R.layout.group_list_row, groups);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.group_list_row, parent, false);

        String os = getItem(position);
        TextView textView1 = (TextView) customView.findViewById(R.id.groups_list_textView1);
        textView1.setText(os);

        return customView;
    }
}
