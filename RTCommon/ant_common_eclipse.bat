del *.log
call ..\..\SetEnv.bat
ant -v -l ant.log -f build_common_eclipse.xml
pause
