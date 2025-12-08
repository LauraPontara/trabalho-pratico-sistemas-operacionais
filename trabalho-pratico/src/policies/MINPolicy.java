package policies;

import java.util.*;

/**
 * Optimal (MIN) replacement policy.
 * Replaces the page that will not be used for the longest period of time.
 */
public class MINPolicy implements PageReplacementPolicy {
    
    @Override
    public String getName() {
        return "MIN";
    }
    
    /**
     * Scans future requests to find the frame with the furthest next-use index.
     */
    @Override
    public int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex) {
        int victim = -1;
        int farthestUse = -1;
        
        for (int frame : frames) {
            int nextUse = Integer.MAX_VALUE;
            
            for (int i = currentIndex + 1; i < futureRequests.length; i++) {
                if (futureRequests[i] == frame) {
                    nextUse = i;
                    break;
                }
            }
            
            if (nextUse > farthestUse) {
                farthestUse = nextUse;
                victim = frame;
            }
        }
        
        return victim;
    }
}
