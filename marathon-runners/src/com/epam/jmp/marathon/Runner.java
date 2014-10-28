package com.epam.jmp.marathon;

import java.util.Random;

public class Runner implements  Runnable {

    private String name;
    private Marathon marathon;
    private int timeToSleep;
    private RunnerState runnerState = RunnerState.WAITING;

    public Runner(String name, Marathon marathon, int timeToSleep) {
        this.name = name;
        this.marathon = marathon;
        this.timeToSleep = timeToSleep;
    }

    @Override
    public void run() {
        try {
            marathon.getStart().await();
            runnerState = RunnerState.RUNNING;
            Random random = new Random();
            int coveredDistance  = 0;
            while (coveredDistance < marathon.getDistance()){
                if (random.nextBoolean()) {
                    if (timeToSleep > 0) {
                        runnerState = RunnerState.SLEEPING;
                        int sleepTime = random.nextInt(500);
                        timeToSleep -= sleepTime;
                        Thread.sleep(sleepTime);
                    }
                } else {
                    runnerState = RunnerState.RUNNING;
                    coveredDistance += random.nextInt(400);
                    Thread.sleep(200);
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