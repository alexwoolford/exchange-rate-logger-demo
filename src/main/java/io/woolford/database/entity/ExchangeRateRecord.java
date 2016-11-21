package io.woolford.database.entity;


public class ExchangeRateRecord {

    String currency;
    Double rate;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRateRecord{" +
                "currency='" + currency + '\'' +
                ", rate=" + rate +
                '}';
    }

}
