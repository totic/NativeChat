package test.chatdemo;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends Activity {

    private MessageAdapter adapter;
    private DBMessageHandler handler;
    private Handler msghandler = new Handler();

    private final Pattern PATTERN_BILL = Pattern.compile(".*\\bbill(s?)\\b.*",Pattern.DOTALL|Pattern.CASE_INSENSITIVE);

    @BindView(R.id.list_msg) ListView listView;
    @BindView(R.id.btn_chat_send) Button sendBtn;
    @BindView(R.id.msg_type) EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        handler = new DBMessageHandler(this);
        adapter = new MessageAdapter(this, handler.getCursor(),listView);
        listView.setAdapter(adapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length()==0) {
                    Toast.makeText(ChatActivity.this, R.string.input_text, Toast.LENGTH_SHORT).show();
                } else {
                    String msg = editText.getText().toString();
                    Matcher m = PATTERN_BILL.matcher(msg);
                    addMessages(new ChatMessage(msg, MessageType.OUTGOING));
                    if(!m.find()){
                        sendIncomingMsgWithDelay(new ChatMessage(msg, MessageType.INCOMING), 1000);
                    }else{
                        sendIncomingMsgWithDelay(new ChatMessage(getString(R.string.current_statement_header), MessageType.INCOMING), 1000);
                        sendIncomingMsgWithDelay(new ChatMessage(null, MessageType.BILL), 1500);
                    }
                    editText.setText("");
                }
            }
        });
    }

    public void addMessages(ChatMessage message){
        handler.addMessage(message);
        adapter.swapCursor(handler.getCursor());
        adapter.notifyDataSetChanged();
        adapter.setDoAnimated(true);
    }

    public void sendIncomingMsgWithDelay(final ChatMessage message, int time) {
        Runnable runnable = new Runnable() {
            public void run() {
                addMessages(message);
            }
        };
        msghandler.postDelayed(runnable, time);
    }
}
