package com.epam.jmp;

public interface CurrencyRatesProvider {

    public boolean updateCurrencyRates(String currencyCode, int days);
    public Double[] getCurrencyRates();
}