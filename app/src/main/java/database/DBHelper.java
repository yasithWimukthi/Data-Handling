package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ninjacode98.datahandling.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String SQL_CREATE_ENTRIES = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY,%s TEXT,%s TEXT)", UsersMaster.Users.TABLE_NAME, UsersMaster.Users._ID, UsersMaster.Users.COLUMN_NAME_USERNAME, UsersMaster.Users.COLUMN_NAME_PASSWORD);

        String SQL_CREATE_ENTRIES = "CREATE TABLE " + UsersMaster.Users.TABLE_NAME +" ("+
                UsersMaster.Users._ID + " INTEGER PRIMARY KEY, " +
                UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT, " +
                UsersMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";


        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addInfo(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME,username);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);

        long newRowID = db.insert(UsersMaster.Users.TABLE_NAME,null,values);
        return newRowID;
    }

    public List readAllInfo(){
        SQLiteDatabase db = getReadableDatabase();

        String [] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List usernames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while(cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            usernames.add(username);
            passwords.add(password);
        }

        cursor.close();
        return usernames;
    }

    public boolean readInfo(String username, String password){
        SQLiteDatabase db = getReadableDatabase();

        String [] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        //String sortOrder = UsersMaster.Users.COLUMN_NAME_USERNAME + "DESC";

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ? AND " + UsersMaster.Users.COLUMN_NAME_PASSWORD + " LIKE ? " ;
        String []selectionArgs = {username,password};

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List usernames = new ArrayList<>();
        List passwords = new ArrayList<>();

        while(cursor.moveToNext()){
            String selectedUsername = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String selectedPassword = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            usernames.add(selectedUsername);
            passwords.add(selectedPassword);
        }

        if(!passwords.isEmpty()){
            return true;
        }

        return false;
    }

    public boolean deleteInfo(String username){
        SQLiteDatabase db = getReadableDatabase();

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME +" LIKE ? ";
        String [] selectionArgs = {username};

        return db.delete(UsersMaster.Users.TABLE_NAME,selection,selectionArgs) > 0;
    }

    public int updateInfo(String username, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String []selectionArgs = {username};

        int count = db.update(
                UsersMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
        return count;
    }
}
