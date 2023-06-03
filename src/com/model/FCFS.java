package model;

public class FCFS extends DiskScheduler{
    @Override
    public int[] simulate() {
        return this.rQ.getRequestQueue();
    }
}
