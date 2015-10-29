del *.log
call ..\..\SetEnv.bat
call ant.bat -v -l ant.log %1
start /MAX ant.log
