/* 7 Degrees of Kevin Bacon
 * Ward McKinley
 * 
 * User presented w/3 options
 * 1) Calculate actor's Bacon Number
 * 2) Add entry to the list
 * 3) Delete an entry from the list
 * 
 * Output for option 1 printed on the screen
 * Result of addition/deletion instantly reflects in text file
 * 
 * 
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class SevenDegreesKB{
    public static void main(String[] args){
        //TODO: Add methods for each option
        Scanner scnr = new Scanner(System.in);
        String option = "o";
        String actorName;

        while (!option.equals("Q") || !option.equals("q")){
            System.out.println("Select Options (Enter Q to Quit):");
            System.out.println("1. Bacon Degrees of Freedom");
            System.out.println("2. Add Entry");
            System.out.println("3. Delete Entry");
            option = scnr.nextLine();

            if(option.equals("Q") || option.equals("q")){
                break;
            }

            switch (option) {
                case "1":
                    //calcBaconNum
                    System.out.println("Enter actor:");
                    actorName = scnr.nextLine();
                    calcBaconNumber(actorName, readList());
                    break;
                case "2":
                    //add entry
                    addEntry(scnr);
                    break;
                
                case "3":
                    //delete entry
                    break;
                
                default:
                    System.out.println("Please enter a value above");
            }
        }
        


    }

    public static ArrayList<ArrayList<String>> readList(){
        ArrayList<ArrayList<String>> actorList = new ArrayList<ArrayList<String>>();
        String currWord;
        String[] name;
        String movie;
        String year;
        int counter = 0;
        try{
            FileInputStream fileByteStream = new FileInputStream("list.txt");
            Scanner inFS = new Scanner(fileByteStream);

            while (inFS.hasNext()){
                currWord = inFS.nextLine();
                name = currWord.split(" ", 3);
                actorList.add(new ArrayList<String>());

                //add f & l name to first index in inner arr
                actorList.get(counter).add(name[0] + " " + name[1]);

                //add movie
                movie = name[2].substring(0, name[2].length()-4);
                actorList.get(counter).add(movie);

                //add year
                year = name[2].substring(name[2].length()-4, name[2].length());
                actorList.get(counter).add(year);

                counter++;
            }
            inFS.close();
        }
        catch(FileNotFoundException fnf){
            System.out.println(fnf.getMessage());
        }

        return actorList;

    }

    public static int calcBaconNumber(String actorName, ArrayList<ArrayList<String>> list){
        //TODO: Given an entry, calculate the bacon number. Add input errors. Recursion?
        int counter = 1;
        ArrayList<String> actorMovies = new ArrayList<String>();
        ArrayList<String> kevinBaconMovies = new ArrayList<String>();

        //get all of the actor's movies
        for(int outer = 0; outer < list.size(); outer++){
            //if actorName in the list equals the user entry, get all the movies that actor has been in
            if(list.get(outer).get(0).equals(actorName)){
                actorMovies.add(list.get(outer).get(1));
            }
        }

        //get all of Kevin Bacons movies
        for(int outer = 0; outer < list.size(); outer++){
            //if actorName in the list equals the user entry, get all the movies that actor has been in
            if(list.get(outer).get(0).equals("Kevin Bacon")){
                kevinBaconMovies.add(list.get(outer).get(1));
            }
        }



        //test arraylist
        for(int i = 0; i < kevinBaconMovies.size(); i++){
            System.out.println(kevinBaconMovies.get(i));
        }


        return 0;
    }

    public static void addEntry(Scanner scnr){
        String name;
        String movie;
        String year;


        try{
            FileOutputStream fileStream = new FileOutputStream("list.txt", true);
            PrintWriter outFS = new PrintWriter(fileStream);
            System.out.println("Enter the actor's first and last name seperated by a space:");
            name = scnr.nextLine();
            System.out.println("Now enter the movie they were in:");
            movie = scnr.nextLine();
            System.out.println("Finally, enter the year the movie released:");
            year = scnr.nextLine();

            //write to file
            outFS.println();
            outFS.print(name + " " + movie + " " + year);
            outFS.close();
        }
        catch(Exception excpt){
            System.out.println(excpt.getMessage());
        }
        
    }

    public static void deleteEntry(){
        //TODO: Finish out method to delete entry from file
    }

 }