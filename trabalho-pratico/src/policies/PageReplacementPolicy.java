package policies;

import java.util.Set;

/**
 * Interface que define o contrato para políticas de substituição de páginas.
 * Cada política implementa uma estratégia diferente para escolher qual página
 * remover da memória RAM quando ela está cheia.
 * 
 * @author Sistema Operacional - Trabalho Prático
 * @version 1.0
 */
public interface PageReplacementPolicy {
    
    /**
     * Retorna o nome da política de substituição.
     * 
     * @return Nome da política (ex: "FIFO", "LRU", "RAND", "MIN")
     */
    String getName();
    
    /**
     * Seleciona a página vítima para ser removida da memória.
     * 
     * @param frames Conjunto de páginas atualmente carregadas na RAM
     * @param futureRequests Array completo de requisições de páginas
     * @param currentIndex Índice da requisição atual no array
     * @return Número da página que deve ser removida
     */
    int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex);
    
    /**
     * Notifica a política que uma página foi acessada.
     * Usado por políticas que precisam rastrear acessos (como FIFO e LRU).
     * 
     * @param page Número da página acessada
     */
    default void notifyPageAccess(int page) {
        // Implementação padrão vazia - políticas que precisam podem sobrescrever
    }
}
