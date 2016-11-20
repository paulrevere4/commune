package xyz.communeapp.commune;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Rabi on 11/19/16.
 */

public class RemoveUserDialog extends DialogFragment {
    // Use this instance of the interface to deliver action events
    RemoveNoticeDialogListener mListener;

    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it. */

    public interface RemoveNoticeDialogListener {
        public void onRemoveDialogPositiveClick(DialogFragment dialog);
    }

    // Override the Fragment.onAttach() method to instantiate the RemoveNoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the RemoveNoticeDialogListener so we can send events to the host
            mListener = (RemoveNoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement RemoveNoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.remove_user_from_group_dialog, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onRemoveDialogPositiveClick(RemoveUserDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RemoveUserDialog.this.getDialog().cancel();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
