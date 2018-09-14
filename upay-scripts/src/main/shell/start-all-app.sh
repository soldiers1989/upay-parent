. ./env-all.conf

readInput

for appName in $APP_SEQ_LIST; do
	echo "============执行$appName的启动操作============"
	execute $appName-$APP_VERSION start
	echo ""
done
