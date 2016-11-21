package xyz.communeapp.commune.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import xyz.communeapp.commune.R;

public class MarkIssueCompleteDialog extends DialogFragment {
    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it. */

    // Override the Fragment.onAttach() method to instantiate the RemoveNoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the RemoveNoticeDialogListener so we can send events to the host
            mListener = (MarkIssueCompleteDialog.NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement " +
                    "RemoveNoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.mark_issue_complete_dialog, null);

        final EditText dollarValue = (EditText) view.findViewById(R.id.dollar_value);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(MarkIssueCompleteDialog.this, dollarValue
                                .getText().toString());
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MarkIssueCompleteDialog.this.getDialog().cancel();
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String value);
    }
}
