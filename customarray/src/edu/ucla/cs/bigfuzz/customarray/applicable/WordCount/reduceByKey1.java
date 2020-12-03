package edu.ucla.cs.bigfuzz.customarray.applicable.WordCount;

public class reduceByKey1 {

            public static Integer applyReduce(int[] a) {
                int s = a[0];
                for(int i = 1 ; i < 2 ; i++){
                    s = a$pply( s , a[i] );
                }
                return s;
            }
            public static Integer a$pply(Integer first, Integer second) {
                return first + second;
            }
        }
