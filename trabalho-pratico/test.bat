@echo off
REM Script para testar o simulador com todos os casos de teste

echo =========================================
echo   Simulador de Memoria Virtual - Testes
echo =========================================
echo.

REM Compilar
echo Compilando...
if not exist bin mkdir bin
javac -d bin src\*.java src\policies\*.java

if %ERRORLEVEL% NEQ 0 (
    echo Erro na compilacao!
    exit /b 1
)

echo Compilacao concluida!
echo.

REM Criar diretório de saídas
if not exist outputs mkdir outputs

REM Testar com cada arquivo do gerador
echo Executando testes...
echo.

for %%f in (enunciado\gerador-casos-de-teste\*.txt) do (
    echo Testando: %%~nf
    java -cp bin App < "%%f" > "outputs\saida_%%~nf.txt" 2>&1
    if %ERRORLEVEL% EQU 0 (
        echo   [OK] Sucesso - Saida em outputs\saida_%%~nf.txt
    ) else (
        echo   [ERRO] Erro na execucao
    )
    echo.
)

REM Testar com entradas personalizadas
for %%f in (inputs\*.txt) do (
    echo Testando: %%~nf ^(personalizado^)
    java -cp bin App < "%%f" > "outputs\saida_%%~nf.txt" 2>&1
    if %ERRORLEVEL% EQU 0 (
        echo   [OK] Sucesso - Saida em outputs\saida_%%~nf.txt
    ) else (
        echo   [ERRO] Erro na execucao
    )
    echo.
)

echo =========================================
echo   Testes concluidos!
echo =========================================
pause
