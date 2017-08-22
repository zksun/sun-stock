#! /bin/sh
PROJECT_HOME=$(dirname $(pwd))

#echo $PROJECT_HOME
for line in $(find $PROJECT_HOME/lib/*.jar)
do
    CLASSPATH=$line:$CLASSPATH
done

#CLASSPATH=$PROJECT_HOME/conf:$CLASSPATH
export CLASSPATH

#echo JAVA_HOME=$JAVA_HOME
#echo CLASSPATH=$CLASSPATH
#java -version
java -Xmx768M -Xms768M -Xmn512M -XX:MaxDirectMemorySize=1G -Dio.netty.leakDetectionLevel=advanced -Xbootclasspath/a:$CLASSPATH -jar  $1 $2 $3 >std.log 2>&1 &