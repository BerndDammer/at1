rm *.log
. ../../SetEnv.sh
# printenv
. ant -v -l ant.log -f build_common_eclipse.xml

# leafpad ant.log
