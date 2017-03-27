package test.chatdemo.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;

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
    private LinearLayout fullLayout;
    private LinearLayout topLayout;
    private LinearLayout bottomLayout;
    private FrameLayout frameLayout;

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
        this.frameLayout =    (FrameLayout)rootView.findViewById(R.id.frame_bill);
        this.fullLayout=      (LinearLayout)rootView.findViewById(R.id.all_bill);
        this.topLayout =      (LinearLayout)rootView.findViewById(R.id.top_bill);
        this.bottomLayout=    (LinearLayout)rootView.findViewById(R.id.bottom_bill);
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

    public void animateBill(final int fullWidth){
        this.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        final int fullHeight = BillView.this.frameLayout.getMeasuredHeight();
        frameLayout.getLayoutParams().height = fullHeight;
        frameLayout.getLayoutParams().width = fullWidth;

        BillView.this.fullLayout.getLayoutParams().height = 0;
        BillView.this.fullLayout.getLayoutParams().width = 0;
        BillView.this.fullLayout.setVisibility(VISIBLE);

        ValueAnimator va = ValueAnimator.ofFloat(0, 100);
        va.setDuration(getResources().getInteger(R.integer.animation_bill_start));
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                float scale = value.floatValue()/100;
                if(BillView.this.getLayoutParams()!=null){
                    BillView.this.fullLayout.getLayoutParams().height = (int)(scale * fullHeight);
                    BillView.this.fullLayout.getLayoutParams().width =  (int)(scale * fullWidth);
                }
                BillView.this.fullLayout.requestLayout();
            }
        });
        va.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation){
                BillView.this.topLayout.setVisibility(VISIBLE);
                AlphaAnimation anim = new AlphaAnimation(0,1);
                anim.setDuration(getResources().getInteger(R.integer.animation_bill_mid));
                BillView.this.topLayout.startAnimation(anim);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation a) {}
                    public void onAnimationRepeat(Animation a) {}
                    public void onAnimationEnd(Animation a) {
                        AlphaAnimation anim = new AlphaAnimation(0,1);
                        BillView.this.bottomLayout.setVisibility(VISIBLE);
                        anim.setDuration(getResources().getInteger(R.integer.animation_bill_end));
                        BillView.this.bottomLayout.startAnimation(anim);
                    }
                });
            }
        });
        va.start();
    }

    public void displayBill(){
        this.fullLayout.setVisibility(VISIBLE);
        this.topLayout.setVisibility(VISIBLE);
        this.bottomLayout.setVisibility(VISIBLE);
    }
}
