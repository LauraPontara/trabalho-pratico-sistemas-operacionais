package policies;

import java.util.*;

/**
 * FIFO page replacement policy.
 * Maintains insertion order and selects the oldest page as victim.
 */
public class FIFOPolicy implements PageReplacementPolicy {
    
    private final Queue<Integer> queue;
    
    public FIFOPolicy() {
        this.queue = new LinkedList<>();
    }
    
    @Override
    public String getName() {
        return "FIFO";
    }
    
    @Override
    public int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex) {
        for (Integer page : queue) {
            if (frames.contains(page)) {
                return page;
            }
        }
        throw new IllegalStateException("Queue inconsistency detected");
    }
    
    @Override
    public void notifyPageAccess(int page) {
        if (!queue.contains(page)) {
            queue.add(page);
        }
    }
}
