package test.chatdemo;

import android.database.Cursor;

import java.util.Date;

public class ChatMessage {


    private String body;
    private int messageType;
    private Date date;

    public ChatMessage(String body, int messageType, Date date) {
        this.body = body;
        this.messageType = messageType;
        this.date = date;
    }

    public ChatMessage(String body, int messageType) {
        this(body,messageType,new Date());
    }

    public boolean isBill() {
        return messageType == MessageType.BILL.getValue();
    }
    public boolean isMine() {
        return messageType == MessageType.OUTGOING.getValue();
    }

    public String getBody() {
        return body;
    }

    public int getMessageType() {
        return messageType;
    }

    public Date getDate() {
        return date;
    }

    public static ChatMessage getChatMessage(Cursor cursor){
        int type = cursor.getInt(cursor.getColumnIndexOrThrow(DBMessageHandler.COLUMN_MESSAGE_TYPE));
        String body = cursor.getString(cursor.getColumnIndexOrThrow(DBMessageHandler.COLUMN_BODY));
        Date date = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(DBMessageHandler.COLUMN_TIMESTAMP)));
        return new ChatMessage(body, type, date);
    }

}
