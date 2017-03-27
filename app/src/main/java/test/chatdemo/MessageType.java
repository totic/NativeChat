package test.chatdemo;

/**
 * Created by federico on 3/24/17.
 */

public enum MessageType {

        INCOMING(0),
        OUTGOING(1),
        BILL(2),
        DATE(3);

        private int intValue;

        private MessageType(int value) {
            intValue = value;
        }

        public int getValue(){
            return intValue;
        }


}
