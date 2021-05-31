import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GradeEvaluationSystem {

    public static void main(String[] args){

        final int GROUP_MEMBERS = 20;
        int studentGrade;

        String studentName, statisticsResult;

        boolean validGrade;

        HashMap<String, Integer> studentsAndGrades = new HashMap<>();
        Scanner myScanner = new Scanner(System.in);

        System.out.println("**** Students grades manager ****");

        //Get the students data
        for(int i = 1; i <= GROUP_MEMBERS; i++) {
            System.out.println("--- Student N° " + i + " ---");
            System.out.print("Type the name of the student: ");
            studentName = myScanner.nextLine();

            //Validate the number range and format
            do {
                try {
                    System.out.print("Type the grade obtained by the student (0-10): ");
                    studentGrade = Integer.parseInt(myScanner.nextLine());
                    if(studentGrade >= 0 && studentGrade <= 10) {
                        validGrade = true;
                        // Save the data in the Map
                        studentsAndGrades.put(studentName, studentGrade);
                    } else {
                        System.out.println("Invalid grade, please enter an integer between 0 and 10");
                        validGrade = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format, please enter an integer between 0 and 10");
                    validGrade = false;
                }
            } while(!validGrade);

        }

        // Getting the statistics
        statisticsResult = Statistics(studentsAndGrades, GROUP_MEMBERS);

        //Create the file and save the data
        File gradesFile = new File("gradesFile.txt");
        try {
            gradesFile.createNewFile();
        } catch(IOException e) {
            System.out.println("\nAn error has occurred while creating the file...");
            return;
        }

        try {
            FileWriter myWriter = new FileWriter("gradesFile.txt");
            myWriter.write("**** Students and grades database ****\n");
            for (Map.Entry<String, Integer> entry : studentsAndGrades.entrySet()) {
                myWriter.write(entry.getKey() + ": "+ entry.getValue() + "\n");
                myWriter.write("-----\n");
            }
            myWriter.write(statisticsResult);
            myWriter.close();

            System.out.println("\nData saved correctly in: " + gradesFile.getName());
        } catch (IOException e) {
            System.out.println("\nAn error occurred while saving the data to the file....");
        }

        // Present the statistics
        System.out.println(statisticsResult);

    }

    public static String Statistics(HashMap<String, Integer> studentsAndGrades, int GROUP_MEMBERS){
        int max = 0, min = 10, counterMostRepeated = 0, mostRepeated = 0, lessRepeated = 0, counterLessRepeated;

        double sum = 0, avg;

        int[] counter = {0,0,0,0,0,0,0,0,0,0,0};
        StringBuilder statisticsString = new StringBuilder("\n**** Statistics ****\n");

        for (Map.Entry<String, Integer> entry : studentsAndGrades.entrySet()) {
            //Get max and min grades
            if(entry.getValue() > max) {
                max = entry.getValue();
            }
            if(entry.getValue() < min) {
                min = entry.getValue();
            }
            //Counting how many times the number is repeated
            counter[entry.getValue()] += 1;
            //Summation of the grades to get the average
            sum += entry.getValue();
        }
        //Get the average
        avg = Math.floor((sum / GROUP_MEMBERS) * 100) / 100;

        // Get most and less repeated grade
        for (int i = 0; i < counter.length; i++) {
            if(counter[i] > counterMostRepeated) {
                counterMostRepeated = counter[i];
                mostRepeated = i;
            }
        }

        counterLessRepeated = counterMostRepeated;

        for(int k = 0; k < counter.length; k++) {
            if(counter[k] > 0 && counter[k] < counterLessRepeated) {
                counterLessRepeated = counter[k];
                lessRepeated = k;
            }
        }

        statisticsString.append("-> Max grade: ").append(max).append("\n");
        statisticsString.append("-> Min grade: ").append(min).append("\n");
        statisticsString.append("-> Average: ").append(avg).append("\n");
        statisticsString.append("-> Most repeated grade: ").append(mostRepeated).append("(").append(counterMostRepeated).append(" times)").append("\n");
        statisticsString.append("-> Less repeated grade: ").append(lessRepeated).append("(").append(counterLessRepeated).append(" times)");

        return statisticsString.toString();
    }
}
