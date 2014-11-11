package com.epam.jmp;

import java.util.Arrays;
import java.util.Collections;

public class Calculator {

    private CurrencyRatesProvider dataProvider;

    public Calculator(CurrencyRatesProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public Double calculateAggregateFunction(String function) {

        Double[] currencyRates = dataProvider.getCurrencyRates();

        if (currencyRates != null && currencyRates.length > 0) {
            switch (function.toUpperCase()) {
                case "MAX": {
                    return Collections.max(Arrays.asList(currencyRates));
                }
                case "MIN": {
                    return Collections.min(Arrays.asList(currencyRates));
                }
                case "AVG": {
                    return calculateAverage(currencyRates);
                }
                default:
                    throw new IllegalArgumentException("Incorrect function! Use one of the following functions: max, min, avg.");
            }
        } else {
            return null;
        }
    }

    private Double calculateAverage(Double[] rates) {
        Double sum = new Double(0);
        Double result = null;
        for (int i = 0; i < rates.length; i++) {
            sum += rates[i];
        }
        result = sum / rates.length;
        return result;
    }
}