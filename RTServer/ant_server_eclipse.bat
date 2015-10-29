del *.log
call ..\..\SetEnv.bat
ant -v -l ant.log -f build_server_eclipse.xml
pause
