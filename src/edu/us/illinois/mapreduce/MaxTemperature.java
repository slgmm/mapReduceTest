package edu.us.illinois.mapreduce;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MaxTemperature {
   public static class MaxTemperatureMapper extends Mapper<Object, Text, Text, IntWritable>
   {
      private final static IntWritable temperature = new IntWritable();
      private Text year = new Text();
      
      public void map(Object key, Text value, Context context) throws IOException, InterruptedException 
      {
         StringTokenizer itr = new StringTokenizer(value.toString());
         year.set(value.toString().substring(15, 19));
         temperature.set(Integer.parseInt(value.toString().substring(87, 92)));
         while (itr.hasMoreTokens()) 
         {
            itr.nextToken();
            context.write(year, temperature);
         }
      }
   }
   
   public static class MaxTemperatureReduce extends Reducer<Text,IntWritable,Text,IntWritable> 
   {
      private IntWritable result = new IntWritable();
      public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException 
      {
         int maxTemperature = -999999;
         for (IntWritable val : values) 
         {
        	 if(val.get() != 9999 && val.get() > maxTemperature)
            	maxTemperature = val.get();
         }
         result.set(maxTemperature);
         context.write(key, result);
      }
   }

 
   public static void main(String[] args) throws Exception 
   {
      Configuration conf = new Configuration();
      Job job = Job.getInstance(conf, "word count");
		
      job.setJarByClass(MaxTemperature.class);
      job.setMapperClass(MaxTemperatureMapper.class);
      job.setCombinerClass(MaxTemperatureReduce.class);
      job.setReducerClass(MaxTemperatureReduce.class);
		
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(IntWritable.class);
	
      if(args.length < 2 ){
    	  System.out.println("Please run command with input & output file name");
      }
      FileInputFormat.addInputPath(job, new Path(args[0]));
      FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
      System.exit(job.waitForCompletion(true) ? 0 : 1);
   }
} 	