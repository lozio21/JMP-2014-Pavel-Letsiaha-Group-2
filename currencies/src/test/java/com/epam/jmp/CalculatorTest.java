package com.epam.jmp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {

    @Mock
    private CurrencyRatesProvider dataProvider;
    private Calculator calculator;
    private Double[] testData = new Double[]{10830.0000, 10820.0000, 10810.0000, 10800.0000};

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        calculator = new Calculator(dataProvider);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectFunction() {
        when(dataProvider.getCurrencyRates()).thenReturn(testData);
        calculator.calculateAggregateFunction("count");
    }

    @Test
    public void testMaxFunction() {
        when(dataProvider.getCurrencyRates()).thenReturn(testData);
        Double expected = 10830.0000;
        Double result = calculator.calculateAggregateFunction("max");
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testMinFunction() {
        when(dataProvider.getCurrencyRates()).thenReturn(testData);
        Double expected = 10800.0000;
        Double result = calculator.calculateAggregateFunction("min");
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testAvgFunction() {
        when(dataProvider.getCurrencyRates()).thenReturn(testData);
        Double expected = 10815.0000;
        Double result = calculator.calculateAggregateFunction("avg");
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testCalculateMethodWhenArrayIsEmpty() {
        when(dataProvider.getCurrencyRates()).thenReturn(new Double[0]);
        Double result = calculator.calculateAggregateFunction("max");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void testCalculateMethodWhenArrayIsNull() {
        when(dataProvider.getCurrencyRates()).thenReturn(null);
        Double result = calculator.calculateAggregateFunction("max");
        assertThat(result, is(nullValue()));
    }

    @After
    public void testThatGetCurrencyRatesMethodWasCalledOnce() {
        Mockito.verify(dataProvider, Mockito.times(1)).getCurrencyRates();
    }
}