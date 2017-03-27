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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends Activity {

    private MessageAdapter adapter;
    private DBMessageHandler handler;
    private Handler msghandler = new Handler();

    private final Pattern patternBill = Pattern.compile(".*\\bbill(s?)\\b.*",Pattern.DOTALL|Pattern.CASE_INSENSITIVE);

    @BindView(R.id.list_msg) ListView listView;
    @BindView(R.id.btn_chat_send) Button btnSend;
    @BindView(R.id.msg_type) EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        handler = new DBMessageHandler(this);
        adapter = new MessageAdapter(this, handler.getCursor());
        listView.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length()==0) {
                    Toast.makeText(ChatActivity.this, R.string.input_text, Toast.LENGTH_SHORT).show();
                } else {
                    String msg = editText.getText().toString();
                    Matcher m = patternBill.matcher(msg);
                    if(!m.find()){
                        handler.addMessage(new ChatMessage(msg, MessageType.OUTGOING.getValue()));
                        sendIncomingMsg(msg);
                    }else{
                        handler.addMessage(new ChatMessage(msg, MessageType.OUTGOING.getValue()));
                        handler.addMessage(new ChatMessage(msg, MessageType.BILL.getValue()));
                    }
                    adapter.swapCursor(handler.getCursor());
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });
    }

    public void sendIncomingMsgWithDelay(final String msg) {
        Runnable runnable = new Runnable() {
            public void run() {
                handler.addMessage(new ChatMessage(msg, MessageType.INCOMING.getValue(), new Date()));
                Cursor cursor = handler.getCursor();
                adapter.swapCursor(cursor);
                adapter.notifyDataSetChanged();
            }
        };
        msghandler.postDelayed(runnable, 3000);
    }

    public void sendIncomingMsg(final String msg) {
        handler.addMessage(new ChatMessage(msg, MessageType.INCOMING.getValue(), new Date()));
    }
}
