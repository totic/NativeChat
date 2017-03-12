package test.chatdemo;

public class BillMessage {

    private String content;
    private boolean isMine;

    public BillMessage(String content, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }
}
