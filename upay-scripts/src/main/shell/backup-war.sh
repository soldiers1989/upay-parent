if [ $# -ne 1 ]
then
    echo "Usage: $0  <tarfile name>"
    exit
fi

if test ! -f $1.war
then
    echo "The install file $1.war is not exist"
    exit
fi
cd /home/weblogic/Oracle/Middleware/user_projects/domains/base_domain/servers/AdminServer/upload
send=`date '+%Y%m%d'`

if test ! -f $1.tar
then
    tar -cvf $1.tar $1
else
    tar -uvf $1.tar $1
fi

sleep 2

if [ ! -d /home/weblogic/backup/$send ]; then
    mkdir -p /home/weblogic/backup/$send
    echo "[INFO]: 创建目录[$send]成功."
fi

mv ./*.tar /home/weblogic/backup/$send
rm -rf $1

mv /home/weblogic/$1.war /home/weblogic/Oracle/Middleware/user_projects/domains/base_domain/servers/AdminServer/upload

unzip -o $1.war -d $1
echo "unzip success........"
rm $1.war