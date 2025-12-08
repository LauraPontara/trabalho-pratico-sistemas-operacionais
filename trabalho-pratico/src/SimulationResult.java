import java.util.*;

/**
 * Immutable result data from a memory simulation run.
 * 
 * @param policyName Name of the replacement policy used
 * @param executionTime Execution time in seconds
 * @param pageFaults Total number of page faults
 * @param pagesInSwap Pages not loaded in RAM at simulation end
 */
public record SimulationResult(
    String policyName,
    long executionTime,
    int pageFaults,
    Set<Integer> pagesInSwap
) {
}
