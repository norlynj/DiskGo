package model;

import java.util.Random;
import java.util.StringJoiner;

public abstract class DiskScheduler {
    private Step[] steps;
    RequestQueue rQ;
    Random random = new Random();


    public void setRequestQueue(RequestQueue rq) {
        this.rQ = rq;
    }

    public int[] simulate() {
        return this.rQ.getRequestQueue();
    }

    public Step[] getSteps() {
        return steps;
    }





}
