package edu.ucla.cs.bigfuzz.customarray.inapplicable.DFOperator;

import scala.Tuple2;

public class mapValues102{

            public static final Tuple2 apply(Tuple2 x)
            {
                System.out.println(x);
                return new Tuple2(x._2(), (Integer)x._1() /(Integer)x._2());
            }


            public static final long serialVersionUID = 0L;

        }
