#!/bin/bash
./gradlew installDebug

if [ $? -eq 0 ]
then
    PHONES=`adb devices |grep "device$"|awk '{print $1}'`
    for p in $PHONES
    do
        adb -s $p shell am start -n com.douban.book.reader/.activity.WelcomeActivity
    done
fi
