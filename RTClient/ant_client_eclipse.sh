rm *.log
. ../../SetEnv.sh
# printenv
. ant -v -l ant.log -f build_client_eclipse.xml

# leafpad ant.log
