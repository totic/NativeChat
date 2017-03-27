package test.chatdemo;

import android.database.Cursor;

import java.util.Date;

public class ChatMessage {


    private String body;
    private MessageType messageType;
    private Date date;


    public ChatMessage(String body, MessageType messageType, Date date) {
        this.body = body;
        this.messageType = messageType;
        this.date = date;
    }

    public ChatMessage(String body, MessageType messageType) {
        this(body, messageType, new Date());
    }

    public boolean isBill() {
        return messageType == MessageType.BILL;
    }

    public boolean isOutgoing() {
        return messageType == MessageType.OUTGOING;
    }

    public boolean isIncoming() {
        return messageType == MessageType.INCOMING;
    }

    public String getBody() {
        return body;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Date getDate() {
        return date;
    }

    public static ChatMessage getChatMessage(Cursor cursor){
        int type = cursor.getInt(cursor.getColumnIndexOrThrow(DBMessageHandler.COLUMN_MESSAGE_TYPE));
        String body = cursor.getString(cursor.getColumnIndexOrThrow(DBMessageHandler.COLUMN_BODY));
        Date date = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(DBMessageHandler.COLUMN_TIMESTAMP)));
        MessageType messageType = MessageType.fromValue(type);
        return new ChatMessage(body, messageType, date);
    }

}
