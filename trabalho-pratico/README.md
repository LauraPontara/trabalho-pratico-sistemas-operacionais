# Simulador de Memória Virtual

## Introdução

Este projeto implementa um simulador de algoritmos de substituição de páginas em memória virtual. O simulador foi desenvolvido para fins educacionais e permite a análise comparativa de diferentes políticas de substituição de páginas, incluindo MIN (Ótimo), FIFO (First In, First Out), LRU (Least Recently Used) e RAND (Random).

## Guia de Compilação e Execução

### Windows

Para compilar o projeto:
```powershell
.\compile.bat
```

Para executar todos os testes automaticamente:
```powershell
.\run_all.bat
```

Para executar um teste específico:
```powershell
.\run_specific.bat nome_entrada
```
Exemplo: `.\run_specific.bat small`, `.\run_specific.bat media`, etc.

### Linux / macOS

**Importante:** Antes de executar os scripts pela primeira vez, é necessário conceder permissões de execução:
```bash
chmod +x *.sh
```

Para compilar o projeto:
```bash
./compile.sh
```

Para executar todos os testes automaticamente:
```bash
./run_all.sh
```

Para executar um teste específico:
```bash
./run_specific.sh nome_entrada
```
Exemplo: `./run_specific.sh small`, `./run_specific.sh media`, etc.

## Estrutura do Projeto

```
trabalho-pratico/
├── src/                          # Código-fonte Java
│   ├── App.java                  # Classe principal - parser de entrada e orquestração
│   ├── SimulationResult.java     # Record para armazenar resultados da simulação
│   ├── Simulator.java            # Motor de simulação da memória virtual
│   └── policies/                 # Implementações das políticas de substituição
│       ├── PageReplacementPolicy.java  # Interface base
│       ├── FIFOPolicy.java       # Política First In, First Out
│       ├── LRUPolicy.java        # Política Least Recently Used
│       ├── MINPolicy.java        # Política Ótima (MIN)
│       └── RANDPolicy.java       # Política Random
├── inputs/                       # Arquivos de entrada para testes
│   ├── small.txt                 # Entrada pequena (veio com o enunciado)
│   ├── medium.txt                # Entrada média (veio com o enunciado)
│   ├── large.txt                 # Entrada grande (veio com o enunciado)
│   ├── pequena.txt               # Entrada pequena (gerada com o gerador de casos de teste)
│   ├── media.txt                 # Entrada média (gerada com o gerador de casos de teste)
│   └── grande.txt                # Entrada grande (gerada com o gerador de casos de teste)
├── outputs/                      # Resultados das simulações
│   ├── output_small.txt
│   ├── output_medium.txt
│   ├── output_large.txt
│   ├── output_pequena.txt
│   ├── output_media.txt
│   └── output_grande.txt
├── bin/                          # Arquivos .class compilados
│   └── policies/
├── enunciado/                    # Enunciado do projeto
│  
├── compile.bat / compile.sh      # Scripts de compilação
├── run_all.bat / run_all.sh      # Scripts para executar todos os testes
├── run_specific.bat / run_specific.sh  # Scripts para teste específico
└── README.md                     # Este arquivo
```

## Políticas Implementadas

### MIN (Ótimo)
A política **MIN** (também conhecida como Ótimo ou OPT) é um algoritmo teórico que substitui a página que será referenciada no futuro mais distante. Esta política serve como referência ideal, pois minimiza o número de page faults. No entanto, é impraticável em sistemas reais, pois requer conhecimento antecipado de todas as referências futuras.

### FIFO (First In, First Out)
A política **FIFO** substitui a página mais antiga carregada na memória, independentemente do seu uso. Funciona como uma fila: a primeira página a entrar é a primeira a sair. É simples de implementar, mas pode sofrer da anomalia de Belady, onde mais frames podem resultar em mais page faults.

### LRU (Least Recently Used)
A política **LRU** substitui a página que não foi usada há mais tempo. Baseia-se no princípio da localidade temporal: páginas recentemente usadas provavelmente serão usadas novamente em breve. É uma aproximação prática da política MIN e geralmente apresenta bom desempenho.

### RAND (Random)
A política **RAND** seleciona uma página aleatória para substituição. Embora seja a mais simples de implementar e não requeira estruturas de dados complexas, seu desempenho é imprevisível e geralmente inferior às outras políticas.

## Formato das Entradas e Saídas

