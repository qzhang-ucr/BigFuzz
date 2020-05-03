import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(JQF.class)

public class StudentGradesDriver {

@Fuzz
    public void testStudentGrades(String fileName) throws IOException {
        System.out.println("StudentGradesDriver::testStudentGrades: "+fileName);
        StudentGrades analysis = new StudentGrades();
    List<String> fileList = Files.readAllLines(Paths.get(fileName));
    System.out.println(fileList.size());
    analysis.StudentGrades(fileList.get(0));
    }

    public static void main(String[] args) throws IOException {

        StudentGrades analysis = new StudentGrades();
        analysis.StudentGrades("/home/qzhang/Programs/BigFuzz/dataset/salary1.csv");
    }
}
