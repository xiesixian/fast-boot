#!/bin/sh
 
APP=gotv-api
APP_NAME=${APP}".jar"
JVM="-XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms1024m -Xmx1024m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC"
command=$1
 
function start()
{
  rm -f tpid
  nohup java ${JVM} -jar ${APP_NAME} --spring.profiles.active=release --server.port=39001  > /dev/null 2>&1 &
  echo $! > tpid

  check
}
 
function stop()
{
  tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
  if [ ${tpid} ]; then
     echo 'Stop Process...'
     kill -15 $tpid
   fi
 
   sleep 5
   tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
 
   if [ ${tpid} ]; then
      echo 'Kill Process!'
      kill -9 $tpid
    else
      echo 'Stop Success!'
    fi
}
 
function check()
{
   tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
   if [ ${tpid} ]; then
       echo 'App is running.'
   else
       echo 'App is NOT running.'
   fi
}

function forcekill()
{
  tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
 
  if [ ${tpid} ]; then
     echo 'Kill Process!'
     kill -9 $tpid
 
  fi
 }

 
if [ "${command}" ==  "start" ]; then
     start
 
elif [ "${command}" ==  "stop" ]; then
     stop
	 
elif [ "${command}" ==  "rest" ]; then
	 stop
	 start
 
elif [ "${command}" ==  "check" ]; then
     check
 
elif [ "${command}" ==  "kill" ]; then
     forcekill
else
    echo "Unknow argument...."
fi
