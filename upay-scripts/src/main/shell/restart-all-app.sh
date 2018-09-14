. ./env-all.conf

readInput

for appName in $APP_SEQ_LIST; do
	echo "============执行$appName的重启操作============"
	execute $appName-$APP_VERSION restart
	echo ""
done
