. ./env.conf

execute(){

if [ "`ls -A $TEMP_HOME`" = "" ]; then
echo "$TEMP_HOME is  empty"
exit 0
fi

cd $APP_HOME
#rm -rf $APP_HOME/upay* $APP_HOME/batch*

        lists=`ls $TEMP_HOME`
for key  in `echo $lists | sed 's/ / /g'`
do
    echo "=============start=========="$key
               #mv $TEMP_HOME/$key $APP_HOME
               cp $TEMP_HOME/$key $APP_HOME
            unzip -o $key
            rm -rf $key
    echo "=============end=========="$key
echo ""
done
}

execute
