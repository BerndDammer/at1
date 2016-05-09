del *.log
call SetEnv.bat
set
call ant.bat -v -l ant.log %1
rem pause
start /MAX ant.log
