### Installation & execution
- Compile and execute the code using <br>
   mvn clean compile test -DinputFile=\<intputFilePath\> -DoutputFile=\<outputFilePath\>

### Streaming API example
- Alternative to Java code, one can also use hadoop streaming API with our own scripts <br>
- Following example for version 2.7.3
  1) export HADOOP_STREAMING=$HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-2.7.3.jar <br>
  2) hadoop jar $HADOOP_STREAMING -input \<inputFile1\> -input \<inputFile2\> -output \<ouptputFile\> -mapper \<mapper_dir\>/mapper.py -reducer \<reducer_dir\>/reducer.py
