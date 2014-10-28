package com.epam.jmp.marathon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Marathon {

    private static final String RUNNER_NAME = "r";

    private int numberOfRunners;
    private int distance;
    private int[] timeToSleep;

    private CountDownLatch start;
    private CountDownLatch finish;

    private List<Runner> members;
    private List<String> results;

    public Marathon(int numberOfRunners, int numberOfDays, int[] timeToSleep) {
        this.numberOfRunners = numberOfRunners;
        this.distance = numberOfDays;
        this.timeToSleep = timeToSleep;
    }

    public void beginCompetition() {
        members = new ArrayList<Runner>();
        results = Collections.synchronizedList(new ArrayList<String>(numberOfRunners));
        start = new CountDownLatch(1);
        finish = new CountDownLatch(numberOfRunners); // we'll wait for all runners

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfRunners; i++) {
            sb.append(RUNNER_NAME);
            sb.append(i + 1);
            Runner runner = new Runner(sb.toString(), this, timeToSleep[i]);
            sb.setLength(0);
            members.add(runner);
            new Thread(runner).start();
        }

        // start the marathon
        start.countDown();

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // print names of marathon members
        for (Runner runner : members) {
            System.out.print(runner.getName() + "\t");
        }
        System.out.println();

        // print state of each runner through the certain period of time
        Runnable marathonWatcher = new Runnable() {
            @Override
            public void run() {
                for (Runner r : members) {
                    System.out.print(r.getRunnerState() + "\t");
                }
                System.out.println();
            }
        };

        scheduledExecutorService.scheduleAtFixedRate(marathonWatcher, 0, 200, TimeUnit.MILLISECONDS);

        try {
            // waiting for all members
            finish.await();
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        scheduledExecutorService.shutdown(); // stop observation

        for (String name : results) {
            System.out.println((results.indexOf(name) + 1) + " place - " + name);
        }
    }

    public void addToResults(String name){
        if (results != null) {
            results.add(name);
        }
    }

    public int getDistance() {
        return distance;
    }

    public CountDownLatch getStart() {
        return start;
    }

    public CountDownLatch getFinish() {
        return finish;
    }
}