set PATH=%PATH%;c:\PortableApps\WinSCP

rem WinSCP alarm@alarmpi /command "cd /" /command ls
winscp.com /command "open sftp://alarm:alarm@alarmpi/" "put dist\at4.jar /home/alarm/at4.jar" 
pause
