@echo off
rem Gradle wrapper script for Windows

set DIR=%~dp0
if "%DIR%"=="" set DIR=.

set GRADLE_HOME=%DIR%gradle
set PATH=%GRADLE_HOME%\bin;%PATH%

java -jar "%DIR%gradle\wrapper\gradle-wrapper.jar" %*