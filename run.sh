#!/bin/sh
#手动执行、守护服务、自动化均需要使用此脚本，误删

# 名称
appName=xxxx
# jar包
jarName=${appName}".jar"
# 启动参数
jvmOpti="-XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms1024m -Xmx1024m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC"
# 附加参数
command=$1

#启动方法
function start()
{
  pid=`ps -ef|grep $jarName|grep -v grep|awk '{print $2}'`
  #info=`ps -ef|grep $jarName|grep -v grep|grep -v kill|awk "{print $2}"`
  if [ "$pid" ]; then
    echo "$appName is already running. pid is ${pid}"

  else
    nohup java ${jvmOpti} -jar ${jarName} --spring.profiles.active=release --server.port=9091 > ${appName}.log 2>&1 &
    #nohup java ${jvmOpti} -jar ${jarName} --spring.profiles.active=release --server.port=9091 > /dev/null 2>&1 &
    #echo $! > ${appName}.pid
    sleep 5
    status
    
   fi
}

#停止方法
function stop()
{
   
   # 先停止
   pid=`ps -ef|grep $jarName|grep -v grep|awk '{print $2}'`
   if [ "$pid" ]; then
      kill -15 $pid
      echo "Pid:$pid Stopped"
   fi
    
    #杀进程
   pid=`ps -ef|grep $jarName|grep -v grep|awk '{print $2}'`
   if [ "$pid" ]; then
      kill -9 $pid
      echo "Pid:$pid Killed"
   else
      echo "$appName Stop Success!"
   fi
}
 
#运行状态
function status()
{
   pid=`ps -ef|grep $jarName|grep -v grep|awk '{print $2}'`
   info=`ps -ef|grep $jarName|grep -v grep|grep -v kill|awk "{print $2}"`
   if [ "$pid" ]; then
       echo "============>"
       echo "$appName is running. pid is ${pid}"
       echo "------------"
       echo "${info}"
       echo "============>"
   else
       echo "$appName is NOT running."
   fi
}

 
  if [ "${command}" ==  "start" ]; then
     start
 
elif [ "${command}" ==  "stop" ]; then
     stop
	 
elif [ "${command}" ==  "restart" ]; then
	 stop
	 sleep 5
	 start
 
elif [ "${command}" ==  "status" ]; then
     status

else
    echo "Usage:{start|stop|restart|status}"
fi

exit 0