@echo off
setlocal enabledelayedexpansion

if "%1"=="" (
    echo Uso: run.bat ^<nome_do_arquivo^>
    echo Exemplo: run.bat entrada_pequena
    exit /b 1
)

set INPUT_FILE=inputs\%1.txt
set OUTPUT_FILE=outputs\saida_%1.txt

if not exist "%INPUT_FILE%" (
    echo Erro: Arquivo %INPUT_FILE% nao encontrado!
    exit /b 1
)

if not exist outputs mkdir outputs

echo Executando com entrada: %INPUT_FILE%
java -cp bin App < "%INPUT_FILE%" > "%OUTPUT_FILE%"

if %ERRORLEVEL% EQU 0 (
    echo Resultado salvo em %OUTPUT_FILE%
    echo.
    echo Conteudo da saida:
    type "%OUTPUT_FILE%"
) else (
    echo Erro na execucao!
)