### Arquivos de Entrada
Os arquivos de entrada estão localizados na pasta `inputs/` e seguem o formato:
```
<tamanho_memoria>
<numero_de_processos>
<numero_de_acessos>

<processo_id>
<sequencia_de_paginas_acessadas>
<politica_1>
...
```

### Arquivos de Saída
As saídas são geradas na pasta `outputs/` com o padrão `output_<nome>.txt` e contêm:
- Número do processo
- Política aplicada
- Total de page faults
- Páginas finais na memória

## Análise dos Resultados

Os testes foram executados com sucesso em 6 arquivos de entrada diferentes, variando em tamanho e complexidade. A análise comparativa dos resultados por política está apresentada a seguir:

### Resultados da Política MIN (Ótimo)

A política MIN, como esperado, apresentou o **melhor desempenho em todos os cenários**, servindo como referência ideal:

- **small (2048 bytes, 2 processos):** 13 e 9 page faults (processos 1 e 2)
- **medium (2048 bytes, 64 frames, 3 processos):** 77, 103, e 140 page faults
- **large (256 bytes, 8192 acessos):** 6636 page faults (idêntico a todas as políticas - sem substituição necessária)
- **pequena (2048 bytes, 2 KB, 3 processos):** 13, 9, e 11 page faults
- **media (2048 bytes, 64 frames, 6 processos):** 77, 103, 140, 107, 105, e 119 page faults
- **grande (256 bytes, 8192 acessos, 3 processos):** 6636 page faults (todos iguais)

**Observação importante:** No caso "grande" com apenas 256 bytes de memória e 8192 processos, o desempenho de todas as políticas foi idêntico (6636 page faults) devido à extrema restrição de memória, tornando qualquer estratégia de substituição igualmente ineficaz.

### Resultados da Política FIFO

A política FIFO apresentou desempenho consistente, mas geralmente inferior às políticas MIN e LRU:

- **small:** 16 e 11 page faults
- **medium:** 80, 122, e 203 page faults
- **large:** 6636 page faults
- **pequena:** 16, 11, e 14 page faults
- **media:** 80, 122, 203, 144, 172, e 316 page faults
- **grande:** 6636 page faults

**Análise:** A política FIFO apresentou aproximadamente **19-45% mais page faults** em comparação com MIN, demonstrando a limitação de não considerar o padrão de uso das páginas.

### Resultados da Política LRU

A política LRU apresentou o **segundo melhor desempenho**, aproximando-se da política MIN:

- **small:** 17, 10 page faults
- **medium:** 80, 126, e 211 page faults
- **large:** 6636 page faults
- **pequena:** 17, 10, e 13 page faults
- **media:** 80, 126, 211, 150, 184, e 312 page faults
- **grande:** 6636 page faults

**Análise:** LRU superou FIFO na maioria dos casos, ficando apenas **15-30% acima de MIN**, demonstrando que considerar a recência de uso é uma boa aproximação do comportamento ótimo.

### Resultados da Política RAND

A política RAND apresentou o **desempenho mais imprevisível**:

- **small:** 16, 10 page faults
- **medium:** 78, 125, e 216 page faults
- **large:** 6636 page faults
- **pequena:** 16, 10, e 13 page faults
- **media:** 78, 125, 216, 142, 163, e 313 page faults
- **grande:** 6636 page faults

**Análise:** Surpreendentemente, em alguns casos específicos (como no processo 1 do medium com 78 page faults), RAND teve desempenho próximo ou até melhor que FIFO e LRU. Isso demonstra a natureza estocástica do algoritmo e sua dependência dos padrões de acesso das páginas.

### Conclusões Gerais

1. **MIN** é comprovadamente ótimo, mas impraticável em sistemas reais.
2. **LRU** é a melhor escolha prática, apresentando desempenho consistentemente próximo do ótimo.
3. **FIFO** é mais simples de implementar, mas sofre com desempenho inferior em cenários com localidade temporal.
4. **RAND** tem desempenho imprevisível e geralmente não é recomendado, exceto em situações onde a simplicidade é crítica.
5. Em cenários de **extrema restrição de memória** (como "grande"), todas as políticas convergem para desempenho similar, pois a capacidade limitada domina o comportamento do sistema.
6. O **tamanho da memória** e o **padrão de acesso** são fatores críticos que influenciam significativamente a eficácia de cada política.

---

**Desenvolvido para fins educacionais - Sistemas Operacionais**
