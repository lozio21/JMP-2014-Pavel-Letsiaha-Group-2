package com.epam.jmp;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PriorbankCurrencyRatesProvider implements CurrencyRatesProvider {

    private static final String WEB_SERVICE_URL = "http://www.priorbank.by/CurratesExportXml.axd?version=2&channel=3";
    private static final String PARAMETERS_FORMAT = "&iso=%s&from=%s&to=%s";
    private static final String CHARSET = "windows-1251";
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String XPATH_EXPRESSION = "//RATE[@SORT = 1]/SELL";

    private Double[] currencyRates;

    @Override
    public boolean updateCurrencyRates(String currencyCode, int days) {
        try {
            // get strings with current date and date in past
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String currentDate = sdf.format(calendar.getTime());
            calendar.add(Calendar.DATE, -days);
            String dateInPast = sdf.format(calendar.getTime());

            // prepare parameters for URL
            String parameters = String.format(PARAMETERS_FORMAT,
                    URLEncoder.encode(currencyCode, CHARSET),
                    URLEncoder.encode(dateInPast, CHARSET),
                    URLEncoder.encode(currentDate, CHARSET));

            // create new connection using prepared URL
            URLConnection connection = new URL(WEB_SERVICE_URL + parameters).openConnection();

            // parse XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(connection.getInputStream());

            // find necessary values using xPath and update currency rates
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList list = (NodeList) xPath.evaluate(XPATH_EXPRESSION, doc, XPathConstants.NODESET);

            currencyRates = new Double[list.getLength()];

            for (int i = 0; i < list.getLength(); i++) {
                currencyRates[i] = (Double.parseDouble(list.item(i).getTextContent()));
            }

            return true;
        } catch (Exception e) {
            System.err.println(e);
        }
        return false;
    }

    @Override
    public Double[] getCurrencyRates() {
        return currencyRates;
    }
}