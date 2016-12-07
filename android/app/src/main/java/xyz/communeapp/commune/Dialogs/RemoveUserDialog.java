package xyz.communeapp.commune.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import xyz.communeapp.commune.R;

/**
 * Creates and returns a dialog to be displayed when user wants to remove a user from a group
 */
public class RemoveUserDialog extends DialogFragment {
    RemoveNoticeDialogListener mListener;

    /**
     * Callback function to attach a listener to the activity
     *
     * @param activity activity the listener is attached to
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the RemoveNoticeDialogListener so we can send events to the host
            mListener = (RemoveNoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement " +
                    "RemoveNoticeDialogListener");
        }
    }

    /**
     * Callback function triggered at dialog creation
     *
     * @param savedInstanceState background information
     * @return a dialog to be displayed to the user
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        // Get the UI elements from the UI that was inflated by the dialog
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
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                RemoveUserDialog.this.getDialog().cancel();
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Interface that the activity calling the dialog needs to implement to catch user actions
     */
    public interface RemoveNoticeDialogListener {
        void onRemoveDialogPositiveClick(DialogFragment dialog);
    }
}
