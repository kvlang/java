#!/bin/bash
mkdir kvl kvltest
rm -rf KVL.java
cp $HOME/IdeaProjects/KVL/src/kvl/KVL.java .
javac *.java
mv KVL*.class kvl
mv *.class kvltest
jar cfm KVL-Basic.jar manifest.mf kvl/*.class kvltest/*.class > /dev/null
rm -rf kvl kvltest KVL.java