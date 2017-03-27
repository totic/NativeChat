package test.chatdemo.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.chatdemo.R;

/**
 * Created by federico on 3/17/17.
 */

public class BillView extends LinearLayout {

    private View rootView;
    private TextView accountNumber;
    private TextView price;
    private TextView taxes;
    private TextView total;
    private TextView dueDate;

    public BillView(Context context) {
        super(context);
        init(context);
    }

    public BillView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.item_bill, this);
        this.accountNumber =  (TextView)rootView.findViewById(R.id.account_number);
        this.price =          (TextView)rootView.findViewById(R.id.product_price);
        this.taxes =          (TextView)rootView.findViewById(R.id.taxes);
        this.dueDate =        (TextView)rootView.findViewById(R.id.due_date);
        this.total =          (TextView)rootView.findViewById(R.id.total);
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber.setText(accountNumber);
    }

    public void setPrice(String price) {
        this.price.setText(price);
    }

    public void setTaxes(String taxes) {
        this.taxes.setText(taxes);
    }

    public void setTotal(String total) {
        this.total.setText(total);
    }

    public void setDueDate(String dueDate) {
        this.dueDate.setText(dueDate);
    }
}
