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

public class EditItemDialogFragment extends DialogFragment {

    public static final int SAVE = 1;
    public static final int DELETE = 2;

    private EditText nameText;
    private EditText priceText;

    int position;
    String key, itemName;
    double price;

    public interface EditItemDialogListener {
        void updateItem(int position, Item item, int action);
    }

    public static EditItemDialogFragment newInstance(int position, String key, String itemName, double price) {
        EditItemDialogFragment dialog = new EditItemDialogFragment();

        Bundle args = new Bundle();
        args.putString("key", key);
        args.putInt("position", position);
        args.putString("name", itemName);
        args.putString("price", String.valueOf(price));
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        key = getArguments().getString("key");
        position = getArguments().getInt("position");
        itemName = getArguments().getString("name");
        price = Double.parseDouble(getArguments().getString("price"));

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate( R.layout.add_item_dialog, getActivity().findViewById( R.id.root ) );

        nameText = layout.findViewById(R.id.editText1);
        priceText = layout.findViewById(R.id.editText2);

        nameText.setText(itemName);
        priceText.setText(String.valueOf(price));

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity());
        builder.setView(layout);

        builder.setTitle("Edit item");

        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });

        // The Save button handler
        builder.setPositiveButton( "SAVE", new SaveButtonClickListener() );

        // The Delete button handler
        builder.setNeutralButton( "DELETE", new DeleteButtonClickListener() );

        // Create the AlertDialog and show it
        return builder.create();

    } // onCreateDialog

    private class SaveButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String name = nameText.getText().toString();
            double price = Double.parseDouble(priceText.getText().toString());

            Item item = new Item(name, price);
            item.setKey( key );

            // get the Activity's listener to add the new job lead
            EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
            // add the new job lead
            listener.updateItem( position, item, SAVE );

            // close the dialog
            dismiss();
        }
    } // saveListener

    private class DeleteButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick( DialogInterface dialog, int which ) {

            Item item = new Item(itemName, price);
            item.setKey( key );

            // get the Activity's listener to add the new job lead
            EditItemDialogFragment.EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();            // add the new job lead
            listener.updateItem( position, item, DELETE );
            // close the dialog
            dismiss();
        }
    } // deleteListener


} // EditItemDialogFragment
