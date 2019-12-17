#!/bin/bash

# 
# scp to all hosts defined in ip.conf.
#

filename=$1
dest=$2
ipfile=`dirname $0`/ip.conf

echo "copy file ${filename} to remote ${dest}"

if [ ! -f "$ipfile" ]; then
  echo "ip.conf exists!"
  exit 1
fi

for ip in $(cat $ipfile);do
  echo "scp to $ip.."
  sshpass -p password scp -o "StrictHostKeyChecking no" $filename root@$ip:$dest

done
