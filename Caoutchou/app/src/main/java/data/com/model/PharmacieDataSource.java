package data.com.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillepire on 15/10/2014.
 */
public class PharmacieDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_LAT,
            MySQLiteHelper.COLUMN_LNG,
            MySQLiteHelper.COLUMN_TEL,
            MySQLiteHelper.COLUMN_ADDRESS
    };

    public PharmacieDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Pharmacie createPharmacie(String name, Double lat, Double lng, String tel, String address) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_LAT, lat);
        values.put(MySQLiteHelper.COLUMN_LNG, lng);
        values.put(MySQLiteHelper.COLUMN_TEL, tel);
        values.put(MySQLiteHelper.COLUMN_ADDRESS, address);
        
        long insertId = database.insert(MySQLiteHelper.TABLE_PHARMA, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PHARMA,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Pharmacie newPharmacie = cursorToPharmacie(cursor);
        cursor.close();
        return newPharmacie;
    }

    public void deletePharmacie(Pharmacie Pharmacie) {
        long id = Pharmacie.getId();
        System.out.println("Pharmacie deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_PHARMA, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Pharmacie> getAllPharmacies() {
        List<Pharmacie> Pharmacies = new ArrayList<Pharmacie>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PHARMA,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Pharmacie Pharmacie = cursorToPharmacie(cursor);
            Pharmacies.add(Pharmacie);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return Pharmacies;
    }

    private Pharmacie cursorToPharmacie(Cursor cursor) {
        Pharmacie Pharmacie = new Pharmacie();
        Pharmacie.setId(cursor.getInt(0));
        Pharmacie.setName(cursor.getString(1));
        Pharmacie.setLat(cursor.getDouble(2));
        Pharmacie.setLng(cursor.getDouble(3));
        Pharmacie.setTelephone(cursor.getString(4));
        Pharmacie.setAdrComplete(cursor.getString(5));
        return Pharmacie;
    }
}
