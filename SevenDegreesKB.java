/* Programming Assignment: Degrees of Kevin Bacon
 * 
 * FILENAME: SevenDegreesKB.java
 * 
 * Author: Ward McKinley
 * 
 * Description: Given a text document, create a program that finds the degrees of freedom an actor is from Kevin Bacon. 
 *    Also include functionality to add/delete entries in the document
 * 
 * Honor Code: I've followed and abided by NOVA's honor code for this project.
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
        Scanner scnr = new Scanner(System.in);
        String option = "o";
        String actorName;

        while (!option.equals("Q") || !option.equals("q")){
            System.out.println("==============MENU=================");
            System.out.println("Select Options (Enter Q to Quit):");
            System.out.println("1. Bacon Degrees of Freedom");
            System.out.println("2. Add Entry");
            System.out.println("3. Delete Entry");
            System.out.println("===================================");
            option = scnr.nextLine();

            if(option.equals("Q") || option.equals("q")){
                break;
            }

            //add options/functionality for the user to select from
            switch (option) {
                case "1":
                    //calcBaconNum
                    System.out.println("Enter actor:");
                    actorName = scnr.nextLine();
                    System.out.println();
                    ArrayList<String> comparedActors = new ArrayList<String>();
                    int counter = 1;

                    System.out.println( actorName + " has Bacon Number : " + calcBaconNumber(actorName, readList(), comparedActors, counter));
                    System.out.println();
                    break;

                case "2":
                    //add entry
                    System.out.println("Enter the actor's first and last name seperated by a space:");
                    String name = scnr.nextLine();
                    System.out.println("Now enter the movie they were in:");
                    String movie = scnr.nextLine();
                    System.out.println("Finally, enter the year the movie released:");
                    String year = scnr.nextLine();
                    boolean append = true;

                    addEntry(name, movie, year, append);
                    System.out.println();
                    break;
                
                case "3":
                    //delete entry
                    System.out.println(deleteEntry(readList(), scnr));
                    System.out.println();
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
                try{
                    currWord = inFS.nextLine();
                    name = currWord.split(" ", 3);
                    actorList.add(new ArrayList<String>());

                    //add f & l name to first index in inner arr
                    actorList.get(counter).add(name[0] + " " + name[1]);

                    //add movie
                    movie = name[2].substring(0, name[2].length()-4).trim();
                    actorList.get(counter).add(movie);

                    //add year
                    year = name[2].substring(name[2].length()-4, name[2].length());
                    actorList.get(counter).add(year);

                    counter++;
                }
                catch(ArrayIndexOutOfBoundsException arr){
                    continue;
                }
            }
            inFS.close();
        }
        catch(FileNotFoundException fnf){
            System.out.println(fnf.getMessage());
        }

        return actorList;
    }


    public static int calcBaconNumber(String actorName, ArrayList<ArrayList<String>> list, ArrayList<String> comparedActors, int counter){
        //TODO: add year to the print out

        ArrayList<String> actorMovies = new ArrayList<String>();
        ArrayList<String> kevinBaconMovies = new ArrayList<String>();
        comparedActors.add(actorName);

        //get all of the actor's movies
        for(int outer = 0; outer < list.size(); outer++){
            //if actorName in the list equals the user entry, get all the movies that actor has been in
            if(list.get(outer).get(0).equals(actorName)){
                actorMovies.add(list.get(outer).get(1));
            } else if (outer == list.size()-1 && actorMovies.size() == 0){
                System.out.println("Actor not found in list");
                return -1;
            }
        }

        //get all of Kevin Bacons movies
        for(int outer = 0; outer < list.size(); outer++){
            if(list.get(outer).get(0).equals("Kevin Bacon")){
                kevinBaconMovies.add(list.get(outer).get(1));
            }
        }

        //iterate through actor movie array while also iterating through kevin bacon array
        for(String movies : actorMovies){
            for(String kbMovies : kevinBaconMovies){
                if(movies.equals(kbMovies)){
                    System.out.println(actorName + " acted with Kevin Bacon in the movie " + movies);
                    return counter;
                }
            }
        }

        //increase bacon score by one since match wasn't found
        counter++;

        //recursively go through list again to find the next actor 
        for(String movies : actorMovies){
            for(int outer = 0; outer < list.size(); outer++){
                //if there is a match with the movie, and the actor isn't the same actor, and hasn't already been compared before
                if(list.get(outer).get(1).equals(movies) && !list.get(outer).get(0).equals(actorName) && !comparedActors.contains(list.get(outer).get(0))){
                    System.out.println(actorName + " acted with " + list.get(outer).get(0) + " in the movie " + list.get(outer).get(1));
                    return calcBaconNumber(list.get(outer).get(0), list, comparedActors, counter);
                }
            }
        }

        return 0;
    }

    public static void addEntry(String name, String movie, String year, boolean append){

        try{
            if(append == false){
                FileOutputStream fileStream = new FileOutputStream("list.txt", append);
                PrintWriter outFS = new PrintWriter(fileStream);
                outFS.print(name + " " + movie + " " + year);
                outFS.close();
            } else {
                FileOutputStream fileStream = new FileOutputStream("list.txt", append);
                PrintWriter outFS = new PrintWriter(fileStream);
                
                //write to file
                outFS.println();
                outFS.print(name + " " + movie + " " + year);
                outFS.close();
            }
            
        }
        catch(Exception excpt){
            System.out.println(excpt.getMessage());
        }
        
    }

    public static String deleteEntry(ArrayList<ArrayList<String>> list, Scanner scnr){
        String actor;
        String movie;
        String year;

        System.out.println("Enter actor to be deleted: ");
        actor = scnr.nextLine();
        System.out.println("Enter movie to be deleted:");
        movie = scnr.nextLine();
        System.out.println("Enter year of movie to be deleted:");
        year = scnr.nextLine();

        //iterate through list of movies 
        for(int outer = 0; outer < list.size(); outer++){
            //if found a match in the array remove it from the array
            if(list.get(outer).get(0).equals(actor) && list.get(outer).get(1).equals(movie) && list.get(outer).get(2).equals(year)){
                list.remove(outer);
                break;
            } else if (outer == list.size()-1){
                return "Could not find entry, please try again";
            }
        }

        //delete file contents and add first entry in the list to the document
        addEntry(list.get(0).get(0), list.get(0).get(1), list.get(0).get(2), false);
        //rewrite the rest of the file with entry removed
        for(int outer = 1; outer < list.size(); outer++){
            addEntry(list.get(outer).get(0), list.get(outer).get(1), list.get(outer).get(2),true);
        }

        return "Entry successfully deleted";
    }
 }