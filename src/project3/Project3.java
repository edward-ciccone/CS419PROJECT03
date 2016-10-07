package project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Program takes in input from a user, and returns the ranking of the b aby name
 * for a given year. User is prompted to enter the year for the baby name
 * ranking, the gender of the baby name, and finally is asked for the baby name
 * in which it wants to look up.
 *
 * @author edward
 */
public class Project3 {

    /**
     * Creates Scanner object, and opens Scanner to get input from user.
     */
    public static Scanner input = new Scanner(System.in);

    /**
     * Creates Scanner object, and initializes to null, used to scan a file for
     * a specific name.
     */
    public static Scanner fileScanner = null;

    /**
     * The File object that is scanned for a specific name.
     */
    public static File file;

    /**
     * Asks the user for the year, gender, and name, and then attempts to find
     * the name.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            int year = setAndGetYear();
            String gender = setAndGetGender();
            String name = setAndGetName();
            findName(name, year, gender);
        } catch (InputMismatchException ex) {
            System.out.println("Year must be entered as an Integer.");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            /* Closes user input Scanner object. */
            input.close();
        }
    }

    /**
     * Asks the user for the baby name to look for and then returns this name.
     *
     * @return the baby name to look up
     */
    private static String setAndGetName() {
        System.out.println("Enter the name: ");
        String name = input.next();
        return name;
    }

    /**
     * Asks the user for the gender of the baby and then returns it.
     *
     * @return the baby gender
     * @throws IOException if gender doesn't equal m or f, throws IOException.
     */
    private static String setAndGetGender() throws IOException {
        System.out.println("Enter the gender: ");
        String gender = input.next();
        if (!gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f")) {
            throw new IOException("Gender must be m (male) or f (female).");
        }
        return gender;
    }

    /**
     * Asks the user for the year of the records and then returns it.
     *
     * @return the year of the record
     * @throws FileNotFoundException  if no record for that year is found,
     *                                throws FileNotFoundException
     * @throws InputMismatchException if year is not entered as an integer,
     *                                throws InputMismatchException
     */
    private static int setAndGetYear() throws FileNotFoundException, InputMismatchException {
        System.out.println("Enter the year: ");
        int year = input.nextInt();
        file = new File("Babynamesranking" + year + ".txt");
        /* Opens scanner to the name records if file exists, otherwise throws a FileNotFoundException. */
        if (file.exists()) {
            fileScanner = new Scanner(file);
        } else {
            throw new FileNotFoundException("No records for year " + year + " were found.");
        }
        return year;
    }

    /**
     * Uses a Scanner for the appropriate file record, and searches for the name
     * of the baby; male names and female names are searched differently, so the
     * name is dependent on the gender.
     *
     * @param name   the name to search for
     * @param year   the year of the record
     * @param gender the gender of the baby
     */
    private static void findName(String name, int year, String gender) {
        /* If true the baby is male. */
        boolean male = gender.equalsIgnoreCase("m");
        /* If true the baby is female. */
        boolean female = gender.equalsIgnoreCase("f");
        /* While not all names have been searched it continues looping. */
        while (fileScanner.hasNext()) {
            /* Rank of the name is the first element for both males and females. */
            int rank = fileScanner.nextInt();
            String currentName = "";

            /* Male names appear as the second element */
            if (male) {
                currentName = fileScanner.next();
                fileScanner.next();
                fileScanner.next();
                fileScanner.next();
            }

            /* Female names appear as the fourht element. */
            if (female) {
                fileScanner.next();
                fileScanner.next();
                currentName = fileScanner.next();
                fileScanner.next();
            }

            /* If the name was found the rank is displayed to the user. */
            if (currentName.equalsIgnoreCase(name)) {
                System.out.println(currentName + " is ranked #" + rank + " in year " + year);
                break;
            }

            /* If the name was not found a message is sent to the user. */
            if (!fileScanner.hasNext()) {
                System.out.println("The name " + name + " is not ranked in year " + year);
            }
        }
        /* Closes the Scanner to the name records. */
        fileScanner.close();
    }
}
