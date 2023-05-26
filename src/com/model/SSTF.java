package model;

public class SSTF extends DiskScheduler{

    public int[] simulate() {
        int[] queue = this.getRequestQueue();
        int[] res = new int[queue.length];
        int currentPosition = this.getHead();
        for (int i = 0; i < this.getRequestQueue().length; i++){
            int min_diff = (currentPosition - queue[i]) >= 0 ? (currentPosition - queue[i]) : (queue[i] - currentPosition);
            int min_index = i;
            for (int j = i+1; j < queue.length; j++){
                int diff = currentPosition - queue[j] >= 0 ? currentPosition - queue[j] : queue[j] - currentPosition;
                if (diff < min_diff){
                    min_diff = diff;
                    min_index = j;
                }
            }
            int temp = queue[i];
            queue[i] = queue[min_index];
            queue[min_index] = temp;
            res[i] = queue[i];
            currentPosition = res[i];
        }
        return res;
    }
}
