package com.epam.jmp.marathon;

import java.util.Random;

public class Runner implements Runnable {

    private static final int SLEEP_LIMIT_PER_ONE_TIME = 500;

    private String name;
    private Marathon marathon;
    private RunnerState runnerState = RunnerState.WAITING;

    public Runner(String name, Marathon marathon) {
        this.name = name;
        this.marathon = marathon;
    }

    @Override
    public void run() {
        try {
            marathon.getStart().await();
            runnerState = RunnerState.RUNNING;
            Random random = new Random();
            int timeToSleep = marathon.getTimeToSleep();
            int coveredDistance = 0;
            while (coveredDistance < marathon.getDistance()) {
                if (random.nextBoolean()) {
                    if (timeToSleep > 0) {
                        runnerState = RunnerState.SLEEPING;
                        int sleepTime = random.nextInt((timeToSleep <= SLEEP_LIMIT_PER_ONE_TIME) ? timeToSleep : SLEEP_LIMIT_PER_ONE_TIME);
                        timeToSleep -= sleepTime;
                        Thread.sleep(sleepTime);
                    }
                } else {
                    runnerState = RunnerState.RUNNING;
                    coveredDistance += 300;
                    Thread.sleep(300);
                }
            }
            if (timeToSleep > 0) {
                runnerState = RunnerState.SLEEPING;
                Thread.sleep(timeToSleep);
            }
            marathon.getFinish().countDown();
            setRunnerState(RunnerState.WAITING);
            marathon.addToResults(name);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    public RunnerState getRunnerState() {
        return runnerState;
    }

    public void setRunnerState(RunnerState runnerState) {
        this.runnerState = runnerState;
    }

    public String getName() {
        return name;
    }
}