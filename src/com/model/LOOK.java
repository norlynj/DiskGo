package model;

import java.util.ArrayList;
import java.util.Arrays;

public class LOOK extends DiskScheduler{
    @Override
    public int[] simulate(boolean towardsLargerValue) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        int[] queue = this.rQ.getRequestQueue();
        int currentIndex = 0;
        Arrays.sort(queue);
        for (int i : queue) {
            if (i > this.rQ.getHead()) {
                break;
            }
            currentIndex++;
        }

        if (towardsLargerValue) {
            for (int i = currentIndex; i < queue.length; i++) {
                res.add(queue[i]);
            }
            for (int i = currentIndex - 1; i >= 0; i--) {
                res.add(queue[i]);
            }
        } else {
            for (int i = currentIndex - 1; i >= 0; i--) {
                res.add(queue[i]);
            }
            for (int i = currentIndex; i < queue.length; i++) {
                res.add(queue[i]);
            }
        }

        int[] ret = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ret[i] = res.get(i);
        }
        return ret;
    }
}
