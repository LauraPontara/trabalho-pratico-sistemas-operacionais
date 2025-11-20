import java.util.Scanner;
import policies.*;
import policies.PageReplacementPolicy;

/**
 * Classe principal do Simulador de Memória Virtual.
 * 
 * Este programa simula o comportamento de um sistema de memória virtual
 * com paginação, testando 4 políticas diferentes de substituição de páginas:
 * - FIFO (First In, First Out)
 * - RAND (Random)
 * - LRU (Least Recently Used)
 * - MIN (Optimal)
 * 
 * <p><b>Entrada:</b> Lê da entrada padrão (System.in) via redirecionamento</p>
 * <p><b>Saída:</b> Escreve na saída padrão (System.out) via redirecionamento</p>
 * 
 * <p><b>Formato de uso:</b></p>
 * <pre>
 * javac App.java
 * java App < entrada.txt > saida.txt
 * </pre>
 * 
 * @author Sistema Operacional - Trabalho Prático
 * @version 1.0
 */
public class App {
    
    /**
     * Método principal que executa o simulador.
     * 
     * Lê os parâmetros da entrada, calcula valores derivados,
     * processa cada sequência de requisições e simula com as 4 políticas.
     * 
     * @param args Argumentos da linha de comando (não utilizados)
     * @throws Exception Se houver erro na leitura da entrada
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        // 1. Ler configurações
        int M = sc.nextInt();        // Tamanho da RAM
        int V = sc.nextInt();        // Tamanho da memória virtual
        @SuppressWarnings("unused")
        String arch = sc.next();     // Arquitetura
        int P = sc.nextInt();        // Número de páginas virtuais
        
        // 2. Calcular parâmetros derivados
        int Sp = V / P;              // Tamanho da página
        int Nframes = M / Sp;        // Número de frames na RAM
        int swapSize = V - M;        // Tamanho do Swap
        
        // 3. Imprimir parâmetros calculados
        System.out.println(Sp);
        System.out.println(Nframes);
        System.out.println(swapSize);
        System.out.println();
        
        // 4. Ler número de sequências
        int N = sc.nextInt();
        
        // 5. Processar cada sequência
        for (int i = 0; i < N; i++) {
            int R = sc.nextInt();    // Número de requisições
            int[] requests = new int[R];
            
            // Ler requisições
            for (int j = 0; j < R; j++) {
                requests[j] = sc.nextInt();
            }
            
            // Imprimir número da sequência
            System.out.println(i + 1);
            System.out.println();
            
            // Imprimir a sequência de requisições
            for (int j = 0; j < requests.length; j++) {
                System.out.print(requests[j]);
                if (j < requests.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            
            // 6. Criar simulador
            Simulator simulator = new Simulator(Nframes);
            
            // 7. Simular cada política
            PageReplacementPolicy[] policies = {
                new FIFOPolicy(),
                new RANDPolicy(),
                new LRUPolicy(),
                new MINPolicy()
            };
            
            for (PageReplacementPolicy policy : policies) {
                Simulator.SimulationResult result = simulator.simulate(policy, requests);
                
                // Imprimir resultados
                System.out.println(result.policyName);
                System.out.println(result.executionTime);
                System.out.println(result.pageFaults);
                
                // Imprimir páginas no Swap
                if (result.pagesInSwap.isEmpty()) {
                    System.out.println();
                } else {
                    int count = 0;
                    for (int page : result.pagesInSwap) {
                        System.out.print(page);
                        count++;
                        if (count < result.pagesInSwap.size()) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
            }
        }
        
        sc.close();
    }
}
