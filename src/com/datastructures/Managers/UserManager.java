package com.datastructures.Managers;

import com.datastructures.DataStructures.SortedArrayDictionary;
import com.datastructures.Objects.User;
import com.datastructures.DataStructures.ArrayList;
import com.datastructures.interfaces.DictionaryInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;


/**
 * This class deals with all user related actions throughout the app. This includes login and sign up
 * actions as well.
 *
 * @author Biruk Mengistu, Mya Hartman, Elena Lam
 */


public class UserManager {
    // The dictionary that temporarily stores the users.
    private final DictionaryInterface<String, User> userRecords;

    // The filename of the file that will be storing user credentials.
    private static final String fileName = "user_records.txt";

    // The scanner that will be reading the files.
    private static Scanner data;

    // simple line separator.
    private static final String n = "\n";


    // Constructor
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public UserManager() {
        userRecords = new SortedArrayDictionary<>();
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
     * This method reads the file and updates the dictionary
     */
    public void readFile() {
        while (data.hasNext()) {
            String username = data.next();
            String password = data.next();
            String type = data.next();
            String year = data.next();
            userRecords.add(username, new User(username, password, type, User.parseYear(year)));
        }
        data.close();
    }

    /**
     * This method saves the contents of the user record to the record file
     */
    private void saveFile() {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(dictionaryToString(userRecords));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates a new account for a user and signs them up.
     *
     * @param user: the user that we will be signing up.
     * @return whether or not the user can be signed up.
     */
    public boolean signUp(User user) {
        if (userExists(user.getUsername())) {        // looks to see if the user name already exsits
            return false;
        } else userRecords.add(user.getUsername(), user);
        saveFile();
        return true;
    }

    /**
     * Checks whether or not a user exists in the database.
     *
     * @param username: the username that we will be checking for
     * @return whether or not the user exists.
     */
    public boolean userExists(String username) {
        return userRecords.contains(username);
    }

    /**
     * This method converts the dictionary object to a string that can be saved to the file.
     *
     * @param dictionaryInterface: the dictionary object
     * @return the converted string.
     */
    private String dictionaryToString(DictionaryInterface<String, User> dictionaryInterface) {
        Iterator<String> usernameIterator = dictionaryInterface.getKeyIterator();
        Iterator<User> userIterator = dictionaryInterface.getValueIterator();
        String s = "";
        while (usernameIterator.hasNext()) {
            String currentUsername = usernameIterator.next();
            User currentUser = userIterator.next();
            s += currentUsername + " " + currentUser.getPassword() + " " + currentUser.getType().toString() + " " + currentUser.getYear().toString() + n;
        }
        return s;
    }

    /**
     * Checks if a given username password combination is valid.
     *
     * @param username: username  that we will be checking.
     * @param password  password that we will be checking.
     * @return whether or not the combo is valid.
     */
    public boolean isValid(String username, String password) {
        User user = userRecords.getValue(username);
        return password.equals(user.getPassword());
    }

    /**
     * Gets a user object when given a username
     *
     * @param username: username of the user we want to fetch.
     * @return the user object.
     */
    public User getUser(String username) {
        return userRecords.getValue(username);
    }

    /**
     * Gets all children for a given parent.
     *
     * @param parentUsername: the username of the parent.
     * @return a list of the children.
     */

    public ArrayList<User> getChildren(String parentUsername) {
        ArrayList<User> children = new ArrayList<>();
        Iterator<User> childrenIterator = userRecords.getValueIterator();
        while (childrenIterator.hasNext()) {
            User currentChild = childrenIterator.next();
            if (currentChild.getType() == User.Type.STUDENT) {
                GradesManager manager = new GradesManager(currentChild.getUsername());
                if (manager.getParentUsername().equals(parentUsername))
                    children.add(currentChild);
            }
        }
        return children;
    }

    /**
     * Gets all parents stored in the file.
     *
     * @return a list of the parents.
     */
    public ArrayList<User> getParents() {
        ArrayList<User> parents = new ArrayList<>();
        Iterator<User> parentsIterator = userRecords.getValueIterator();
        while (parentsIterator.hasNext()) {
            User currentChild = parentsIterator.next();
            if (currentChild.getType().equals(User.Type.PARENT))
                parents.add(currentChild);
            //
        }
        return parents;
    }

    /**
     * Gets all students stored in the database.
     *
     * @return a list of students.
     */
    public ArrayList<User> getStudents(User.Year year) {
        ArrayList<User> students = new ArrayList<>();
        Iterator<User> studentsIterator = userRecords.getValueIterator();
        while (studentsIterator.hasNext()) {
            User currentStudent = studentsIterator.next();
            if (currentStudent.getType().equals(User.Type.STUDENT) && currentStudent.getYear().equals(year))
                students.add(currentStudent);
        }
        return students;
    }

    /**
     * Gets the user records in the form of a string
     *
     * @return the string
     */
    @Override
    public String toString() {
        return userRecords.toString();
    }
}
