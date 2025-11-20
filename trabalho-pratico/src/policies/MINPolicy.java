package policies;

import java.util.*;

/**
 * Implementação da política MIN/OPT (Optimal).
 * Remove a página que será usada mais tarde no futuro.
 * 
 * Esta é a política teoricamente ótima, pois minimiza o número de page faults.
 * Funciona "olhando para o futuro" nas requisições e escolhendo remover
 * a página que demorará mais para ser requisitada novamente.
 * 
 * Na prática, esta política é impossível de implementar em um sistema real,
 * pois não podemos prever o futuro. É usada apenas para comparação e estabelecer
 * um limite inferior teórico para o número de page faults.
 * 
 * @author Sistema Operacional - Trabalho Prático
 * @version 1.0
 */
public class MINPolicy implements PageReplacementPolicy {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "MIN";
    }
    
    /**
     * {@inheritDoc}
     * 
     * Na política MIN/OPT, remove a página que será usada mais tarde no futuro.
     * Para cada página na memória, procura quando ela será requisitada novamente.
     * A página que for ser requisitada mais tarde (ou nunca mais) é escolhida.
     */
    @Override
    public int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex) {
        int victim = -1;
        int farthestUse = -1;
        
        for (int frame : frames) {
            // Procurar quando esta página será usada novamente
            int nextUse = Integer.MAX_VALUE;
            
            for (int i = currentIndex + 1; i < futureRequests.length; i++) {
                if (futureRequests[i] == frame) {
                    nextUse = i;
                    break;
                }
            }
            
            // Escolher a página que será usada mais tarde
            // Se nextUse = Integer.MAX_VALUE, a página nunca mais será usada
            if (nextUse > farthestUse) {
                farthestUse = nextUse;
                victim = frame;
            }
        }
        
        return victim;
    }
}
