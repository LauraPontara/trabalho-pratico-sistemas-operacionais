import java.util.*;
import policies.PageReplacementPolicy;

/**
 * Simulador de memória virtual com paginação.
 * 
 * Esta classe é responsável por simular o comportamento do gerenciamento de
 * memória virtual com diferentes políticas de substituição de páginas.
 * Para cada sequência de requisições, calcula:
 * - Número de page faults
 * - Tempo de execução
 * - Páginas que ficaram no Swap (não carregadas na RAM)
 * 
 * @author Sistema Operacional - Trabalho Prático
 * @version 1.0
 */
public class Simulator {
    
    /** Número de frames disponíveis na RAM */
    private int nframes;
    
    /**
     * Construtor do simulador.
     * 
     * @param nframes Número de frames (páginas) que cabem na RAM
     */
    public Simulator(int nframes) {
        this.nframes = nframes;
    }
    
    /**
     * Simula o gerenciamento de memória para uma sequência de requisições.
     * 
     * Para cada requisição de página:
     * - Se a página já está na RAM: acesso normal (hit)
     * - Se a página NÃO está na RAM: page fault
     *   - Se há espaço livre: carrega a página
     *   - Se a RAM está cheia: aplica política para escolher vítima e substitui
     * 
     * @param policy Política de substituição a ser usada
     * @param requests Array com a sequência de requisições de páginas
     * @return Resultado da simulação contendo métricas e estatísticas
     */
    public SimulationResult simulate(PageReplacementPolicy policy, int[] requests) {
        long startTime = System.currentTimeMillis();
        
        Set<Integer> frames = new LinkedHashSet<>();
        int pageFaults = 0;
        
        for (int i = 0; i < requests.length; i++) {
            int page = requests[i];
            
            if (!frames.contains(page)) {
                // Page Fault!
                pageFaults++;
                
                if (frames.size() < nframes) {
                    // Há espaço livre - carrega diretamente
                    frames.add(page);
                    policy.notifyPageAccess(page);
                } else {
                    // RAM cheia - precisa substituir
                    int victim = policy.selectVictim(frames, requests, i);
                    frames.remove(victim);
                    frames.add(page);
                    policy.notifyPageAccess(page);
                }
            }
        }
        
        long endTime = System.currentTimeMillis();
        long executionTime = (endTime - startTime) / 1000; // em segundos
        
        // Calcular páginas no Swap (páginas referenciadas mas não carregadas na RAM no final)
        Set<Integer> pagesInSwap = new TreeSet<>();
        Set<Integer> allPages = new TreeSet<>();
        for (int page : requests) {
            allPages.add(page);
        }
        
        // Páginas no swap são aquelas que foram referenciadas mas não estão nos frames
        for (int page : allPages) {
            if (!frames.contains(page)) {
                pagesInSwap.add(page);
            }
        }
        
        return new SimulationResult(policy.getName(), executionTime, pageFaults, pagesInSwap);
    }
    
    /**
     * Classe que encapsula os resultados de uma simulação.
     * Contém todas as métricas coletadas durante a execução.
     */
    public static class SimulationResult {
        /** Nome da política utilizada */
        public String policyName;
        
        /** Tempo de execução em segundos */
        public long executionTime;
        
        /** Número total de page faults ocorridos */
        public int pageFaults;
        
        /** Conjunto de páginas que ficaram no Swap (não estão na RAM) */
        public Set<Integer> pagesInSwap;
        
        /**
         * Construtor do resultado da simulação.
         * 
         * @param policyName Nome da política utilizada
         * @param executionTime Tempo de execução em segundos
         * @param pageFaults Número de page faults
         * @param pagesInSwap Páginas no Swap
         */
        public SimulationResult(String policyName, long executionTime, int pageFaults, Set<Integer> pagesInSwap) {
            this.policyName = policyName;
            this.executionTime = executionTime;
            this.pageFaults = pageFaults;
            this.pagesInSwap = pagesInSwap;
        }
    }
}
