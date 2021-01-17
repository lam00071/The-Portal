package com.datastructures;

import com.datastructures.Managers.UserManager;
import com.datastructures.Objects.User;
import com.datastructures.screens.ParentScreen;
import com.datastructures.interfaces.Screen;
import com.datastructures.screens.StudentScreen;
import com.datastructures.screens.TeacherScreen;

import java.util.Scanner;

/**
 * This class is the entry point into the app. It lets the user login then takes them to the
 * corresponding screen. Each type of user will have a specific type of screen
 * available to them.
 *
 * @author Biruk Mengistu, Mya Hartman, Elena Lam
 */
public class MainPortal {

    // The currently logged in user.
    public static User user;

    // The manager that will be dealing with all user related actions.
    public static final UserManager USER_MANAGER = new UserManager();

    // The scanner that will be receiving input from the user.
    public static final Scanner scanner = new Scanner(System.in);

    // main method
    public static void main(String[] args) {
        print("   Welcome to the student portal.   ");

        // Let user login or sign up
        user = getUser();

        print("Currently logged in as _ (_)", user.getUsername(), user.getType());

        // The appropriate screen based on the type of user.
        Screen currentScreen = switch (user.getType()) {
            case TEACHER -> new TeacherScreen();
            case STUDENT -> new StudentScreen();
            case PARENT  -> new ParentScreen();
        };

        // Start the screen
        currentScreen.start();
    }

    /**
     * Lets the user login or sign up then retrieves the user object.
     *
     * @return the user
     */
    private static User getUser() {
        print("1. Login  2. Signup  3. Exit");
        return switch (scanner.next().toLowerCase()) {
            case "1" -> signIn();
            case "2" -> signUp();
            case "3" -> {
                System.exit(0);
                yield null;
            }
            default -> {
                print("Please enter an option from the choices below");
                yield getUser();
            }
        };
    }

    /**
     * This method lets the user sign up then returns the user
     *
     * @return the user object
     */
    private static User signUp() {
        String type = requestData("type");
        String year = "N/A";

        if (type.toLowerCase().equals("student"))
            year = requestData("year");
        else if (type.toLowerCase().equals("teacher"))
            year = requestData("year");

        String username = requestData("username");
        if (USER_MANAGER.userExists(username)) {
            print("This username has already been taken. Please try another one.");
            return signUp();
        }
        String password = requestData("password");
        User user = new User(username, password, type, User.parseYear(year));
        USER_MANAGER.signUp(user);
        return USER_MANAGER.getUser(username);
    }

    /**
     * This method logs the user out and asks them to login or sign up.
     */
    public static void logOut() {
        print("Logging out...");
        main(null);
    }

    /**
     * This is an important method. It will be handling all the inputs that we will be receiving from the user.
     * <p>
     * The input can be of type:
     * 1. Password: A password to login or sign up with
     * 2. Username: A username to login or sign up with.
     * 3. Type: Whether or not the user is a student, parent or teacher.
     * 4. Choice: A list of numbers that the user can choose from.
     * <p>
     * Note: This method will be used in all of the screens as well since they extend this class.
     *
     * @param type:         the type of input that will be requested.
     * @param numOfChoices: If <code>type</code> is "choice", then the second optional argument will be the number of choices
     *                      for the user to select from.
     * @return the appropriate input entered by the user.
     */
    public static String requestData(String type, int... numOfChoices) {
        return (switch (type.toLowerCase()) {
            case "password", "username" -> { // We are requesting a username or password here
                print("Please enter your _:", type);
                String input = scanner.next();
                if (input.length() < 5) {
                    print("The _ you entered is too short.", type);
                    yield requestData(type);
                }
                yield input;
            }
            case "type" -> { // We are requesting user type here
                print("Are you a student, teacher, or parent? (Please enter one of these choices.)");
                String input = scanner.next().toLowerCase();
                yield switch (input) {
                    case "student", "teacher", "parent" -> input;
                    default -> {
                        print("You have to choose from the listed options.");
                        yield requestData(type);
                    }
                };
            }
            case "choice" -> { // We are requesting a choice between a set of numbers here
                int input;
                try {
                    input = Integer.parseInt(scanner.next());
                } catch (Exception e) {
                    print("Please enter a number!");
                    yield requestData(type, numOfChoices);
                }
                if (input <= numOfChoices[0] && input > 0) yield input + "";
                else {
                    print("Please enter a number between 1 and _", numOfChoices[0]);
                    yield requestData(type, numOfChoices);
                }
            }
            case "year" -> {
                print("Please select your year from the choices below:");
                print("1. Freshman  2. Sophomore  3. Junior  4. Senior");
                yield switch (scanner.next()) {
                    case "1" -> "Freshman";
                    case "2" -> "Sophomore";
                    case "3" -> "Junior";
                    case "4" -> "Senior";
                    default -> {
                        print("Please select from the options below!");
                        yield requestData(type);
                    }
                };
            }
            default -> scanner.next(); // All other requests
        })
                .trim()
                .replaceAll(" ", "_"); // Just making sure that there is no whitespace in the input entered by the user
    }

    /**
     * Lets the user sign in.
     *
     * @return the signed in user
     */
    private static User signIn() {
        String username = requestData("username");
        if (!USER_MANAGER.userExists(username)) {
            print("User does not exist.");
            return signIn();
        }
        String password = requestData("password");
        if (!USER_MANAGER.isValid(username, password)) {
            print("Username and password do not match.");
            return signIn();
        }
        return USER_MANAGER.getUser(username);
    }

    /**
     * This is a helpful method that will help format text that will be outputted onto the screen.
     * It serves three purposes:
     * <p>
     * 1. Prints out a line separator by simply typing three spaces.
     * 2. prints a newline escape character/ "\n" by typing two spaces
     * 3. Puts in strings in place of "_". This makes it easier to concatenate or join many strings together.
     *
     * @param string:    the string to which we will be inserting strings into, also the string to be finally printed.
     * @param toBeAdded: the strings that will be inserted into @param string
     */
    public static void print(Object string, Object... toBeAdded) {
        System.out.printf(((String) string)
                        .replaceAll("   ", "\n------------------------------------\n")
                        .replaceAll("  ", "\n")
                        .replaceAll("_", "%s") + "\n",
                toBeAdded
        );
    }
}
