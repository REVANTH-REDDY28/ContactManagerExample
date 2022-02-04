package com.sunny.contactmanagerexample.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.sunny.contactmanagerexample.R;
import com.sunny.contactmanagerexample.model.Contact;
import com.sunny.contactmanagerexample.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        //1.changing the constructor
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //2.create table
        String CREATE_CONTACT_TABLE = "CREATE TABLE "+Util.TABLE_NAME+ "( "
                +Util.CONTACT_COLUMN_ID+" INTEGER PRIMARY KEY,"
                +Util.CONTACT_COLUMN_NAME+" TEXT,"
                +Util.CONTACT_COLUMN_PHONE_NUMBER+" TEXT"+")";
        db.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //3.drop table
        String DROP_TABLE = String.valueOf(R.string.drop_table_db);
        db.execSQL(DROP_TABLE, new String[]{Util.TABLE_NAME});
        //4.create a table again
        onCreate(db);
    }
    /*CRUD = create read update delete*/
    //add contact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(Util.CONTACT_COLUMN_ID,contact.getId());
        values.put(Util.CONTACT_COLUMN_NAME,contact.getName());
        values.put(Util.CONTACT_COLUMN_PHONE_NUMBER,contact.getPhoneNumber());

//        String INSERT_INTO_TABLE = "INSERT INTO "+Util.TABLE_NAME+
//                "VALUES("+values.get(Util.CONTACT_COLUMN_NAME)+","+
//                values.get(Util.CONTACT_COLUMN_PHONE_NUMBER)+")";
        db.insert(Util.TABLE_NAME,null,values);
//        Log.d("myDb", "addContact: ");
    }

    //get contact
    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME,
                new String[]{Util.CONTACT_COLUMN_ID,Util.CONTACT_COLUMN_NAME,Util.CONTACT_COLUMN_PHONE_NUMBER},
                Util.CONTACT_COLUMN_ID+"=?",new String[]{String.valueOf(id)},
                null,null,null);


        if(cursor!=null)
            cursor.moveToFirst();

        Contact contact = new Contact();
        if(cursor != null){
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));
        }
        return contact;

    }
    public List<Contact> getAllContacts(){
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_ENTIRE_TABLE = "SELECT * FROM "+Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(SELECT_ENTIRE_TABLE,null);
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contactList.add(contact);
            }while (cursor.moveToNext());
        }
        return contactList;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.CONTACT_COLUMN_NAME,contact.getName());
        values.put(Util.CONTACT_COLUMN_PHONE_NUMBER,contact.getPhoneNumber());

        return db.update(Util.TABLE_NAME,values,
                Util.CONTACT_COLUMN_ID+"=?",new String[]{String.valueOf(contact.getId())});
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
//        String DELETE_CONTACT_SQL = "DELETE FROM "+Util.TABLE_NAME+" WHERE "+Util.CONTACT_COLUMN_ID+
//                "= "+contact.getId();
//        Log.d("myAdap", "deleteContact: "+getContact(id));
        db.delete(Util.TABLE_NAME,Util.CONTACT_COLUMN_ID+" = ? ",new String[]{String.valueOf(id)});
//        db.execSQL(DELETE_CONTACT_SQL);

    }

    public int getCount(){
        String SELECT_TABLE_SQL = "SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_TABLE_SQL,null);

        return cursor.getCount();
    }

}
