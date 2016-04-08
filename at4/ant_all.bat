del *.log
call SetEnv.bat
set
call ant.bat -v -l ant.log %1
pause
start /MAX ant.log
