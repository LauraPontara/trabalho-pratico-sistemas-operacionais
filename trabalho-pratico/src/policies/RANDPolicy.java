package policies;

import java.util.*;

/**
 * Random (RAND) replacement policy.
 * Selects a victim frame uniformly at random.
 */
public class RANDPolicy implements PageReplacementPolicy {
    
    private final Random random;
    
    public RANDPolicy() {
        this.random = new Random();
    }
    
    @Override
    public String getName() {
        return "RAND";
    }
    
    /**
     * Selects a random frame from the current set.
     */
    @Override
    public int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex) {
        List<Integer> frameList = new ArrayList<>(frames);
        int randomIndex = random.nextInt(frameList.size());
        return frameList.get(randomIndex);
    }
}
