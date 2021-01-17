package com.datastructures.Objects;

public class User {
    /**
     * An enum that encompasses all the different types of users.
     */
    public enum Type {
        STUDENT("Student"),
        TEACHER("Teacher"),
        PARENT("Parent");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    public enum Year {
        FRESHMAN("Freshman"), SOPHOMORE("Sophomore"),
        JUNIOR  ("Junior"  ), SENIOR   ("Senior"   ),
        NOT_APPLICABLE("N/A");

        private final String value;

        Year(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    // Data fields, constructors and setter and getter methods...
    String username;
    String password;
    Type type;
    Year year;

    // Constructors
    public User(String username, String password, Type type, Year year) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.year = year;
    }

    public User(String username, String password, Type type) {
        this(username,password,type,Year.NOT_APPLICABLE);
    }

    public User(String username, String password, String type,Year year) {
        this( username, password, switch (type.toLowerCase()) {
            case "teacher" -> Type.TEACHER;
            case "parent"  -> Type.PARENT;
            default -> Type.STUDENT;
        },year);
    }

    // Getter methods.
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Type getType() {
        return type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    /**
     * This method converts a given string to a Year enum
     * @param s: the string to be converted
     * @return the appropriate <code>Year</code> enum
     */
    public static Year parseYear(String s){
        return switch (s.toLowerCase().trim()) {
            case "freshman" -> User.Year.FRESHMAN;
            case "sophomore" -> User.Year.SOPHOMORE;
            case "junior" -> User.Year.JUNIOR;
            case "senior" -> User.Year.SENIOR;
            default -> User.Year.NOT_APPLICABLE;
        };
    }
}
