package data.com.model;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by camillepire on 15/10/2014.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_PHARMA = "pharmacie";
    public static final String TABLE_DISTRIB = "distributeur";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String COLUMN_TEL = "telephone";
    public static final String COLUMN_HORAIRE = "horaire";
    public static final String COLUMN_ADDRESS = "adresse";

    private static final String DATABASE_NAME = "caoutchou.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE_PH = "create table "
            + TABLE_PHARMA + "(" + COLUMN_ID
            + " integer primary key autoincrement, " +
            COLUMN_NAME + " text, "+
            COLUMN_LAT + " REAL, "+
            COLUMN_LNG + " REAL, "+
            COLUMN_TEL + " text, "+
            COLUMN_ADDRESS + " text "
            +");";
    private static final String DATABASE_CREATE_DIS = "create table "
            + TABLE_DISTRIB + "(" + COLUMN_ID
            + " integer primary key autoincrement, " +
            COLUMN_NAME + " text, "+
            COLUMN_LAT + " REAL, "+
            COLUMN_LNG + " REAL, "+
            COLUMN_HORAIRE + " text, "+
            COLUMN_ADDRESS + " text "
            +");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_PH);
        database.execSQL(DATABASE_CREATE_DIS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHARMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRIB);
        onCreate(db);
    }
}
