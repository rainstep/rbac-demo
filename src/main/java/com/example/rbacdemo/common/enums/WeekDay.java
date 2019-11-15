package com.example.rbacdemo.common.enums;


import java.util.Calendar;

public enum  WeekDay {
    SUN(Calendar.SUNDAY), MON(Calendar.MONDAY), TUE(Calendar.TUESDAY),
    WED(Calendar.WEDNESDAY), THU(Calendar.THURSDAY), FRI(Calendar.FRIDAY), SAT(Calendar.SATURDAY);
    private int value;
    WeekDay(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
