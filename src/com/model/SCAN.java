package model;

import java.util.ArrayList;
import java.util.Arrays;

public class SCAN extends DiskScheduler{
    @Override
    public int[] simulate() {
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

        for (int i = currentIndex; i < queue.length; i++) res.add(queue[i]);
        if (currentIndex != 0 && currentIndex != queue.length) res.add(this.rQ.getCylinder());
        for (int i = currentIndex - 1; i >= 0; i--) res.add(queue[i]);

        int[] ret = new int[res.size()];
        for (int i = 0; i < res.size(); i++){
            ret[i] = res.get(i);
        }
        return ret;
    }
}
