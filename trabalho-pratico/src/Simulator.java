import java.util.*;
import policies.PageReplacementPolicy;

/**
 * Virtual memory paging simulator.
 * Executes page request sequences using different replacement policies.
 */
public class Simulator {
    
    private final int nframes;
    
    /**
     * @param nframes Number of page frames available in RAM
     */
    public Simulator(int nframes) {
        this.nframes = nframes;
    }
    
    /**
     * Simulates memory management for a page request sequence.
     * 
     * @param policy Replacement policy to use
     * @param requests Page request sequence
     * @return Simulation metrics including page faults and swap state
     */
    public SimulationResult simulate(PageReplacementPolicy policy, int[] requests) {
        long startTime = System.currentTimeMillis();
        
        Set<Integer> frames = new LinkedHashSet<>();
        int pageFaults = 0;
        
        for (int i = 0; i < requests.length; i++) {
            int page = requests[i];
            
            if (!frames.contains(page)) {
                pageFaults++;
                
                if (frames.size() < nframes) {
                    frames.add(page);
                    policy.notifyPageAccess(page);
                } else {
                    int victim = policy.selectVictim(frames, requests, i);
                    frames.remove(victim);
                    frames.add(page);
                    policy.notifyPageAccess(page);
                }
            }
        }
        
        long executionTime = (System.currentTimeMillis() - startTime) / 1000;
        Set<Integer> pagesInSwap = calculatePagesInSwap(requests, frames);
        
        return new SimulationResult(policy.getName(), executionTime, pageFaults, pagesInSwap);
    }
    
    private Set<Integer> calculatePagesInSwap(int[] requests, Set<Integer> frames) {
        Set<Integer> allPages = new TreeSet<>();
        for (int page : requests) {
            allPages.add(page);
        }
        
        Set<Integer> pagesInSwap = new TreeSet<>(allPages);
        pagesInSwap.removeAll(frames);
        
        return pagesInSwap;
    }
}
