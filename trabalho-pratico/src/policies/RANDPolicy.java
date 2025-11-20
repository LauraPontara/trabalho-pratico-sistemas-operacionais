package policies;

import java.util.*;

/**
 * Implementação da política RAND (Random).
 * Remove uma página aleatória da memória quando necessário.
 * 
 * Esta política não considera histórico de acessos nem previsões futuras.
 * A escolha da vítima é completamente aleatória entre as páginas presentes
 * na memória. Apesar de simples, serve como baseline para comparação com
 * outras políticas mais sofisticadas.
 * 
 * @author Sistema Operacional - Trabalho Prático
 * @version 1.0
 */
public class RANDPolicy implements PageReplacementPolicy {
    
    /** Gerador de números aleatórios */
    private Random random;
    
    /**
     * Construtor da política RAND.
     * Inicializa o gerador de números aleatórios.
     */
    public RANDPolicy() {
        this.random = new Random();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "RAND";
    }
    
    /**
     * {@inheritDoc}
     * 
     * Na política RAND, escolhe aleatoriamente uma página para remover.
     * Não considera nenhum critério de otimização.
     */
    @Override
    public int selectVictim(Set<Integer> frames, int[] futureRequests, int currentIndex) {
        // Converter para lista para poder acessar por índice
        List<Integer> frameList = new ArrayList<>(frames);
        
        // Selecionar aleatoriamente
        int randomIndex = random.nextInt(frameList.size());
        return frameList.get(randomIndex);
    }
}
