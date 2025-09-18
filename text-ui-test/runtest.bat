@ECHO OFF
SETLOCAL ENABLEEXTENSIONS

REM ---- Clean build dir
IF EXIST ..\bin RMDIR /S /Q ..\bin
MKDIR ..\bin

REM ---- Clean previous output
IF EXIST ACTUAL.TXT DEL ACTUAL.TXT

REM ---- Recursively compile ALL sources into ..\bin
ECHO Compiling sources...
FOR /R "..\src\main\java" %%f IN (*.java) DO (
  javac -cp ..\src\main\java -Xlint:none -d ..\bin "%%f"
  IF ERRORLEVEL 1 (
    ECHO ********** BUILD FAILURE **********
    EXIT /B 1
  )
)

REM ---- Run program (must be executed from the text-ui-test folder)
ECHO Running Leo with redirected input...
java -classpath ..\bin leo.Leo < input.txt > ACTUAL.TXT
IF ERRORLEVEL 1 (
  ECHO ********** RUNTIME FAILURE **********
  EXIT /B 1
)

REM ---- Verify output against expected (silent diff)
FC ACTUAL.TXT EXPECTED.TXT >NUL
IF ERRORLEVEL 1 (
  ECHO ********** TEST FAILED **********
  EXIT /B 1
) ELSE (
  ECHO ********** TEST PASSED **********
)

ENDLOCAL
