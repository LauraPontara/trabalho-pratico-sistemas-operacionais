package policies;

import java.util.Set;

/**
 * Defines the contract for page replacement strategies.
 */
public interface PageReplacementPolicy {
    
    /**
     * Returns the identifier of the policy.
     * 
     * @return Policy name
     */
    String getName();
    
    /**
     * Identifies the frame to be replaced based on the policy rules.
     * 
     * @param frames Pages currently loaded in RAM
     * @param futureRequests Complete page request sequence
     * @param currentIndex Current position in the request sequence
     * @return Page number to be evicted
     */
    int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex);
    
    /**
     * Notifies the policy that a page has been accessed.
     * Useful for history-based policies.
     * 
     * @param page Page number accessed
     */
    default void notifyPageAccess(int page) {
    }
}
