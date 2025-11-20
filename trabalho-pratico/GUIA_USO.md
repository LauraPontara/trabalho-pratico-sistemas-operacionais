# Guia de Uso do Simulador

## Compilação e Execução

### Windows

1. **Compilar o projeto:**
   ```batch
   compile.bat
   ```

2. **Executar com arquivo de entrada:**
   ```batch
   run.bat entrada_pequena
   ```

### Linux/Mac

1. **Compilar:**
   ```bash
   mkdir -p bin
   javac -d bin src/*.java src/policies/*.java
   ```

2. **Executar:**
   ```bash
   java -cp bin App < inputs/entrada_pequena.txt > outputs/saida_pequena.txt
   ```

## Gerar Casos de Teste Personalizados

O projeto inclui um gerador Python em `enunciado/gerador-casos-de-teste/`:

### Teste Pequeno
```bash
cd enunciado/gerador-casos-de-teste
python gen.py -s 5 --min_req 50 --max_req 100 -p 32 > ../../inputs/teste_pequeno.txt
```

### Teste Médio
```bash
python gen.py -s 10 --min_req 100 --max_req 1000 -p 128 --mfisica 131072 --mvirtual 262144 > ../../inputs/teste_medio.txt
```

### Teste Grande
```bash
python gen.py -s 10 --min_req 1000 --max_req 10000 -p 32768 --mfisica 2097152 --mvirtual 8388608 > ../../inputs/teste_grande.txt
```

## Parâmetros do Gerador

- `-s`: Número de sequências
- `--min_req`: Número mínimo de requisições por sequência
- `--max_req`: Número máximo de requisições por sequência
- `-p`: Número de páginas virtuais
- `--mfisica`: Tamanho da memória física (RAM) em bytes
- `--mvirtual`: Tamanho da memória virtual em bytes

## Exemplo de Uso Completo

```batch
# 1. Gerar caso de teste
cd enunciado\gerador-casos-de-teste
python gen.py -s 3 --min_req 20 --max_req 50 -p 16 > ..\..\inputs\meu_teste.txt

# 2. Voltar para a raiz
cd ..\..

# 3. Compilar (se ainda não compilou)
compile.bat

# 4. Executar
run.bat meu_teste

# 5. Ver resultado
type outputs\saida_meu_teste.txt
```

## Estrutura dos Arquivos

- **Entrada**: `inputs/*.txt`
- **Saída**: `outputs/saida_*.txt`
- **Código compilado**: `bin/*.class`

## Políticas Implementadas

- **FIFO**: First In, First Out
- **RAND**: Random
- **LRU**: Least Recently Used
- **MIN**: Optimal (MIN/OPT)

## Troubleshooting

### "javac não é reconhecido"
- Instale o JDK e adicione ao PATH do sistema

### "python não é reconhecido"
- Instale o Python 3 e adicione ao PATH

### Erro de compilação
- Verifique se todos os arquivos .java estão no local correto
- Execute `compile.bat` novamente

### Arquivo de entrada não encontrado
- Verifique se o arquivo está em `inputs/`
- Use o nome sem extensão: `run.bat entrada` (não `run.bat entrada.txt`)
