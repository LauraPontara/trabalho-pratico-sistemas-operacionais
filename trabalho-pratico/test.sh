#!/bin/bash

# Script para testar o simulador com todos os casos de teste

echo "========================================="
echo "  Simulador de Memória Virtual - Testes"
echo "========================================="
echo ""

# Compilar
echo "Compilando..."
mkdir -p bin
javac -d bin src/*.java src/policies/*.java

if [ $? -ne 0 ]; then
    echo "Erro na compilação!"
    exit 1
fi

echo "Compilação concluída!"
echo ""

# Criar diretório de saídas
mkdir -p outputs

# Testar com cada arquivo
echo "Executando testes..."
echo ""

for input_file in enunciado/gerador-casos-de-teste/*.txt; do
    filename=$(basename "$input_file" .txt)
    output_file="outputs/saida_${filename}.txt"
    
    echo "Testando: $filename"
    java -cp bin App < "$input_file" > "$output_file"
    
    if [ $? -eq 0 ]; then
        echo "  ✓ Sucesso - Saída em $output_file"
    else
        echo "  ✗ Erro na execução"
    fi
    echo ""
done

# Testar com entradas personalizadas
for input_file in inputs/*.txt; do
    if [ -f "$input_file" ]; then
        filename=$(basename "$input_file" .txt)
        output_file="outputs/saida_${filename}.txt"
        
        echo "Testando: $filename (personalizado)"
        java -cp bin App < "$input_file" > "$output_file"
        
        if [ $? -eq 0 ]; then
            echo "  ✓ Sucesso - Saída em $output_file"
        else
            echo "  ✗ Erro na execução"
        fi
        echo ""
    fi
done

echo "========================================="
echo "  Testes concluídos!"
echo "========================================="
