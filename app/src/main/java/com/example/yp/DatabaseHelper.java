package com.example.yp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDataBase.db";
    private static final String TABLE_NAME = "users";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "EMAIL";
    private static final String COL_4 = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT, " +
                "EMAIL TEXT, " +
                "PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(String username, String email, String password) {
        if (!isEmailUnique(email)) {
            // Email уже существует, регистрация невозможна
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL=? AND PASSWORD=?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean updateUser(String oldEmail, String newUsername, String newEmail, String newGroup, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, newUsername);
        contentValues.put(COL_3, newEmail);
        contentValues.put(COL_4, newPassword);

        int result = db.update(TABLE_NAME, contentValues, "EMAIL = ?", new String[]{oldEmail});
        return result > 0; // true, если обновление прошло успешно
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{email});
        return cursor;
    }

    public boolean isEmailUnique(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMAIL = ?", new String[]{email});
        boolean isUnique = cursor.getCount() == 0; // если записей с таким email нет - email уникален
        cursor.close();
        return isUnique;
    }

    // Новый метод для удаления аккаунта по email
    public boolean deleteAccount(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Удаляем пользователя
        int result = db.delete(TABLE_NAME, "EMAIL = ?", new String[]{email});
        // Также можно удалить связанные задачи пользователя
        db.delete("tasks", "user_email = ?", new String[]{email});
        return result > 0; // true, если удаление прошло успешно
    }

    public boolean checkPassword(String email, String passwordToCheck) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_4 + " FROM " + TABLE_NAME + " WHERE " + COL_3 + " = ?",
                new String[]{email}
        );

        if (cursor.moveToFirst()) {
            String storedPassword = cursor.getString(0); // Получаем сохранённый пароль из базы
            cursor.close();
            return storedPassword.equals(passwordToCheck); // Сравниваем пароли
        }

        cursor.close();
        return false; // Пользователь не найден
    }
}

