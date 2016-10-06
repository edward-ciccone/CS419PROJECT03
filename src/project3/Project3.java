/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edward
 */
public class Project3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int year = -1;
        String gender = null;
        String name = null;

        Scanner input = new Scanner(System.in);
        
        try {
            System.out.println("Enter the year: ");
            year = input.nextInt();

            System.out.println("Enter the gender: ");
            gender = input.next();

            if (!gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f")) {
                throw new IOException();
            }

            System.out.println("Enter the name: ");
            name = input.next();
            input.close();

        } catch (InputMismatchException ex) {
            System.out.println("Year must be entered as an Integer.");
        } catch (IOException ex) {
            System.out.println("Gender must be m (male) or f (female).");

        }

        if (gender.equalsIgnoreCase("m")) {
            findMaleName(name, year);
        }
        
        if (gender.equalsIgnoreCase("f")) {
            findFemaleName(name, year);
        } 

    }

    private static void findMaleName(String name, int year) {
        try {
            File file = new File("Babynamesranking" + year + ".txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNext()) {
                int rank = fileScanner.nextInt();
                String currentName = fileScanner.next();

                fileScanner.next();
                fileScanner.next();
                fileScanner.next();

                if (currentName.equalsIgnoreCase(name)) {
                    System.out.println(currentName + " is ranked #" + rank + " in year " + year);
                    break;
                }

                if (!fileScanner.hasNext()) {
                    System.out.println("The name " + name + " is not ranked in year " + year);
                }
            }
            
            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("No records for year " + year + " was found.");
        }

    }

    private static void findFemaleName(String name, int year) {
        try {
            File file = new File("Babynamesranking" + year + ".txt");
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNext()) {
                int rank = fileScanner.nextInt();
                fileScanner.next();

                fileScanner.next();
                String currentName = fileScanner.next();
                fileScanner.next();

                if (currentName.equalsIgnoreCase(name)) {
                    System.out.println(currentName + " is ranked #" + rank + " in year " + year);
                    break;
                }

                if (!fileScanner.hasNext()) {
                    System.out.println("The name " + name + " is not ranked in year " + year);
                }
            }
            
            fileScanner.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("No records for year " + year + " was found.");
        }

    }
}
