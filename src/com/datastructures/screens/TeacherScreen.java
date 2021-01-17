package com.datastructures.screens;

import com.datastructures.DataStructures.ArrayList;
import com.datastructures.Managers.GradesManager;
import com.datastructures.MainPortal;
import com.datastructures.Objects.Grade;
import com.datastructures.Objects.User;
import com.datastructures.interfaces.Screen;


public class TeacherScreen extends MainPortal implements Screen {

    // The managing class for all grade related tasks
    GradesManager gradesManager;

    /**
     * Method that runs when the screen starts.
     */
    public void start() {
        print("   Welcome, _. | (_ _)  To begin, please select a number.   ", user.getUsername(),user.getYear(), user.getType());
        print("1. View student grades  2. Add grades  3. Change grades  4. Remove grades  5. Log out");

        switch (requestData("choice", 5)) {

            case "1" -> {
                User student = getStudent();
                gradesManager = new GradesManager(student.getUsername());
                for (Grade grade : gradesManager.getAllGrades()) {
                    print("_ - _ ", grade.getName(), grade.getGrade());
                }
                print("Average: _",gradesManager.getAverage());
            }

            case "2" -> {
                User student = getStudent();
                gradesManager = new GradesManager(student.getUsername());
                print("Please enter the name of the assignment you want to add:");
                String name = requestData("other");

                if(gradesManager.assignmentExists(name)){
                    print("Assignment already exists.");
                    break;
                }

                print("Please enter the grade for the assignment:");
                String grade = requestData("other");
                gradesManager.addGrade(new Grade(name, grade));

                print("The assignment _ with grade _ was added to student _.", name, grade, student.getUsername());
            }

            case "3" -> {
                User student = getStudent();
                gradesManager = new GradesManager(student.getUsername());

                for (Grade grade : gradesManager.getAllGrades()) {
                    print("_ - _", grade.getName(), grade.getGrade());
                }
                print("Please enter the name of the assignment you want to change grade:");
                String name = requestData("other");

                if(!gradesManager.assignmentExists(name)){
                    print("Assignment does not exist.");
                    break;
                }

                print("Please enter a new grade for the assignment:");
                String grade = requestData("other");
                gradesManager.editGrade(name, new Grade(name, grade));

                print("The grade for the assignment \"_\" for student _ has been changed to _.", name, student.getUsername(), grade);
            }

            case "4" -> {
                User student = getStudent();
                gradesManager = new GradesManager(student.getUsername());

                for (Grade grade : gradesManager.getAllGrades()) {
                    print("_ - _", grade.getName(), grade.getGrade());
                }
                print("Please enter the name of the assignment you want to remove grade:");
                String name = requestData("other");

                if(!gradesManager.assignmentExists(name)){
                    print("Assignment does not exist.");
                    break;
                }

                gradesManager.removeGrade(name);

                print("The assignment _ of the student _ has been removed.", name, student.getUsername());
            }

            case "5" -> {
                print("Logging out...");
                logOut();
            }
        }
        print("Please enter anything to continue...");
        scanner.next();
        start();
    }

    /**
     * Method that requests the teacher for which student they want to view grades of or modify grades of.
     * @return the selected student
     */
    private static User getStudent() {
        ArrayList<User> students = USER_MANAGER.getStudents(user.getYear());

        print("   ");
        for (int i = 0;i < students.size();i++)
            print("_. _", i + 1, students.get(i).getUsername());

        print("  Please select a student by their number:");
        int choice = Integer.parseInt(requestData("choice", students.size()));
        return students.get(choice - 1);
    }
}
