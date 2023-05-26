package model;

import java.util.ArrayList;
import java.util.Arrays;

public class CLOOK extends DiskScheduler{
    @Override
    public int[] simulate() {
        ArrayList<Integer> res = new ArrayList<Integer>();
        int[] queue = this.getRequestQueue();
        int currentIndex = 0;
        Arrays.sort(queue);
        for (int i : queue) {
            if (i > this.getHead()) {
                break;
            }
            currentIndex++;
        }
        int j = currentIndex + 1;
        if (currentIndex != queue.length) res.add(queue[currentIndex]);
        else {
            currentIndex = queue.length - 1;
            j = 0;
        }
        while (j % queue.length != currentIndex) {
            int i = j % queue.length;
            j++;
            res.add(queue[i]);
        }
        int[] ret = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ret[i] = res.get(i);
        }
        return ret;
    }
}
