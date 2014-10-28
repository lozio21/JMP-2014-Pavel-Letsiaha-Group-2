package com.epam.jmp.marathon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    // Minimal valid values
    private static final int MIN_NUMBER_OF_RUNNERS = 2;
    private static final int MIN_DISTANCE = 1000;
    private static final int MIN_TIME_TO_SLEEP = 1000;

    // Messages
    private static final String MSG_ENTER_NUMBER_OF_RUNNERS = "Enter number of runners:";
    private static final String MSG_ENTER_MIN_DISTANCE = "Enter distance:";
    private static final String MSG_ENTER_TIME_TO_SLEEP_1 = "Enter time to sleep for ";
    private static final String MSG_ENTER_TIME_TO_SLEEP_2 = " runner:";
    private static final String VALIDATION_MSG_NUMBER_OF_RUNNERS = "Number of runners should be greater than ";
    private static final String VALIDATION_MSG_DISTANCE = "Distance should be greater than ";
    private static final String VALIDATION_MSG_TIME_TO_SLEEP = "Time to sleep should be greater than ";

    public static void main(String[] args) {

        int numberOfRunners = 0;
        int distance = 0;
        int[] timeToSleep;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            // read number of runners
            System.out.println(MSG_ENTER_NUMBER_OF_RUNNERS);
            numberOfRunners = readIntValueFromConsole(br, MIN_NUMBER_OF_RUNNERS, VALIDATION_MSG_NUMBER_OF_RUNNERS);

            // read distance
            System.out.println(MSG_ENTER_MIN_DISTANCE);
            distance = readIntValueFromConsole(br, MIN_DISTANCE, VALIDATION_MSG_DISTANCE);

            timeToSleep = new int[numberOfRunners];
            StringBuilder sb = new StringBuilder();

            // read time to sleep for each runner
            for (int i = 0; i < numberOfRunners; i++) {
                sb.append(MSG_ENTER_TIME_TO_SLEEP_1);
                sb.append(i + 1);
                sb.append(MSG_ENTER_TIME_TO_SLEEP_2);
                System.out.println(sb.toString());
                sb.setLength(0);
                timeToSleep[i] = readIntValueFromConsole(br, MIN_TIME_TO_SLEEP, VALIDATION_MSG_TIME_TO_SLEEP);
            }

            // create new marathon and begin competition
            Marathon marathon = new Marathon(numberOfRunners, distance, timeToSleep);
            marathon.beginCompetition();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static int readIntValueFromConsole(BufferedReader br, int minValue, String validationMsg) throws IOException {
        int value = 0;
        do {
            try{
                value = Integer.parseInt(br.readLine());
                if (value < minValue) {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                System.err.println(validationMsg + minValue);
            }
        } while (value < minValue);
        return value;
    }
}