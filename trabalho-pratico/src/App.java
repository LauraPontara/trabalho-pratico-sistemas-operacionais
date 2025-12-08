import java.util.Scanner;
import policies.*;

/**
 * Virtual Memory Simulator.
 * Tests FIFO, RAND, LRU, and MIN page replacement policies.
 */
public class App {
    
    private static final PageReplacementPolicy[] POLICIES = {
        new FIFOPolicy(),
        new RANDPolicy(),
        new LRUPolicy(),
        new MINPolicy()
    };
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        SimulationConfig config = parseConfiguration(sc);
        printSystemParameters(config);
        
        int numSequences = sc.nextInt();
        
        for (int i = 0; i < numSequences; i++) {
            processSequence(sc, config, i + 1);
        }
        
        sc.close();
    }
    
    /**
     * Parses initial configuration from input stream.
     * 
     * @param sc Input scanner
     * @return Configuration object with system parameters
     */
    private static SimulationConfig parseConfiguration(Scanner sc) {
        int ramSize = sc.nextInt();
        int virtualMemorySize = sc.nextInt();
        
        // Architecture token is read to respect input format but intentionally ignored.
        // Virtual memory size (V) is already provided numerically above.
        sc.next();
        
        int numPages = sc.nextInt();
        
        int pageSize = virtualMemorySize / numPages;
        int numFrames = ramSize / pageSize;
        int swapSize = virtualMemorySize - ramSize;
        
        return new SimulationConfig(pageSize, numFrames, swapSize);
    }
    
    private static void printSystemParameters(SimulationConfig config) {
        System.out.println(config.pageSize);
        System.out.println(config.numFrames);
        System.out.println(config.swapSize);
        System.out.println();
    }
    
    private static void processSequence(Scanner sc, SimulationConfig config, int sequenceNumber) {
        int numRequests = sc.nextInt();
        int[] requests = new int[numRequests];
        
        for (int i = 0; i < numRequests; i++) {
            requests[i] = sc.nextInt();
        }
        
        System.out.println(sequenceNumber);
        System.out.println();
        printRequestSequence(requests);
        
        Simulator simulator = new Simulator(config.numFrames);
        
        for (PageReplacementPolicy policy : POLICIES) {
            SimulationResult result = simulator.simulate(policy, requests);
            printSimulationReport(result);
        }
    }
    
    private static void printRequestSequence(int[] requests) {
        for (int i = 0; i < requests.length; i++) {
            System.out.print(requests[i]);
            if (i < requests.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
    
    /**
     * Prints simulation results in the required output format.
     * 
     * @param result Simulation result to print
     */
    private static void printSimulationReport(SimulationResult result) {
        System.out.println(result.policyName());
        System.out.println(result.executionTime());
        System.out.println(result.pageFaults());
        
        if (result.pagesInSwap().isEmpty()) {
            System.out.println();
        } else {
            int count = 0;
            for (int page : result.pagesInSwap()) {
                System.out.print(page);
                count++;
                if (count < result.pagesInSwap().size()) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Internal configuration holder.
     */
    private static record SimulationConfig(int pageSize, int numFrames, int swapSize) {
    }
}
