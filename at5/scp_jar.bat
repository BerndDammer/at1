set PATH=%PATH%;c:\PortableApps\WinSCP

rem WinSCP alarm@alarmpi /command "cd /" /command ls
winscp.com /command "open sftp://alarm:alarm@alarmpi/" "put dist\wwt1.jar /home/alarm/wwt1.jar" 
pause
