#!/bin/bash
# Script para executar todos os testes da pasta inputs
# Processa todos os arquivos .txt e gera as saidas correspondentes

echo "========================================"
echo "Executando TODOS os testes"
echo "========================================"
echo ""

# Verificar se a pasta inputs existe
if [ ! -d "inputs" ]; then
    echo "ERRO: Pasta inputs nao encontrada!"
    exit 1
fi

# Criar diretorio outputs se nao existir
if [ ! -d "outputs" ]; then
    mkdir outputs
fi

# Contadores
COUNT=0
SUCCESS=0
FAILED=0

# Iterar sobre todos os arquivos .txt na pasta inputs
for INPUT_FILE in inputs/*.txt; do
    # Verificar se existem arquivos
    if [ ! -f "$INPUT_FILE" ]; then
        echo "Nenhum arquivo .txt encontrado na pasta inputs/"
        exit 1
    fi
    
    # Incrementar contador
    ((COUNT++))
    
    # Extrair nome do arquivo sem extensao
    FILENAME=$(basename "$INPUT_FILE" .txt)
    OUTPUT_FILE="outputs/output_${FILENAME}.txt"
    
    echo "[$COUNT] Processando: ${FILENAME}.txt"
    echo "     Entrada: $INPUT_FILE"
    echo "     Saida: $OUTPUT_FILE"
    
    # Executar o simulador
    java -cp bin App < "$INPUT_FILE" > "$OUTPUT_FILE" 2>/dev/null
    
    if [ $? -eq 0 ]; then
        echo "     Status: OK"
        ((SUCCESS++))
    else
        echo "     Status: FALHOU"
        ((FAILED++))
    fi
    echo ""
done

echo "========================================"
echo "Resumo da Execucao"
echo "========================================"
echo "Total de testes: $COUNT"
echo "Sucessos: $SUCCESS"
echo "Falhas: $FAILED"
echo "========================================"

if [ $FAILED -gt 0 ]; then
    exit 1
fi
