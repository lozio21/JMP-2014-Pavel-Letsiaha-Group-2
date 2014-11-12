package com.epam.jmp;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {

    @Mock
    private CurrencyRatesProvider dataProvider;
    private Calculator calculator;
    private Double[] testData = new Double[]{10830.0000, 10820.0000, 10810.0000, 10800.0000};

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        calculator = new Calculator(dataProvider);
        when(dataProvider.getCurrencyRates()).thenReturn(testData);
    }

    @Test
    public void testUnsupportedFunction() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("There is no implementation of this function in calculator.");
        calculator.calculateAggregateFunction(AggregateFunction.COUNT);
    }

    @Test
    public void testMaxFunction() {
        Double result = calculator.calculateAggregateFunction(AggregateFunction.MAX);
        Double expected = 10830.0000;
        assertEquals(expected, result);
    }

    @Test
    public void testMinFunction() {
        Double result = calculator.calculateAggregateFunction(AggregateFunction.MIN);
        Double expected = 10800.0000;
        assertEquals(expected, result);
    }

    @Test
    public void testAvgFunction() {
        Double result = calculator.calculateAggregateFunction(AggregateFunction.AVG);
        Double expected = 10815.0000;
        assertEquals(expected, result);
    }

    @Test
    public void testCalculateMethodWhenArrayIsEmpty() {
        when(dataProvider.getCurrencyRates()).thenReturn(new Double[0]);
        Double result = calculator.calculateAggregateFunction(AggregateFunction.MAX);
        assertNull(result);
    }

    @Test
    public void testCalculateMethodWhenArrayIsNull() {
        when(dataProvider.getCurrencyRates()).thenReturn(null);
        Double result = calculator.calculateAggregateFunction(AggregateFunction.MAX);
        assertNull(result);
    }

    @After
    public void testThatGetCurrencyRatesMethodWasCalledOnce() {
        Mockito.verify(dataProvider, Mockito.times(1)).getCurrencyRates();
    }
}