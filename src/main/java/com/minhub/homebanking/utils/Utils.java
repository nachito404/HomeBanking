package com.minhub.homebanking.utils;

public final class Utils {

    private Utils() {
    }

    public static int getCVV() {
        return (int) ((Math.random() * (999 - 100)) +100 );}
    public static String getCardNumber() {
        String cardNumber = (int)((Math.random()*(9999 - 1000)) + 1000)
                + "-" +(int)((Math.random()*(9999 - 1000)) +1000)
                + "-" +(int)((Math.random()*(9999 - 1000)) +1000)
                + "-" +(int)((Math.random()*(9999 - 1000)) +1000);
        return cardNumber;
    }
    public static int getAccountNumber() {
        return (int) ((Math.random() * (99999999 - 10000000)) + 10000000);
    }
}
