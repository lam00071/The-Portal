package com.datastructures.screens;

import com.datastructures.Managers.GradesManager;
import com.datastructures.MainPortal;
import com.datastructures.Objects.Grade;
import com.datastructures.Objects.User;
import com.datastructures.DataStructures.ArrayList;
import com.datastructures.interfaces.Screen;


public class ParentScreen extends MainPortal implements Screen {
    // List of children for the given parent.
    ArrayList<User> children = USER_MANAGER.getChildren(user.getUsername());

    GradesManager gradesManager;

    /**
     * Method that runs when the screen starts.
     */
    public void start() {
        print("   Welcome, _. | (_)  To begin, please select an option from the list below:   ", user.getUsername(), user.getType());
        print("1. View your children's grades  2. Log out");
        switch (requestData("choice", 2)) {
            case "1" -> {
                if (children.size() == 0) {
                    print("You dont have any children registered at this time.");
                    break;
                }
                int number = 1;
                for (User student : children) {
                    print("_. _", number, student.getUsername());
                    number++;
                }

                print("  Please select student by entering a number.");
                int choice = Integer.parseInt(requestData("choice", children.size()));

                User child = children.get(choice - 1);
                gradesManager = new GradesManager(child.getUsername());
                for (Grade grade : gradesManager.getAllGrades()) {
                    print("_ - _ ", grade.getName(), grade.getGrade());
                }
                print("Average: _", gradesManager.getAverage());
            }
            case "2" -> logOut();
        }
        print("Please enter anything to continue...");
        scanner.next();
        start();
    }
}
