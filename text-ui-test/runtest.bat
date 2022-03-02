@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL0.TXT del ACTUAL0.TXT
if exist ACTUAL1.TXT del ACTUAL1.TXT
if exist ACTUAL2.TXT del ACTUAL2.TXT
if exist ACTUAL3.TXT del ACTUAL3.TXT

REM delete save from previous run
if exist data\save.csv del data\save.csv

REM compile the code into the bin folder
javac  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\duke\*.java ..\src\main\java\duke\task\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM TEST 0: NO SAVE FILE TEST
REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin duke.Duke < reference_in\input0.txt > ACTUAL0.TXT
REM compare the output to the expected output, expected to have differences in save file address output
FC ACTUAL0.TXT reference_out\EXPECTED0.TXT
FC data\save.csv reference_out\EXPECTED_SAVE0.CSV

REM TEST 1: FUNCTIONALITY TEST
REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin duke.Duke < reference_in\input1.txt > ACTUAL1.TXT
REM compare the output to the expected output
FC ACTUAL1.TXT reference_out\EXPECTED1.TXT
FC data\save.csv reference_out\EXPECTED_SAVE1.CSV

REM TEST 2: SUCCESSFUL LOAD TEST
REM rerun program to test loading function
java -classpath ..\bin duke.Duke < reference_in\input2.txt > ACTUAL2.TXT
REM compare the output to the expected output
FC ACTUAL2.TXT reference_out\EXPECTED2.TXT
FC data\save.csv reference_out\EXPECTED_SAVE1.CSV

REM TEST 3: INVALID LOAD TEST
REM delete previous save file and copy malformed csv to data folder
if exist data\save.csv del data\save.csv
COPY MALFORMED_SAVE.CSV data\save.csv
REM rerun program to test malformed save load
java -classpath ..\bin duke.Duke < reference_in\input3.txt > ACTUAL3.TXT
REM compare the output to the expected output
FC ACTUAL3.TXT reference_out\EXPECTED3.TXT
FC data\save.csv reference_out\EXPECTED_SAVE3.CSV