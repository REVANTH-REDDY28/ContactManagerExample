package com.sunny.contactmanagerexample;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sunny.contactmanagerexample.adapter.RecycleViewAdapter;
import com.sunny.contactmanagerexample.data.DatabaseHandler;
import com.sunny.contactmanagerexample.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    ArrayList<Contact> contactArrayList;
    RecyclerView recyclerView;


    RecycleViewAdapter recycleViewAdapter;
    FloatingActionButton fab;
    public static final int REQUEST_CODE = 1;
    DatabaseHandler myDb;


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent in = result.getData();
                        String name = in.getStringExtra("name");
                        String number = in.getStringExtra("number");
                        Contact contact = new Contact(name,number);
                        contactArrayList.add(contact);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);


        View mainConstraintLayout = findViewById(R.id.constraint_of_main);

        fab= findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,SaveContactActivity.class);
////                startActivityForResult(intent,REQUEST_CODE);
//                someActivityResultLauncher.launch(intent);
                saveContactDialog();
            }

        });

        contactArrayList = new ArrayList<>();
        myDb = new DatabaseHandler(this.getApplicationContext());

        contactArrayList.addAll(myDb.getAllContacts());

        if(myDb.getCount() > 0){
            mainConstraintLayout.setBackgroundColor(Color.BLACK);
        }


//        for(Contact contact:myDb.getAllContacts()){
//            contactArrayList.add(contact.getId(),contact);
//        }

        recycleViewAdapter = new RecycleViewAdapter(MainActivity.this,contactArrayList);
        recyclerView.setAdapter(recycleViewAdapter);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK){
//            String name = data.getStringExtra("name");
//            String number = data.getStringExtra("number");
//            Contact contact = new Contact(name,number);
//            contactArrayList.add(contact);
//
//        }
//    }

//    public void activityResultLauncher(Intent intent){
//        registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if(result.getResultCode() == RESULT_OK){
//                            Intent in = result.getData();
//                            String name = in.getStringExtra("name");
//                            String number = in.getStringExtra("number");
//                            Contact contact = new Contact(name,number);
//                            contactArrayList.add(contact);
//                        }
//                    }
//                }).launch(intent);
//    }

    public void saveContactDialog(){
//        final Dialog dialog = new Dialog(MainActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog;
        View view = getLayoutInflater().inflate(R.layout.popup_save_contact,null);
        EditText name = view.findViewById(R.id.s_name);
        EditText number = view.findViewById(R.id.s_number);
        Button btn = view.findViewById(R.id.button_save_contact);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c_name = String.valueOf(name.getText());
                String c_num = String.valueOf(number.getText());
                Contact contact = new Contact(c_name,c_num);
                myDb.addContact(contact);
                alertDialog.dismiss();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                overridePendingTransition(0,0);
                finish();
            }
        });
    }
    

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setView(R.layout.contact_row);

        dialog.setMessage("Please Select any option");
        dialog.setTitle("Dialog Box");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
    public static void activityRestart(AppCompatActivity appCompatActivity){
//        appCompatActivity.finish();
        appCompatActivity.recreate();
        appCompatActivity.overridePendingTransition(0,0);
    }
}