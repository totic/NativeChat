package test.chatdemo;

import java.math.BigDecimal;
import java.util.Date;

public class BillMessage {

    private String content;
    private String mAccountNumber;
    private BigDecimal mProductPriceAmount;
    private BigDecimal mTaxesAmount;
    private BigDecimal mTotalAmount;
    private Date mDueDate;


    public String getContent() {
        return content;
    }

    public BillMessage(String content, String accountNumber, BigDecimal productPriceAmount,
                       BigDecimal taxesAmount, BigDecimal totalAmount, Date dueDate) {

        this.content = content;
        this.mAccountNumber = accountNumber;
        this.mProductPriceAmount = productPriceAmount;
        this.mTaxesAmount = taxesAmount;
        this.mTotalAmount = totalAmount;
        this.mDueDate = dueDate;
    }

    public String getAccountNumber() {
        return mAccountNumber;
    }

    public BigDecimal getProductPriceAmount() {
        return mProductPriceAmount;
    }

    public BigDecimal getTaxesAmount() {
        return mTaxesAmount;
    }

    public BigDecimal getTotalAmount() {
        return mTotalAmount;
    }

    public Date getDueDate() {
        return mDueDate;
    }
}
