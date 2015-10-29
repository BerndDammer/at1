rm *.log
. ../../SetEnv.sh
# printenv
. ant -v -l ant.log -f build_server_svn.xml

# leafpad ant.log
