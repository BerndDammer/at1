del *.log
call SetEnv.bat
set
pause
call ant.bat -v -l ant.log %1
start /MAX ant.log
