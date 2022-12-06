package edu.uga.cs.finalshoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PurchasedRecyclerAdapter extends RecyclerView.Adapter<PurchasedRecyclerAdapter.ItemHolder> {

    private List<Item> itemList;
    private Context context;

    public PurchasedRecyclerAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView username;

        public ItemHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.price);
            username = itemView.findViewById(R.id.username);
        }
    } // ItemHolder

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.purchased_item, parent, false );
        return new ItemHolder( view );
    }

    // This method fills in the values of the Views to show a item
    @Override
    public void onBindViewHolder( ItemHolder holder, int position ) {
        Item item = itemList.get( position );

        String key = item.getKey();
        String name = item.getName();
        double price = item.getPrice();

        holder.name.setText( item.getName());
        holder.price.setText( String.valueOf(item.getPrice() ));
        holder.username.setText(item.getUser());


        // We can attach an OnClickListener to the itemView of the holder;
        // itemView is a public field in the Holder class.
        // It will be called when the user taps/clicks on the whole item, i.e., one of
        // the items shown.
        // This will indicate that the user wishes to edit (modify or delete) this item.
        // We create and show an EditItemDialogFragment.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditItemDialogFragment editJobFragment =
                        EditItemDialogFragment.newInstance( holder.getAdapterPosition(), key, name, price);
                editJobFragment.show( ((AppCompatActivity)context).getSupportFragmentManager(), null);
            }
        });
    } // onBindViewHolder

    @Override
    public int getItemCount() {
        return itemList.size();
    }


} // ItemRecyclerAdapter
