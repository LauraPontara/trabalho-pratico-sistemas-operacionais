@echo off
REM Script para executar todos os testes da pasta inputs
REM Processa todos os arquivos .txt e gera as saidas correspondentes

setlocal enabledelayedexpansion

echo ========================================
echo Executando TODOS os testes
echo ========================================
echo.

REM Verificar se a pasta inputs existe
if not exist "inputs" (
    echo ERRO: Pasta inputs nao encontrada!
    exit /b 1
)

REM Criar diretorio outputs se nao existir
if not exist "outputs" (
    mkdir outputs
)

REM Contador de testes
set COUNT=0
set SUCCESS=0
set FAILED=0

REM Iterar sobre todos os arquivos .txt na pasta inputs
for %%f in (inputs\*.txt) do (
    set /a COUNT+=1
    set INPUT_FILE=%%f
    set FILENAME=%%~nf
    set OUTPUT_FILE=outputs\output_!FILENAME!.txt
    
    echo [!COUNT!] Processando: !FILENAME!.txt
    echo     Entrada: !INPUT_FILE!
    echo     Saida: !OUTPUT_FILE!
    
    REM Executar o simulador
    java -cp bin App < "!INPUT_FILE!" > "!OUTPUT_FILE!" 2>nul
    
    if !ERRORLEVEL! EQU 0 (
        echo     Status: OK
        set /a SUCCESS+=1
    ) else (
        echo     Status: FALHOU
        set /a FAILED+=1
    )
    echo.
)

echo ========================================
echo Resumo da Execucao
echo ========================================
echo Total de testes: !COUNT!
echo Sucessos: !SUCCESS!
echo Falhas: !FAILED!
echo ========================================

if !FAILED! GTR 0 (
    exit /b 1
)
