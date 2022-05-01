package com.ahmedriyadh.oauth1.utils;

import java.util.Random;

/**
 * This class generate timestamp and nonce
 */
public class TimestampServiceImpl implements TimestampService {
    private Timer timer;

    /**
     * Default constructor.
     */
    public TimestampServiceImpl() {
        timer = new Timer();
    }


    public String getNonce() {
        Long ts = getTs();
        return String.valueOf(ts + timer.getRandomInteger());
    }

    public String getTimestampInSeconds() {
        return String.valueOf(getTs());
    }

    private Long getTs() {
        return timer.getMilis() / 1000;
    }

    void setTimer(Timer timer) {
        this.timer = timer;
    }

    /**
     * Inner class that uses {@link System} for generating the timestamps.
     */
    static class Timer {
        private final Random rand = new Random();

        Long getMilis() {
            return System.currentTimeMillis();
        }

        Integer getRandomInteger() {
            return rand.nextInt();
        }
    }

}