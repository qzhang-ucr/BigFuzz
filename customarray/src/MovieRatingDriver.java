import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JQF.class)

public class MovieRatingDriver {

@Fuzz
    public void testMovieRating(String fileName) throws IOException {
        System.out.println("MovieRatingDriver::testMovieRating: "+fileName);
        MovieRating analysis = new MovieRating();
    List<String> fileList = Files.readAllLines(Paths.get(fileName));
    System.out.println(fileList.size());
    analysis.MovieRating(fileList.get(0));
    }

    public static void main(String[] args) throws IOException {

        MovieRating analysis = new MovieRating();
        analysis.MovieRating("/home/qzhang/Programs/BigFuzz/dataset/salary1.csv");
    }
}
