package model;

public class FCFS extends DiskScheduler{
    @Override
    public int[] simulate(boolean towardsLargestValue) {
        return this.rQ.getRequestQueue();
    }
}
