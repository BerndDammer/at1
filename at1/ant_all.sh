rm *.log
. ../../SetEnv_knoppix.sh
printenv
. ant -v -l ant.log

# leafpad ant.log
