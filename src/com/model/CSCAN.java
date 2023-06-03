package model;

import java.util.ArrayList;
import java.util.Arrays;

public class CSCAN extends DiskScheduler{
    @Override
    public int[] simulate(boolean towardsLargerValue) {
        int[] queue = this.rQ.getRequestQueue();
        ArrayList<Integer> res = new ArrayList<Integer>();
        int currentIndex = 0;
        Arrays.sort(queue);
        for (int i : queue){
            if (i > this.rQ.getHead()){
                break;
            }
            currentIndex++;
        }
        int j = currentIndex + 1;
        if (currentIndex != queue.length) res.add(queue[currentIndex]);
        else {
            currentIndex = queue.length - 1;
            res.add(0);
        }
        while (j % queue.length != currentIndex){
            int i = j % queue.length;
            if (i == 0) {
                res.add(this.rQ.getCylinder());
                res.add(0);
            }
            j++;
            res.add(queue[i]);
        }
        int[] ret = new int[res.size()];
        for (int i = 0; i < res.size(); i++){
            ret[i] = res.get(i);
        }
        return ret;
    }
}
