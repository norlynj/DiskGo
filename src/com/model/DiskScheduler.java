package model;

import java.util.Random;

public abstract class DiskScheduler {
    RequestQueue rQ;
    Random random = new Random();


    public void setRequestQueue(RequestQueue rq) {
        this.rQ = rq;
    }

    public int[] simulate() {
        return this.rQ.getRequestQueue();
    }

}
