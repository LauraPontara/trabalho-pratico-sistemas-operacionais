# Simulador de Memória Virtual com Paginação

Trabalho prático de Sistemas Operacionais - Simulador de políticas de substituição de páginas.

## 📋 Descrição

Este projeto implementa um simulador de memória virtual com paginação que simula 4 políticas diferentes de substituição de páginas:

- **FIFO** (First In, First Out): Remove a página mais antiga na memória
- **LRU** (Least Recently Used): Remove a página menos recentemente usada
- **RAND** (Random): Remove uma página aleatória
- **MIN/OPT** (Optimal): Remove a página que será usada mais tarde (algoritmo ótimo)

## 🏗️ Estrutura do Projeto

```
trabalho-pratico/
├── src/
│   ├── App.java                          # Classe principal
│   ├── Simulator.java                    # Coordenador das simulações
│   └── policies/
│       ├── PageReplacementPolicy.java    # Interface das políticas
│       ├── FIFOPolicy.java              # Política FIFO
│       ├── LRUPolicy.java               # Política LRU
│       ├── RANDPolicy.java              # Política Random
│       └── MINPolicy.java               # Política MIN/OPT
├── inputs/                               # Arquivos de entrada
├── outputs/                              # Arquivos de saída gerados
├── bin/                                  # Classes compiladas
├── compile.bat                           # Script de compilação
└── run.bat                               # Script de execução
```

## 🚀 Como Usar

### 1. Compilar o projeto

```batch
compile.bat
```

### 2. Executar com um arquivo de entrada

```batch
run.bat entrada_pequena
```

Ou executar manualmente:
```batch
java -cp bin App < inputs\entrada_pequena.txt > outputs\saida_pequena.txt
```

## 📝 Formato da Entrada

```
4096          ← Tamanho da RAM em bytes (M)
16384         ← Tamanho da memória virtual em bytes (V)
x86           ← Arquitetura (x86 ou x64)
16            ← Número total de páginas virtuais (P)
              ← Linha em branco
2             ← Número de sequências a processar (N)
              ← Linha em branco
8             ← Primeira sequência: 8 requisições (R)
0 1 2 3 0 1 4 0   ← Os números das páginas requisitadas
              ← Linha em branco
10            ← Segunda sequência: 10 requisições
0 1 2 0 3 4 5 6 7 0   ← Os números das páginas requisitadas
```

## 📤 Formato da Saída

```
1024          ← Tamanho da página (Sp)
4             ← Número de frames (Nframes)
12288         ← Tamanho do Swap
              ← Linha em branco
1             ← Número da sequência
              ← Linha em branco
0 1 2 3 0 1 4 0   ← Sequência de requisições
FIFO          ← Política
0             ← Tempo em segundos
6             ← Número de page faults
1             ← Página(s) no Swap
RAND
0
5
2
LRU
0
5
2
MIN
0
5
3
```

## 🧮 Cálculos Realizados

O simulador calcula automaticamente:

- **Tamanho da página (Sp)**: `V / P`
- **Número de frames (Nframes)**: `M / Sp`
- **Tamanho do Swap**: `V - M`

## 🔍 Como Funciona

Para cada sequência de requisições, o simulador:

1. **Inicia com memória vazia**
2. **Para cada requisição de página**:
   - Se a página já está na RAM: acesso normal (sem page fault)
   - Se a página NÃO está na RAM:
     - Conta um **page fault**
     - Se há espaço livre: carrega a página
     - Se a RAM está cheia: aplica a política de substituição para escolher uma vítima
3. **Registra**:
   - Tempo de execução
   - Número de page faults
   - Páginas que ficaram no Swap

## 📚 Políticas Implementadas

### FIFO (First In, First Out)
- Usa uma fila (Queue)
- Remove sempre a página que entrou primeiro na memória

### LRU (Least Recently Used)
- Rastreia quando cada página foi acessada
- Remove a página que não é usada há mais tempo

### RAND (Random)
- Escolhe aleatoriamente uma página para remover
- Usa `java.util.Random`

### MIN/OPT (Optimal)
- "Olha para o futuro" nas requisições
- Remove a página que será usada mais tarde
- É o algoritmo teoricamente ótimo (mas impraticável na prática)

## 🧪 Arquivos de Teste

O projeto inclui arquivos de entrada de exemplo:

- `entrada_pequena.txt`: Teste básico com 2 sequências
- `entrada_media.txt`: Teste maior com mais páginas

## 👥 Autores

Trabalho desenvolvido para a disciplina de Sistemas Operacionais.
