rm *.log
. ../../SetEnv.sh
# printenv
. ant -v -l ant.log -f build_client_svn.xml

# leafpad ant.log
