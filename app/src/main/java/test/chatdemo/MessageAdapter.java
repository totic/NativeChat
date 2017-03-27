package test.chatdemo;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.chatdemo.views.BillView;

public class MessageAdapter extends CursorAdapter {

    private Context context;
    private boolean animated = false;


    public MessageAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Cursor cursor = (Cursor)getItem(position);

        ChatMessage chatMessage = ChatMessage.getChatMessage(cursor);
        int layoutResource = 0;
        int type = chatMessage.getMessageType();

        if (type == MessageType.OUTGOING.getValue()) {
            layoutResource = R.layout.item_chat_outgoing;}

        else if (type == MessageType.INCOMING.getValue()) {
            layoutResource = R.layout.item_chat_incoming;

        } else if (type == MessageType.BILL.getValue()) {
            layoutResource = R.layout.item_bill;
        }

        View view = LayoutInflater.from(context).inflate(layoutResource, parent, false);

        if (chatMessage.getMessageType() != MessageType.BILL.getValue()) {
            ViewHolderMsg holder = new ViewHolderMsg(view);
            view.setTag(holder);
            holder.msg.setText(chatMessage.getBody());
        }else{
            BillView billView = new BillView(context);
            billView.setAccountNumber("7283 23 9812389 21");
            billView.setDueDate(AndroidUtils.getDayFormat(new Date()));
            billView.setPrice("$123.54");
            billView.setTaxes("$13.36");
            billView.setTotal("$136.90");
            view = billView;
        }


        if(needsDate(position, chatMessage.getDate())){
            View dateView = LayoutInflater.from(context).inflate(R.layout.item_date, parent, false);
            TextView dateText = ButterKnife.findById(dateView,R.id.date_text);
            if(dateText!=null){
                dateText.setText(AndroidUtils.getDate(chatMessage.getDate()));
            }
            view = addDate(context, view, dateView);
        }
        return view;
    }

    private int getItemViewType(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndex(DBMessageHandler.COLUMN_MESSAGE_TYPE))+1;
    }

    @Override
    public int getItemViewType(int position) {
        Cursor cursor = (Cursor) getItem(position);
        return getItemViewType(cursor);
    }

    /**
     * Checks if we need to add a date before the next message
     * @param position
     * @return
     */
    private boolean needsDate(int position, Date date){
        if(position == 0){
            return true;
        }else {
            Cursor cursor = (Cursor) getItem(position -1);
            ChatMessage previousChatMessage = ChatMessage.getChatMessage(cursor);
            return !AndroidUtils.sameDay(previousChatMessage.getDate(), date);
        }
    }

    public View addDate(Context context, View view, View date){
        LinearLayout ll = new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(date);
        ll.addView(view);
        return ll;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
    }

    public class ViewHolderMsg {
        @BindView(R.id.txt_msg) TextView msg;
        public ViewHolderMsg(View v) {
            ButterKnife.bind(this, v);
        }
    }

}
