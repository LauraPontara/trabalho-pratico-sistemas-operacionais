#!/bin/bash
# Script de compilacao para Linux/macOS
# Compila todos os arquivos Java do projeto

echo "========================================"
echo "Compilando Simulador de Memoria Virtual"
echo "========================================"

# Criar diretorio bin se nao existir
if [ ! -d "bin" ]; then
    echo "Criando diretorio bin..."
    mkdir -p bin/policies
fi

# Compilar arquivos Java
echo "Compilando arquivos fonte..."
javac -d bin src/*.java src/policies/*.java

# Verificar resultado da compilacao
if [ $? -eq 0 ]; then
    echo ""
    echo "========================================"
    echo "Compilacao concluida com sucesso!"
    echo "========================================"
else
    echo ""
    echo "========================================"
    echo "ERRO: Falha na compilacao!"
    echo "========================================"
    exit 1
fi
