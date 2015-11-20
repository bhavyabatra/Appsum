package com.ttu.spm.appsum.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Pareshan on 11/16/2015.
 */
public class VolunteerDataSource {

    private static final String[] allColumns = {

            "rowid _id",
            VolunteerDBOpenHelper.VOLUNTEER_ID,
            VolunteerDBOpenHelper.VOLUNTEER_NAME,
            VolunteerDBOpenHelper.VOLUNTEER_EMAIL,
            VolunteerDBOpenHelper.VOLUNTEER_TELENO,
            VolunteerDBOpenHelper.VOLUNTEER_CITY,
            VolunteerDBOpenHelper.DATE_MODIFIED,
            VolunteerDBOpenHelper.NOTES
    };

    private static final String LOGTAG = "VolunteerDataSource";
    private static final String clause = VolunteerDBOpenHelper.VOLUNTEER_CITY + " = ?" + " and " +
            VolunteerDBOpenHelper.VOLUNTEER_COUNTRY + " = ?";
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    public VolunteerDataSource(Context context) {
        dbhelper = new VolunteerDBOpenHelper(context);
    }


    public void open() {
        Log.i(LOGTAG, " Database Opened:");
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhelper.close();
    }

    public Cursor getData(String city, String country) {

        open();
        Log.i(LOGTAG, city);
        Log.i(LOGTAG, country);

      //  database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('Jackson','JJ365@gmail.com','806-723-999','Lubbock', 'United States')");
     //database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,) VALUES('Sam','Sam@gmail.com','806-723-994 ','Lubbock', 'United States')");

        database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('Patterson','Patterson@gmail.com',' ','Lubbock', 'United States')" );
        database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('Kid','Kid@gmail.com',' ','Lubbock', 'United States')" );
        database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('Jamal','Jamal@gmail.com','806-723-992','Lubbock', 'United States')" );
        database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('Patty','Patty@gmail.com',' ','Lubbock', 'United States')" );
        database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('Rogers Adam','Rogers Adam5@gmail.com',' ','Lubbock', 'United States')" );
        database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('Christy Keeeive','Keeeive65@gmail.com',' ','Lubbock', 'United States')" );
        database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('Jacob','Jacob.Jones365@gmail.com',' ','Lubbock', 'United States')" );
        database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('Iyer','Iyer365@gmail.com',' ','Hyderabad', 'India')" );
        database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('Ahmed','ahmed@gmail.com',' ','Hyderabad', 'Pakistan')" );
        database.execSQL("INSERT INTO VOLUNTEER(volunteer_name,volunteer_email,volunteer_teleno,volunteer_city,volunteer_country) VALUES('JJackson','JJJ365@gmail.com',' ','Lubbock', 'United States')" );


        Cursor cursor = database.query(VolunteerDBOpenHelper.TABLE_NAME_VOLUNTEER,  //table name
                allColumns, //columns
                clause, //where condition
                new String[]{city, country}, //where coditions[]
                null, //group by
                null, // having
                null); // order by


        Log.i(LOGTAG, "Cursor Executed, Rows fetched " + cursor.getCount());

        return cursor;

    }


}// end of class
