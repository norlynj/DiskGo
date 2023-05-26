package model;

import java.util.Random;
import java.util.StringJoiner;

public abstract class DiskScheduler {
    private Step[] steps;
    private int[] requestQueue;
    private int head;
    private int cylinder = 200; // 0-199
    Random random = new Random();


    public void setRequestQueue(int[] requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int[] simulate() {
        return this.requestQueue;
    }

    public Step[] getSteps() {
        return steps;
    }

    public int[] getRequestQueue() {
        return this.requestQueue;
    }

    public int getHead() {
        return head;
    }

    public int getCylinder() {
        return cylinder;
    }

    public int[] randomizeQueue() {

        int size = random.nextInt(41); // Generate random size between 0 and 40

        int[] queue = new int[size];
        for (int i = 0; i < size; i++) {
            queue[i] = random.nextInt(200); // Generate random integer between 0 and 199
        }
        this.requestQueue = queue;
        return queue;
    }

    public int getRandomizeHead() {
        this.head = random.nextInt(200);
        return head;
    }

    public String getQueueAsString() {
        StringJoiner joiner = new StringJoiner(", ");
        for (int number : requestQueue) {
            joiner.add(String.valueOf(number));
        }
        return joiner.toString();
    }
}
