package com.datastructures.Managers;

import com.datastructures.DataStructures.SortedArrayDictionary;
import com.datastructures.Objects.Grade;
import com.datastructures.interfaces.DictionaryInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class deals with all grade related actions in the app.
 * It is initialized with the username of the student whose grades
 * are being accessed.
 *
 * @author Biruk Mengistu, Mya Hartman, Elena Lam
 */
public class GradesManager {

    // The dictionary that will be temporarily saving grades.
    private final DictionaryInterface<String, Grade> grades;

    // A scanner that we will be using to read contents of the file.
    private static Scanner data;

    // Simple line separator for ease.
    private static final String n = "\n";

    // The filename that will be set when the constructor is called.
    public static String fileName;

    // The default parent username associated with the student.
    private String parentUsername = "none";


    // Constructor
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public GradesManager(String studentUsername) {
        grades = new SortedArrayDictionary<>();
        fileName = studentUsername + "_grades.txt";
        File file = new File(fileName);
        try {
            file.createNewFile();
            data = new Scanner(file);
        } catch (Exception ignored) {
            System.out.println("File could not be created.");
        }
        readFile();
    }

    /**
     * This method reads the file of the student and updates the dictionary.
     */
    public void readFile() {
        if (data.hasNext()) parentUsername = data.next();
        while (data.hasNext()) {
            String name = data.next();
            String grade = data.next();
            grades.add(name, new Grade(name, grade));
        }
        data.close();
    }

    /**
     * This method saves the contents of the grade dictionary to the file of the student.
     */
    private void saveFile() {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(dictionaryToString(grades));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method checks if a given assignment exists for the student.
     *
     * @param name: the name of the assignment.
     * @return whether or not the assignment exists.
     */

    public boolean assignmentExists(String name) {
        return grades.contains(name);
    }

    /**
     * This method converts the dictionary object to a string that can be saved to the file.
     *
     * @param dictionaryInterface: the dictionary object
     * @return the converted string.
     */
    private String dictionaryToString(DictionaryInterface<String, Grade> dictionaryInterface) {
        Iterator<String> nameIterator = dictionaryInterface.getKeyIterator();
        Iterator<Grade> gradeIterator = dictionaryInterface.getValueIterator();
        String s = parentUsername + n; // File starts off with the username of the parent that is associated with the student.
        while (nameIterator.hasNext()) {
            String currentName = nameIterator.next();
            Grade currentGrade = gradeIterator.next();
            s += currentName + " " + currentGrade.getGrade() + n;
        }
        return s;
    }

    /**
     * This method adds a grade for the given student.
     *
     * @param grade: the grade that we want to be added.
     */
    public void addGrade(Grade grade) {
        grades.add(grade.getName(), grade);
        saveFile();
    }

    /**
     * This method edits the grade for a given assignment to a new grade.
     * The reason it looks identical to the addGrade method is that the add() method replaces the value
     * of a given key if it already exists.
     *
     * @param assignmentName: the name of the assignment to be edited.
     * @param newGrade:       the new grade that we want it to be changed to.
     */
    public void editGrade(String assignmentName, Grade newGrade) {
        grades.add(assignmentName, newGrade);
        saveFile();
    }

    /**
     * Gets the average grade for a given student.
     *
     * @return the average.
     */
    public double getAverage() {
        double total = 0;
        ArrayList<Grade> grades = getAllGrades();
        for (Grade grade : grades)
            total += Integer.parseInt(grade.getGrade());
        return grades.size() != 0 ? total / grades.size() : 0;
    }

    /**
     * Gets the username of the parent associated with the student.
     *
     * @return the username of the parent.
     */
    public String getParentUsername() {
        return parentUsername;
    }

    /**
     * Sets the parent of the student
     *
     * @param parentUsername: the username of the parent that we want to assign.
     */
    public void setParent(String parentUsername) {
        this.parentUsername = parentUsername;
        saveFile();
    }

    /**
     * Removes a given assignment from a student.
     * @param assignmentName: the name of the assignment to be removed.
     */
    public void removeGrade(String assignmentName) {
        grades.remove(assignmentName);
        saveFile();
    }

    /**
     * Gets all the grades for a given student.
     * @return an arraylist of all the grades.
     */
    public ArrayList<Grade> getAllGrades() {
        ArrayList<Grade> gradeArrayList = new ArrayList<>();
        Iterator<Grade> theGradeIterator = grades.getValueIterator();
        while (theGradeIterator.hasNext()) {
            gradeArrayList.add(theGradeIterator.next());
        }
        //....
        return gradeArrayList;
    }

    /**
     * Gets the user records in the form of a string
     *
     * @return the string
     */
    @Override
    public String toString() {
        return grades.toString();
    }
}
