@echo off
REM Script de compilacao para Windows
REM Compila todos os arquivos Java do projeto

echo ========================================
echo Compilando Simulador de Memoria Virtual
echo ========================================

REM Criar diretorio bin se nao existir
if not exist "bin" (
    echo Criando diretorio bin...
    mkdir bin
    mkdir bin\policies
)

REM Compilar arquivos Java
echo Compilando arquivos fonte...
javac -d bin src\*.java src\policies\*.java

REM Verificar resultado da compilacao
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Compilacao concluida com sucesso!
    echo ========================================
) else (
    echo.
    echo ========================================
    echo ERRO: Falha na compilacao!
    echo ========================================
    exit /b 1
)
