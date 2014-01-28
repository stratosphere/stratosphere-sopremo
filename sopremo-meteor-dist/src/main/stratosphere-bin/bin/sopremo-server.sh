#!/bin/bash
########################################################################################################################
# 
#  Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
# 
#  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
#  the License. You may obtain a copy of the License at
# 
#      http://www.apache.org/licenses/LICENSE-2.0
# 
#  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
#  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
#  specific language governing permissions and limitations under the License.
# 
########################################################################################################################

STARTSTOP=$1

bin=`dirname "$0"`
bin=`cd "$bin"; pwd`

# get nephele config
. "$bin"/config.sh

if [ "$STRATOSPHERE_PID_DIR" = "" ]; then
        STRATOSPHERE_PID_DIR=/tmp
fi

if [ "$STRATOSPHERE_IDENT_STRING" = "" ]; then
        STRATOSPHERE_IDENT_STRING="$USER"
fi

JVM_ARGS="$JVM_ARGS -Xmx512m"

log=$STRATOSPHERE_LOG_DIR/sopremo-$STRATOSPHERE_IDENT_STRING-server.log
pid=$STRATOSPHERE_PID_DIR/sopremo-$STRATOSPHERE_IDENT_STRING-server.pid
log_setting="-Dlog.file="$log" -Dlog4j.configuration=file://"$STRATOSPHERE_CONF_DIR"/log4j.properties"

case $STARTSTOP in

        (start)
                mkdir -p "$STRATOSPHERE_PID_DIR"
                if [ -f $pid ]; then
                        if kill -0 `cat $pid` > /dev/null 2>&1; then
                                echo Sopremo server running as process `cat $pid`.  Stop it first.
                                exit 1
                        fi
                fi
                echo Starting Sopremo server
		$JAVA_HOME/bin/java $JVM_ARGS $log_setting -classpath $CLASSPATH eu.stratosphere.sopremo.server.SopremoServer -configDir $STRATOSPHERE_CONF_DIR &
		echo $! > $pid
	;;

        (stop)
                if [ -f $pid ]; then
                        if kill -0 `cat $pid` > /dev/null 2>&1; then
                                echo Stopping Sopremo server
                                kill `cat $pid`
                                kill -9 `cat $pid`
                        else
                                echo No Sopremo server to stop
                        fi
                else
                        echo No Sopremo server to stop
                fi
        ;;

        (*)
                echo Please specify start or stop
        ;;

esac


