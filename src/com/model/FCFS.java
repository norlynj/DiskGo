package model;

public class FCFS extends DiskScheduler{
    public int[] simulate() {
        return this.rQ.getRequestQueue();
    }
}
