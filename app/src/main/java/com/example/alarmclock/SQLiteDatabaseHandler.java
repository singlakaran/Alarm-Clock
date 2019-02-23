package com.example.alarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AlarmDB";
    private static final String TABLE_NAME = "alarmList";
    private static final String ID = "id";
    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";
    private static final String[] COLUMNS = { ID, HOUR, MINUTE};

    public SQLiteDatabaseHandler(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE alarmList ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "hour TEXT, "
                + "minute TEXT)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Alarm alarm) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(alarm.getId()) });
        db.close();
    }

    public Alarm getPlayer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Alarm player = new Alarm();
        player.setId(Integer.parseInt(cursor.getString(0)));
        player.setHour(cursor.getString(1));
        player.setMinute(cursor.getString(2));

        return player;
    }

    public List<Alarm> allPlayers() {

        List<Alarm> alarmList = new LinkedList<Alarm>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Alarm alarm = null;

        if (cursor.moveToFirst()) {
            do {
                alarm = new Alarm();
                alarm.setId(Integer.parseInt(cursor.getString(0)));
                alarm.setHour(cursor.getString(1));
                alarm.setMinute(cursor.getString(2));
                alarmList.add(alarm);
            } while (cursor.moveToNext());
        }

        return alarmList;
    }

    public void addAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HOUR, alarm.getHour());
        values.put(MINUTE, alarm.getMinute());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updatePlayer(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HOUR, alarm.getHour());
        values.put(MINUTE, alarm.getMinute());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(alarm.getId()) });

        db.close();

        return i;
    }

}
