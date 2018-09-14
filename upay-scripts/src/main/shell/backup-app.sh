if [ $# -ne 1 ]
then
    echo "Usage: $0  <tarfile name>"
    exit
fi

if test ! -f $1
then
    echo "The install file $1 is not exist"
    exit
fi

cd /home/appsvr

unzip -o $1
cd /home/appsvr/app
filelist=`ls *.zip`
send=`date '+%Y%m%d'`

for file in `echo $filelist | sed 's/-bin.zip//g'`
do
    if test ! -f $file.tar.gz.bak
    then
        tar -zcvf $file.tar.gz.bak $file
        rm -rf $file
    else
        tar -zuvf $file.tar.gz.bak $file
        rm -rf $file
    fi
done
sleep 1
echo ""
echo "Start to uncompress $1 ......"
ls *.zip | xargs -l unzip -o
rm *.zip
if [ ! -d /home/appsvr/backup/$send ]; then
                mkdir -p /home/appsvr/backup/$send
                echo "[INFO]: 创建目录[$send]成功."
fi
mv ./*.tar.gz.bak /home/appsvr/backup/$send

cd /home/appsvr

mv app.zip /home/appsvr/backup/$send

echo "[INFO]: 备份global-resource 配置文件开始......"
#backup global-resource
tar -zcvf global-resources.tar.gz.bak global-resources
mv global-resources.tar.gz.bak /home/appsvr/backup/$send
echo "[INFO]: 备份global-resource 配置文件完成........."


echo "[INFO]: 拷贝本次上线配置文件开始......."
##mv config
#core config
cp /home/appsvr/resources/core/08001.xml /home/appsvr/global-resources/config/gateway-client/core/META-INF/mp/corecli/unpack

#weixin open id config
cp /home/appsvr/resources/weixinopenid/beans.xml /home/appsvr/global-resources/config/gateway-client/weixinopenid/META-INF/flow

#zj config
cp /home/appsvr/resources/zj/beans.xml /home/appsvr/global-resources/config/gateway-client/zjpay/META-INF/flow/
cp /home/appsvr/resources/zj/provider.xml /home/appsvr/global-resources/config/gateway-client/zjpay/META-INF/dubbo/

#wei xin config
cp /home/appsvr/resources/weixin/beans.xml /home/appsvr/global-resources/config/gateway-client/weixin/META-INF/flow/
cp /home/appsvr/resources/weixin/NEWMERCHANT.ftl /home/appsvr/global-resources/config/gateway-client/weixin/META-INF/mp/weixinmerchcli/pack/
cp /home/appsvr/resources/weixin/QUERYMERCHANT.xml /home/appsvr/global-resources/config/gateway-client/weixin/META-INF/mp/weixinmerchcli/unpack/

#zookeeper  db config
cp /home/appsvr/resources/remote-db.properties /home/appsvr/global-resources/config/
cp /home/appsvr/resources/remote-zookeeper.properties /home/appsvr/global-resources/config/
echo "[INFO]: 拷贝本次上线配置文件完成......."


#backup config
mv resources /home/appsvr/backup/$send
