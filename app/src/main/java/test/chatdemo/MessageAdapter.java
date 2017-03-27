package test.chatdemo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.chatdemo.views.BillView;

public class MessageAdapter extends CursorAdapter {

    private Context context;
    private int animationCount = 0;
    private static final ScaleAnimation INCOMING_ANIMATION = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF,0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
    private static final ScaleAnimation OUTGOING_ANIMATION = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_SELF , 1);
    private ListView listView;
    private int fullWidth;

    public MessageAdapter(Context context, Cursor cursor, ListView listView) {
        super(context, cursor, 0);
        this.context = context;
        this.listView = listView;
        animationCount = cursor.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Cursor cursor = (Cursor)getItem(position);
        //Pick correct layout
        ChatMessage chatMessage = ChatMessage.getChatMessage(cursor);
        int layoutResource = 0;

        if (chatMessage.isOutgoing()) {
            layoutResource = R.layout.item_chat_outgoing;

        }else if (chatMessage.isIncoming()) {
            layoutResource = R.layout.item_chat_incoming;

        } else if (chatMessage.isBill()) {
            layoutResource = R.layout.item_bill;
        }

        View view = LayoutInflater.from(context).inflate(layoutResource, parent, false);

        //Load data
        if (!chatMessage.isBill()) {
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

        //Add date if needed
        if(needsDate(position, chatMessage.getDate())){
            View dateView = LayoutInflater.from(context).inflate(R.layout.item_date, parent, false);
            TextView dateText = ButterKnife.findById(dateView,R.id.date_text);
            if(dateText!=null){
                dateText.setText(AndroidUtils.getDate(chatMessage.getDate()));
            }
            view = addDate(context, view, dateView);
        }
        //Do animations
        if(animationCount < position){
            ScaleAnimation anim = null;
            if (!chatMessage.isBill()) {
                if(chatMessage.isIncoming()){
                    anim = INCOMING_ANIMATION;
                }else if(chatMessage.isOutgoing()){
                    anim = OUTGOING_ANIMATION;
                }
                anim.setDuration(context.getResources().getInteger(R.integer.animation_messages));
                view.startAnimation(anim);
            }else {
                if(this.fullWidth  == 0 ){
                    this.fullWidth =this.listView.getWidth();
                }
                ((BillView)view).animateBill(this.fullWidth);
            }
            animationCount = position;
        }else{
            if(chatMessage.isBill()){
                ((BillView)view).displayBill();
            }
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
