@echo off
REM Script para executar um teste especifico
REM Uso: run_specific.bat NOME_DO_ARQUIVO (sem extensao .txt)

if "%~1"=="" (
    echo ERRO: Nenhum arquivo especificado!
    echo Uso: run_specific.bat NOME_DO_ARQUIVO
    echo Exemplo: run_specific.bat pequeno
    exit /b 1
)

set INPUT_NAME=%~1
set INPUT_FILE=inputs\%INPUT_NAME%.txt
set OUTPUT_FILE=outputs\output_%INPUT_NAME%.txt

REM Verificar se o arquivo de entrada existe
if not exist "%INPUT_FILE%" (
    echo ERRO: Arquivo %INPUT_FILE% nao encontrado!
    exit /b 1
)

REM Criar diretorio outputs se nao existir
if not exist "outputs" (
    mkdir outputs
)

echo ========================================
echo Executando teste: %INPUT_NAME%
echo ========================================
echo Entrada: %INPUT_FILE%
echo Saida: %OUTPUT_FILE%
echo.

REM Executar o simulador
java -cp bin App < "%INPUT_FILE%" > "%OUTPUT_FILE%"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Execucao concluida com sucesso!
    echo ========================================
    echo.
    echo Conteudo do arquivo de saida:
    echo ----------------------------------------
    type "%OUTPUT_FILE%"
) else (
    echo.
    echo ========================================
    echo ERRO: Falha na execucao!
    echo ========================================
    exit /b 1
)
