@echo off
if not exist bin mkdir bin
echo Compilando...
javac -d bin src\*.java src\policies\*.java
if %ERRORLEVEL% EQU 0 (
    echo Compilacao concluida com sucesso!
) else (
    echo Erro na compilacao!
)
