package com.example.loginsignupsystem.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GuidesDaoImp implements GuidesDao{
    private static final String dbTable = "guides";
    private static final String ID = "id";
    private static final String GUIDNAME = "guideName";
    private static final String LANGUAGE = "language";
    private static final String IMAGE = "image";

    private final DbHelper dbHelper;

    public GuidesDaoImp(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long addGuide(Guides guide) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GUIDNAME, guide.getGuidName());
        cv.put(LANGUAGE, guide.getLanguage());
        cv.put(IMAGE, guide.getImage());

        long result = db.insert(dbTable, null, cv);
        db.close();
        return result;
    }

    public List<Guides> getAllGuides() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Guides> guides = new ArrayList<>();
        Cursor cursor = db.query(dbTable, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Guides guide = new Guides(
                        cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(GUIDNAME)),
                        cursor.getString(cursor.getColumnIndex(LANGUAGE)),
                        cursor.getInt(cursor.getColumnIndex(IMAGE))
                );
                guides.add(guide);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return guides;
    }

    public Guides getGuideById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Guides guide = null;
        Cursor cursor = db.query(dbTable, null, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            guide = new Guides(
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(GUIDNAME)),
                    cursor.getString(cursor.getColumnIndex(LANGUAGE)),
                    cursor.getInt(cursor.getColumnIndex(IMAGE))
            );
        }
        cursor.close();
        db.close();
        return guide;
    }

    public List<Guides> getGuidesByLanguage(String language) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Guides> guides = new ArrayList<>();
        Cursor cursor = db.query(dbTable, null, LANGUAGE + "=?", new String[]{language}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Guides guide = new Guides(
                        cursor.getInt(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(GUIDNAME)),
                        cursor.getString(cursor.getColumnIndex(LANGUAGE)),
                        cursor.getInt(cursor.getColumnIndex(IMAGE))
                );
                guides.add(guide);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return guides;
    }

}
