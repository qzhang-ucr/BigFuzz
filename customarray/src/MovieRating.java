import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.customarray.applicable.MovieRating.MovieRatingCustomArray;
import edu.ucla.cs.bigfuzz.customarray.applicable.MovieRating.map3;
import edu.ucla.cs.bigfuzz.customarray.applicable.MovieRating.map4;

import java.io.IOException;
import java.util.ArrayList;

public class MovieRating {
public void MovieRating(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
ArrayList<map4> results1 = MovieRatingCustomArray.Map1(results0);
ArrayList<map3> results2 = MovieRatingCustomArray.Map2(results1);
ArrayList<map3> results3 = MovieRatingCustomArray.Filter1(results2);
ArrayList<map3> results4 = MovieRatingCustomArray.ReduceByKey1(results3);
}}
