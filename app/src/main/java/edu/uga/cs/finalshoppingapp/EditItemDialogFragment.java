package edu.uga.cs.finalshoppingapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditItemDialogFragment extends DialogFragment {

    public static final int SAVE = 1;
    public static final int DELETE = 2;

    private EditText nameText;
    private EditText priceText;
    private Button shoppingButt;

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
        final View layout = inflater.inflate( R.layout.edit_item_dialog, getActivity().findViewById( R.id.root ) );

        nameText = layout.findViewById(R.id.editText1);
        priceText = layout.findViewById(R.id.editText2);

        shoppingButt = layout.findViewById(R.id.addShopping);
        shoppingButt.setEnabled(true);

        if (getActivity() instanceof BasketActivity) {
            shoppingButt.setText("Move back to \"all items\" list");
            shoppingButt.setOnClickListener(new MoveBackListener());
        } else {
            shoppingButt.setOnClickListener(new AddShopListener());
        }
        nameText.setText(itemName);
        priceText.setText(String.valueOf(price));

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity());
        builder.setView(layout);

        builder.setTitle("Edit Item");

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

            // get the Activity's listener to add the new item
            EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
            // add the new item
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

            // get the Activity's listener to add the new item
            EditItemDialogFragment.EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
            listener.updateItem( position, item, DELETE );
            // close the dialog
            dismiss();
        }
    } // deleteListener

    private class AddShopListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = nameText.getText().toString();
            double price = Double.parseDouble(priceText.getText().toString());
            Item item = new Item(name, price);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("basket");

            myRef.push().setValue( item )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Clear the EditTexts for next use.
                            nameText.setText("");
                            priceText.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            // do nothing
                        }
                    });

            item.setKey(key);
            // deleting the item from the FireBase database
            EditItemDialogFragment.EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
            listener.updateItem( position, item, DELETE );
            shoppingButt.setEnabled(false);
        }
    } // AddShopListener


    private class MoveBackListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = nameText.getText().toString();
            double price = Double.parseDouble(priceText.getText().toString());
            final Item item = new Item(name, price);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("items");

            myRef.push().setValue( item )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Clear the EditTexts for next use.
                            nameText.setText("");
                            priceText.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            // do nothing
                        }
                    });

            item.setKey(key);
            // deleting the item from the FireBase database
            EditItemDialogFragment.EditItemDialogListener listener = (EditItemDialogFragment.EditItemDialogListener) getActivity();
            listener.updateItem( position, item, DELETE );
            shoppingButt.setEnabled(false);
        }

    } //MoveBackListener


} // EditItemDialogFragment
