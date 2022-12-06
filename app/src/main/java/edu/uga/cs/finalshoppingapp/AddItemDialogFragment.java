package edu.uga.cs.finalshoppingapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddItemDialogFragment extends DialogFragment {

    private EditText nameText;
    private EditText priceText;

    public interface AddItemDialogListener {
        void addItem(Item item);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.add_item_dialog,
                getActivity().findViewById(R.id.root));

        nameText = layout.findViewById(R.id.editText1);
        priceText = layout.findViewById(R.id.editText2);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);

        builder.setTitle("Add Item");

        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        // Provide the positive button listener
        builder.setPositiveButton( android.R.string.ok, new AddItemListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class AddItemListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // get the new item data from the user
            String name = nameText.getText().toString();
            Double price = Double.parseDouble(priceText.getText().toString());

            // create a new item object
            Item item = new Item(name, price);
            // get the Activity's listener to add the new item
            AddItemDialogListener listener = (AddItemDialogListener) getActivity();

            // add the new item
            listener.addItem( item );

            // close the dialog
            dismiss();
        }
    }



} // AddItemDialogFragment
