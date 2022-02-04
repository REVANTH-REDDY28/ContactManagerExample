package com.sunny.contactmanagerexample.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sunny.contactmanagerexample.MainActivity;
import com.sunny.contactmanagerexample.R;
import com.sunny.contactmanagerexample.data.DatabaseHandler;
import com.sunny.contactmanagerexample.model.Contact;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>{
    private List<Contact> localContactList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name;
        private final TextView phoneNumber;
        private final ImageView iconButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconButton = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            phoneNumber = itemView.findViewById(R.id.phone_number);
//            itemView.setOnClickListener(this);
            iconButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Contact contact = localContactList.get(position);
            DatabaseHandler myDb = new DatabaseHandler(RecycleViewAdapter.this.context);
//            Log.d("myAdapter", "onClick: "+position+" "+localContactList.get(position));
//            Log.d("myAdapter", "onClick: "+contact.getId()+" "+myDb.getContact(contact.getId()));
            myDb.deleteContact(contact.getId());
            localContactList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());


        }
    }

    public RecycleViewAdapter(Context context,List<Contact> contactList){
        this.context = context;
        localContactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(localContactList.get(position).getName());
        holder.phoneNumber.setText(localContactList.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return localContactList.size();
    }

}
