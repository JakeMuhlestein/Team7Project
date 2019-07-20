package edu.byui.myapplication.model;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import static edu.byui.myapplication.model.PayMethod.RewardsType.CASHBACK;
import static edu.byui.myapplication.model.PayMethod.RewardsType.HOTEL;
import static edu.byui.myapplication.model.PayMethod.RewardsType.MILES;
import static edu.byui.myapplication.model.PayMethod.RewardsType.NONE;

public class PayMethodRewardTypeConverter {

    @TypeConverter
    public static PayMethod.RewardsType toRewardsType(int type) {
        if (type == MILES.getCode()) {
            return MILES;
        } else if (type == CASHBACK.getCode()) {
            return CASHBACK;
        } else if (type == HOTEL.getCode()) {
            return HOTEL;
        } else {
            return NONE;
        }
    }

    @TypeConverter
    public static int toInt(PayMethod.RewardsType type) {
        return type.getCode();
    }
}
