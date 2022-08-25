import cs2030s.fp.Maybe;
import cs2030s.fp.Transformer;
import java.util.Map;

class Lab5 {
  /* public static String getGrade(String student, String module, String assessment,
     Map<String, Map<String, Map<String, String>>> db) {

    Map<String, Map<String, String>> std = db.get(student);
    if (std == null) {
      return "No such entry";
    } else {
      Map<String, String> mod = std.get(module);
      if (mod == null) {
        return "No such entry";
      } else {
        String grade = mod.get(assessment);
        if (grade == null) {
          return "No such entry";
        }
        return grade;
      }
    }
  } */
  
  public static String getGrade(String student, String module, String assessment,
      Map<String, Map<String, Map<String, String>>> map) { 
    /* In my previous attempt, I tried to get the student name in a field by itself. But I
     * managed to make it also work with a transformer instead - so even though the Question.md
     * said two transformers, I hope it is okay that I made a third transformer in order to get
     * the student's name. We need the student's name to get the grade because the map is -
     * map(student name 1, map(module, map(assignment, grade)), student name 2 ...).
     * I couldn't figure out how to make a solution only using two transformers.
     */
    Transformer<Map<String, Map<String, Map<String, String>>>, 
        Maybe<Map<String, Map<String, String>>>> getStudentName =
        new Transformer<>() {
          public Maybe<Map<String, Map<String, String>>> transform(Map<String, Map<String, 
              Map<String, String>>> value) {
            Map<String, Map<String, String>> studentName = value.get(student);
            return Maybe.of(studentName);
          }
        };

    Transformer<Map<String, Map<String, String>>, Maybe<Map<String, String>>> getModule =
        new Transformer<>() {  
          public Maybe<Map<String, String>> transform(Map<String, Map<String, String>> value) {
            Map<String, String> mod = value.get(module);
            return Maybe.of(mod);
          }
        };

    Transformer<Map<String, String>, Maybe<String>> getAssessment =
        new Transformer<>() {
          public Maybe<String> transform(Map<String, String> value) {
            String assess = value.get(assessment);
            return Maybe.of(assess);
          } 
        }; 

    /* We use flatMap()s to take in our transformers and return a Maybe<U> which is used for the 
     * next transformer if needed. Then, orElse() will check if it is a Maybe<T> which is None or
     * Some<T>. If it is a None, that means there is not entry of a student's grade. Else, it will
     * return us the student's grade!
     */
    return Maybe.of(map).flatMap(getStudentName).flatMap(getModule).flatMap(getAssessment)
      .orElse("There's no such entry");
  }
  
  public static void main(String[] args) {
    Map<String, Map<String, Map<String, String>>> students =
        Map.of(
            "Steve", Map.of(
                "CS2030S", Map.of(
                        "lab1", "A",
                        "lab2", "A-",
                        "lab3", "A+",
                        "lab4", "B",
                        "pe1", "C"),
                "CS2040S", Map.of(
                        "lab1", "A",
                        "lab2", "A+",
                        "lab3", "A+",
                        "lab4", "A",
                        "midterm", "A+")),
            "Tony", Map.of(
                "CS2030S", Map.of(
                    "lab1", "C",
                    "lab2", "C",
                    "lab3", "B-",
                    "lab4", "B+",
                    "pe1", "A")));

    System.out.println(getGrade("Steve", "CS2030S", "lab1", students));
    System.out.println(getGrade("Steve", "CS2030S", "lab2", students));
    System.out.println(getGrade("Steve", "CS2040S", "lab3", students));
    System.out.println(getGrade("Steve", "CS2040S", "lab4", students));
    System.out.println(getGrade("Tony", "CS2030S", "lab1", students));
    System.out.println(getGrade("Tony", "CS2030S", "midterm", students));
    System.out.println(getGrade("Tony", "CS2040S", "lab4", students));
    System.out.println(getGrade("Bruce", "CS2040S", "lab4", students));
  }
}
