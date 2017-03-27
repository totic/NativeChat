package test.chatdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by federico on 3/24/17.
 */

public class DBMessageHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "asapp_messages";
    public static final String TABLE_MESSAGES = "messages";
    public static final String COLUMN_MESSAGES_ID = "_id";
    public static final String COLUMN_BODY = "BODY";
    public static final String COLUMN_MESSAGE_TYPE = "MESSAGE_TYPE";
    public static final String COLUMN_TIMESTAMP = "TIMESTAMP";

    private static final String INTEGER_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String DEFAULT = " DEFAULT";


    public DBMessageHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGES  + " ("+
                        COLUMN_MESSAGES_ID +    TEXT_TYPE       + " PRIMARY KEY" + COMMA_SEP +
                        COLUMN_BODY +           TEXT_TYPE       + COMMA_SEP +
                        COLUMN_MESSAGE_TYPE +   INTEGER_TYPE    + COMMA_SEP +
                        COLUMN_TIMESTAMP +      INTEGER_TYPE    + DEFAULT + " 0" + " )";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }

    public Cursor getCursor(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_MESSAGES, new String[]
                        {COLUMN_MESSAGES_ID, COLUMN_BODY, COLUMN_MESSAGE_TYPE, COLUMN_TIMESTAMP},
                null, null, null, null, null);

    }

    public void addMessage(ChatMessage message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BODY, message.getBody());
        values.put(COLUMN_MESSAGE_TYPE, message.getMessageType().getValue());
        values.put(COLUMN_TIMESTAMP, new Date().getTime());
        db.insert(TABLE_MESSAGES, null, values);
        db.close(); // Closing database connection
    }
}
