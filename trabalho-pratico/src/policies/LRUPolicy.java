package policies;

import java.util.*;

/**
 * Implementação da política LRU (Least Recently Used).
 * Remove a página que não foi usada há mais tempo.
 * 
 * Mantém um mapa que rastreia o índice do último acesso de cada página.
 * Quando precisa escolher uma vítima, seleciona a página com o menor
 * índice de último acesso, ou seja, aquela que foi acessada há mais tempo.
 * 
 * @author Sistema Operacional - Trabalho Prático
 * @version 1.0
 */
public class LRUPolicy implements PageReplacementPolicy {
    
    /** Mapa que armazena o índice do último acesso de cada página */
    private Map<Integer, Integer> lastUsed;
    
    /**
     * Construtor da política LRU.
     * Inicializa o mapa que rastreará os últimos acessos.
     */
    public LRUPolicy() {
        this.lastUsed = new HashMap<>();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "LRU";
    }
    
    /**
     * {@inheritDoc}
     * 
     * Na política LRU, remove a página que foi acessada há mais tempo.
     * Analisa todo o histórico de acessos até o índice atual para determinar
     * qual página tem o último acesso mais antigo.
     */
    @Override
    public int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex) {
        // Atualizar tempo de uso de todas as páginas até agora
        for (int i = 0; i <= currentIndex; i++) {
            lastUsed.put(futureRequests[i], i);
        }
        
        // Encontrar a página menos recentemente usada
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
}
