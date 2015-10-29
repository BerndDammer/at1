del *.log
call ..\..\SetEnv.bat
ant -v -l ant.log -f build_client_eclipse.xml
pause
