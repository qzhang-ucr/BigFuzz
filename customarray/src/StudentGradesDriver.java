import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import java.io.IOException;
@RunWith(JQF.class)

public class StudentGradesDriver {

@Fuzz
    public void testStudentGrades(String fileName) throws IOException {
        System.out.println("StudentGradesDriver::testStudentGrades: "+fileName);
        StudentGrades analysis = new StudentGrades();
        analysis.StudentGrades(fileName);
    }
}
