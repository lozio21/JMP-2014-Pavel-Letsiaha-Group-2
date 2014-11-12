package com.epam.jmp;

import static com.epam.jmp.AggregateFunction.*;

public class Main {

    private static final String DOUBLE_FORMAT = "%.4f%n";

    public static void main(String[] args) {
        CurrencyRatesProvider dataProvider = new PriorbankCurrencyRatesProvider();
        if (dataProvider.updateCurrencyRates("USD", 10)) {
            Double[] rates = dataProvider.getCurrencyRates();
            System.out.println("Currency Rates:");
            for (Double rate : rates) {
                System.out.format(DOUBLE_FORMAT, rate);
            }
            Calculator calculator = new Calculator(dataProvider);
            System.out.format("\nMIN: " + DOUBLE_FORMAT, calculator.calculateAggregateFunction(MIN));
            System.out.format("MAX: " + DOUBLE_FORMAT, calculator.calculateAggregateFunction(MAX));
            System.out.format("AVG: " + DOUBLE_FORMAT, calculator.calculateAggregateFunction(AVG));
        }
    }
}