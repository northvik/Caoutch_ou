package data.com.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by camillepire on 15/10/2014.
 */
public class DistributeurDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_LAT,
            MySQLiteHelper.COLUMN_LNG,
            MySQLiteHelper.COLUMN_HORAIRE,
            MySQLiteHelper.COLUMN_ADDRESS
    };

    public DistributeurDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Distributeur createDistributeur(String name, Double lat, Double lng, String horaire, String address) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_LAT, lat);
        values.put(MySQLiteHelper.COLUMN_LNG, lng);
        values.put(MySQLiteHelper.COLUMN_HORAIRE, horaire);
        values.put(MySQLiteHelper.COLUMN_ADDRESS, address);

        long insertId = database.insert(MySQLiteHelper.TABLE_DISTRIB, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_DISTRIB,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Distributeur newDistributeur = cursorToDistributeur(cursor);
        cursor.close();
        return newDistributeur;
    }

    public Distributeur createDistributeur(Distributeur distributeur) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, distributeur.getName());
        values.put(MySQLiteHelper.COLUMN_LAT, distributeur.getLat());
        values.put(MySQLiteHelper.COLUMN_LNG, distributeur.getLng());
        values.put(MySQLiteHelper.COLUMN_HORAIRE, distributeur.getHoraires());
        values.put(MySQLiteHelper.COLUMN_ADDRESS, distributeur.getAdrComplete());

        long insertId = database.insert(MySQLiteHelper.TABLE_DISTRIB, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_DISTRIB,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Distributeur newDistributeur = cursorToDistributeur(cursor);
        cursor.close();
        return newDistributeur;
    }

    public void deleteDistributeur(Distributeur Distributeur) {
        long id = Distributeur.getId();
        System.out.println("Distributeur deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_DISTRIB, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Distributeur> getAllDistributeurs() {
        List<Distributeur> Distributeurs = new ArrayList<Distributeur>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DISTRIB,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Distributeur Distributeur = cursorToDistributeur(cursor);
            Distributeurs.add(Distributeur);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return Distributeurs;
    }

    public List<Distributeur> getCoordonateDistributeurs(ArrayList<LatLng> view) {
        List<Distributeur> Distributeurs = new ArrayList<Distributeur>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DISTRIB,
                allColumns,
                MySQLiteHelper.COLUMN_LAT + " <= " + view. +
                " AND " + MySQLiteHelper.COLUMN_LAT + "  >= ",
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Distributeur Distributeur = cursorToDistributeur(cursor);
            Distributeurs.add(Distributeur);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return Distributeurs;
    }

    private Distributeur cursorToDistributeur(Cursor cursor) {
        Distributeur Distributeur = new Distributeur();
        Distributeur.setId(cursor.getInt(0));
        Distributeur.setName(cursor.getString(1));
        Distributeur.setLat(cursor.getDouble(2));
        Distributeur.setLng(cursor.getDouble(3));
        Distributeur.setHoraires(cursor.getString(4));
        Distributeur.setAdrComplete(cursor.getString(5));
        return Distributeur;
    }
}
