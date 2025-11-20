package policies;

import java.util.*;

/**
 * Implementação da política FIFO (First In, First Out).
 * Remove a página que está há mais tempo na memória (a primeira que entrou).
 * 
 * Utiliza uma fila (Queue) para manter a ordem de chegada das páginas.
 * A página no início da fila é sempre a mais antiga e será a primeira
 * a ser removida quando necessário.
 * 
 * @author Sistema Operacional - Trabalho Prático
 * @version 1.0
 */
public class FIFOPolicy implements PageReplacementPolicy {
    
    /** Fila que mantém a ordem de chegada das páginas */
    private Queue<Integer> queue;
    
    /**
     * Construtor da política FIFO.
     * Inicializa a fila que rastreará a ordem de chegada das páginas.
     */
    public FIFOPolicy() {
        this.queue = new LinkedList<>();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "FIFO";
    }
    
    /**
     * {@inheritDoc}
     * 
     * Na política FIFO, sempre remove a página que está há mais tempo
     * na memória, ou seja, a primeira página da fila.
     */
    @Override
    public int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex) {
        // Encontrar a página mais antiga que ainda está nos frames
        for (Integer page : queue) {
            if (frames.contains(page)) {
                return page;
            }
        }
        
        // Se a fila estiver vazia ou inconsistente, retorna o primeiro frame
        return frames.iterator().next();
    }
    
    /**
     * {@inheritDoc}
     * 
     * Registra quando uma nova página é carregada na memória.
     */
    @Override
    public void notifyPageAccess(int page) {
        // Adiciona na fila apenas se a página ainda não estiver nela
        if (!queue.contains(page)) {
            queue.add(page);
        }
    }
}
