package policies;

import java.util.*;

/**
 * LRU page replacement policy.
 * Selects the page with the oldest access timestamp as victim.
 */
public class LRUPolicy implements PageReplacementPolicy {
    
    private final Map<Integer, Integer> lastUsed;
    private int accessCounter;
    
    public LRUPolicy() {
        this.lastUsed = new HashMap<>();
        this.accessCounter = 0;
    }
    
    @Override
    public String getName() {
        return "LRU";
    }
    
    @Override
    public int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex) {
        int victim = -1;
        int minTime = Integer.MAX_VALUE;
        
        for (int frame : frames) {
            int time = lastUsed.getOrDefault(frame, -1);
            if (time < minTime) {
                minTime = time;
                victim = frame;
            }
        }
        
        return victim;
    }
    
    @Override
    public void notifyPageAccess(int page) {
        lastUsed.put(page, accessCounter++);
    }
}
