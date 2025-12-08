#!/bin/bash
# Script para executar um teste especifico
# Uso: ./run_specific.sh NOME_DO_ARQUIVO (sem extensao .txt)

if [ -z "$1" ]; then
    echo "ERRO: Nenhum arquivo especificado!"
    echo "Uso: ./run_specific.sh NOME_DO_ARQUIVO"
    echo "Exemplo: ./run_specific.sh pequeno"
    exit 1
fi

INPUT_NAME="$1"
INPUT_FILE="inputs/${INPUT_NAME}.txt"
OUTPUT_FILE="outputs/output_${INPUT_NAME}.txt"

# Verificar se o arquivo de entrada existe
if [ ! -f "$INPUT_FILE" ]; then
    echo "ERRO: Arquivo $INPUT_FILE nao encontrado!"
    exit 1
fi

# Criar diretorio outputs se nao existir
if [ ! -d "outputs" ]; then
    mkdir outputs
fi

echo "========================================"
echo "Executando teste: $INPUT_NAME"
echo "========================================"
echo "Entrada: $INPUT_FILE"
echo "Saida: $OUTPUT_FILE"
echo ""

# Executar o simulador
java -cp bin App < "$INPUT_FILE" > "$OUTPUT_FILE"

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================"
    echo "Execucao concluida com sucesso!"
    echo "========================================"
    echo ""
    echo "Conteudo do arquivo de saida:"
    echo "----------------------------------------"
    cat "$OUTPUT_FILE"
else
    echo ""
    echo "========================================"
    echo "ERRO: Falha na execucao!"
    echo "========================================"
    exit 1
fi
