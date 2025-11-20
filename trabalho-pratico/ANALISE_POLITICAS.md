# Análise das Políticas de Substituição de Páginas

## Visão Geral

Este documento apresenta uma análise detalhada das quatro políticas de substituição de páginas implementadas no simulador.

## 1. FIFO (First In, First Out)

### Descrição
Remove a página que está há mais tempo na memória, independentemente de seu uso recente.

### Implementação
- **Estrutura de Dados**: Queue (Fila)
- **Complexidade**: O(1) para inserção, O(n) para seleção da vítima no pior caso

### Vantagens
- ✅ Simples de implementar
- ✅ Baixo overhead computacional
- ✅ Justa (todas as páginas têm tempo igual)

### Desvantagens
- ❌ Ignora o princípio de localidade
- ❌ Pode remover páginas frequentemente usadas
- ❌ Sofre da "Anomalia de Belady" (mais frames → mais page faults em alguns casos)

### Casos de Uso
Melhor para workloads com acesso sequencial sem reuso.

---

## 2. LRU (Least Recently Used)

### Descrição
Remove a página que não foi usada há mais tempo, baseando-se no histórico de acessos.

### Implementação
- **Estrutura de Dados**: HashMap<Página, ÚltimoAcesso>
- **Complexidade**: O(n) para seleção da vítima, O(1) para atualização

### Vantagens
- ✅ Respeita o princípio de localidade temporal
- ✅ Bom desempenho em padrões de acesso comuns
- ✅ Não sofre da Anomalia de Belady

### Desvantagens
- ❌ Overhead de manter timestamps ou ordem de acesso
- ❌ Difícil de implementar eficientemente em hardware
- ❌ Pode ser enganado por varreduras sequenciais longas

### Casos de Uso
Excelente para workloads com forte localidade temporal (loops, funções recursivas).

---

## 3. RAND (Random)

### Descrição
Escolhe aleatoriamente uma página para remover, sem considerar histórico ou futuro.

### Implementação
- **Estrutura de Dados**: Random + Lista
- **Complexidade**: O(1) para seleção da vítima

### Vantagens
- ✅ Extremamente simples
- ✅ Overhead mínimo
- ✅ Não tem worst-case patológico
- ✅ Surpreendentemente competitiva em alguns cenários

### Desvantagens
- ❌ Performance imprevisível
- ❌ Ignora completamente padrões de acesso
- ❌ Não garante otimalidade

### Casos de Uso
Baseline para comparação, sistemas com padrões de acesso totalmente aleatórios.

---

## 4. MIN/OPT (Optimal)

### Descrição
Remove a página que será usada mais tarde no futuro. É o algoritmo teoricamente ótimo.

### Implementação
- **Estrutura de Dados**: Análise do array futuro de requisições
- **Complexidade**: O(n × m) onde n = frames, m = requisições futuras

### Vantagens
- ✅ Minimiza o número de page faults (provadamente ótimo)
- ✅ Serve como limite inferior teórico
- ✅ Útil para análise comparativa

### Desvantagens
- ❌ **IMPRATICÁVEL**: Requer conhecimento do futuro
- ❌ Complexidade computacional alta
- ❌ Apenas teórico/acadêmico

### Casos de Uso
Análise teórica, estabelecer benchmark para outras políticas.

---

## Comparação de Desempenho

### Métricas Importantes

1. **Taxa de Page Faults**: Número de faltas / Total de requisições
2. **Tempo de Execução**: Overhead computacional da política
3. **Complexidade de Implementação**: Facilidade de implementar em hardware/software

### Ordem Esperada de Performance (Page Faults)

Para workloads típicos com localidade:

```
MIN < LRU ≤ FIFO < RAND
```

**Observação**: Em casos específicos, RAND pode superar FIFO!

### Quando Cada Política É Melhor

| Política | Melhor Para |
|----------|-------------|
| **FIFO** | Acesso sequencial, simplicidade crítica |
| **LRU** | Localidade temporal forte, workloads gerais |
| **RAND** | Testes, sistemas simples, padrões imprevisíveis |
| **MIN** | Análise teórica, limite inferior de performance |

---

## Conceitos Importantes

### Anomalia de Belady
- Fenômeno onde aumentar o número de frames pode **aumentar** page faults
- Ocorre apenas em algoritmos que não seguem a propriedade de stack (ex: FIFO)
- LRU e MIN não sofrem deste problema

### Princípio de Localidade
- **Localidade Temporal**: Páginas recentemente acessadas tendem a ser acessadas novamente
- **Localidade Espacial**: Páginas próximas tendem a ser acessadas juntas
- LRU explora localidade temporal
- Pré-fetching explora localidade espacial

### Working Set
- Conjunto de páginas ativamente usadas por um processo
- Políticas boas mantêm o working set na memória
- LRU aproxima bem o conceito de working set

---

## Resultados do Simulador

### Interpretação dos Resultados

Ao executar o simulador, observe:

1. **Page Faults**: MIN deve ter o menor número. LRU geralmente é o segundo melhor.
2. **Tempo de Execução**: RAND e FIFO devem ser mais rápidos que LRU e MIN.
3. **Páginas no Swap**: Indica quais páginas não estão na RAM ao final.

### Exemplo de Análise

```
Sequência: 0 1 2 3 0 1 4 0
Frames: 4

FIFO: 6 page faults  → Remove páginas antigas
LRU:  5 page faults  → Detecta reuso de 0 e 1
MIN:  5 page faults  → Ótimo teórico
RAND: 5-7 (variável) → Depende do acaso
```

---

## Implementação no Simulador

### Arquitetura

```
App.java (Main)
    ↓
Simulator.java
    ↓
PageReplacementPolicy (Interface)
    ↓
┌─────────┬─────────┬─────────┬─────────┐
FIFOPolicy LRUPolicy RANDPolicy MINPolicy
```

### Fluxo de Execução

1. **Leitura**: App lê configurações e sequências
2. **Cálculo**: Deriva Sp, Nframes, SwapSize
3. **Simulação**: Para cada política:
   - Inicializa frames vazios
   - Processa cada requisição
   - Conta page faults
   - Aplica política quando necessário
4. **Saída**: Imprime resultados formatados

---

## Referências

- Silberschatz, A., Galvin, P. B., & Gagne, G. (2018). *Operating System Concepts* (10th ed.)
- Tanenbaum, A. S., & Bos, H. (2014). *Modern Operating Systems* (4th ed.)
- Belady, L. A. (1966). "A Study of Replacement Algorithms for Virtual-Storage Computer"

---

## Conclusão

Cada política tem seu lugar:
- **Sistemas Reais**: LRU ou aproximações (Clock, Second-Chance)
- **Sistemas Simples**: FIFO por simplicidade
- **Análise Teórica**: MIN como baseline
- **Baseline de Comparação**: RAND

O simulador permite compreender empiricamente as diferenças entre estas abordagens fundamentais do gerenciamento de memória virtual.
