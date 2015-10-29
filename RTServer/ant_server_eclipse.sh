rm *.log
. ../../SetEnv.sh
# printenv
. ant -v -l ant.log -f build_server_eclipse.xml

# leafpad ant.log
