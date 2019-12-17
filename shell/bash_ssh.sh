#!/bin/bash

# 
# execute ssh command to all hosts defined in ip.conf.
#

cmd=$@
ipfile=`dirname $0`/ip.conf
pwd="password"

echo "exec cmd $cmd"

if [ ! -f "$ipfile" ]; then
  echo "ip.conf exists!"
  exit 1
fi

for ip in $(cat $ipfile);do
  echo "exex cmd on host $ip.."
  sshpass -p $pwd ssh -o "StrictHostKeyChecking no" $ip "$cmd"

done
