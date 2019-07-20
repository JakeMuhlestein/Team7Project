package edu.byui.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.sql.Date;

@Entity(tableName = "paymethod")
public class PayMethod {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "pay_type")
    String payType;
    @ColumnInfo(name = "acct_number")
    String acctNumber;
    double balance;

    @ColumnInfo(name = "exp_date")
    @TypeConverters(DateTypeConverter.class)
    Date expDate;

    double points;

    // reward types: miles(1), cashback(2), hotel(3), none(0).
    @ColumnInfo(name = "rewards_type")
    @TypeConverters(PayMethodRewardTypeConverter.class)
    RewardsType rewardsType;

    public enum RewardsType {
        MILES(1),
        CASHBACK(2),
        HOTEL(3),
        NONE(0);

        private int code;

        RewardsType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }


    }
    public PayMethod(String payType, String acctNumber, double balance, double points, Date expDate) {
        this.payType = payType;
        this.acctNumber = acctNumber;
        this.balance = balance;
        this.points = points;
        this.expDate = expDate;
        this.rewardsType = RewardsType.NONE;
    }


    @Ignore
    public PayMethod(String payType, String acctNumber, double balance, double points) {
        this.payType = payType;
        this.acctNumber = acctNumber;
        this.balance = balance;
        this.points = points;
        this.rewardsType = RewardsType.NONE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayType() {
        return payType;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Date getExpDate() {
        return expDate;
    }

    public double getPoints() {
        return points;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addPoints(double points) {
        balance += points;
    }

    public RewardsType getRewardsType() {
        return rewardsType;
    }

    public void setRewardsType(RewardsType rewardsType) {
        this.rewardsType = rewardsType;
    }

    @Override
    public String toString() {
        return "PayMethod{" +
                "id=" + id +
                ", payType='" + payType + '\'' +
                ", acctNumber='" + acctNumber + '\'' +
                ", balance=" + balance +
                ", expDate=" + expDate +
                ", points=" + points +
                ", rewardsType=" + rewardsType +
                '}';
    }

    @Ignore
    public PayMethod() {
        // hack workaround please change.
        //expDate = new Date(2006, 6, 14);
    }

//    // Needs fixing! mt.
//    @Ignore
//    PayMethod(Date expDate) {
//       this.expDate = expDate;
//    }


//    public class PayMethodRewardsType {
//        enum
//    }
}
