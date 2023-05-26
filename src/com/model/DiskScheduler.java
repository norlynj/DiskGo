package model;

public abstract class DiskScheduler {
    private Step[] steps;
    private int[] requestQueue;
    private int head;
    private int cylinder = 200; // 0-199


    public void setRequestQueue(int[] requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int[] simulate() {
        return requestQueue;
    }

    public Step[] getSteps() {
        return steps;
    }

    public int[] getRequestQueue() {
        return requestQueue;
    }

    public int getHead() {
        return head;
    }

    public int getCylinder() {
        return cylinder;
    }
}
