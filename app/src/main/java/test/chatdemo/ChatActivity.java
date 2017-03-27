package test.chatdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends Activity {

    private MessageAdapter adapter;
    private DBMessageHandler handler;
    private Handler msghandler = new Handler();

    @BindView(R.id.list_msg) ListView listView;
    @BindView(R.id.btn_chat_send) Button btnSend;
    @BindView(R.id.msg_type) EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        handler = new DBMessageHandler(this);
        Cursor cursor = handler.getCursor();

        adapter = new MessageAdapter(this, cursor);
        listView.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatActivity.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    String msg = editText.getText().toString();

                    if(!msg.contains("bill")){
                        handler.addMessage(new ChatMessage(msg, MessageType.OUTGOING.getValue(), new Date()));
                        sendIncomingMsg(msg);
                    }else{
                        handler.addMessage(new ChatMessage(msg, MessageType.BILL.getValue(), new Date()));
                    }
                    Cursor cursor = handler.getCursor();
                    adapter.swapCursor(cursor);
                    adapter.notifyDataSetChanged();

                    editText.setText("");
                }
            }
        });

    }

    public void sendIncomingMsgWithDelay(final String msg) {
        Runnable runnable = new Runnable() {
            public void run() {
                handler.addMessage(new ChatMessage(msg + " -- A", MessageType.INCOMING.getValue(), new Date()));
                Cursor cursor = handler.getCursor();
                adapter.swapCursor(cursor);
                adapter.notifyDataSetChanged();
            }
        };
        msghandler.postDelayed(runnable, 3000);
    }

    public void sendIncomingMsg(final String msg) {
        handler.addMessage(new ChatMessage(msg + " -- A", MessageType.INCOMING.getValue(), new Date()));
    }
}
