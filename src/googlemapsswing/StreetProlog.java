/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package googlemapsswing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivana Clairine
 */
public class StreetProlog {
    
    HashMap<String, ArrayList<String>> maps;
    HashMap<ArrayList<String>, ArrayList<String>> coordinate;
    ArrayList<ArrayList<String>> listOfPath;
    
    public StreetProlog(){
        maps = new HashMap<>();
        coordinate = new HashMap<>();
        listOfPath = new ArrayList<>();
    }
    
    public ArrayList<ArrayList<String>> doSearchPathCoordinate (String start, String goal) {
        ArrayList<ArrayList<String>> retCoordinate = new ArrayList<>();
        searchPath(start, goal);
        ArrayList<String> recommendedPath = getRecommendedPath();
        for (int i=0; i<recommendedPath.size()-1; i++) {
            retCoordinate.add(coordinate.get(new ArrayList(Arrays.asList(recommendedPath.get(i), recommendedPath.get(i+1)))));
        }
        System.out.println(retCoordinate);
        return retCoordinate;
    }
    
    public ArrayList<String> getRecommendedPath(){
        int idx = 0;
        for (int i=1; i<listOfPath.size(); i++) {
            if (listOfPath.get(i).size() < listOfPath.get(idx).size()) {
                idx = i;
            }
        }
        return (ArrayList<String>) listOfPath.get(idx).clone();
    }

    public static void main(String[] args) {
       StreetProlog test = new StreetProlog();
        try {
            test.readInputFile();
            String start = "";
            String goal = "";
            Scanner in = new Scanner(System.in);
            System.out.println("Masukkan pencarian (X,Y): ");
            String input = in.nextLine();
            input = input.substring(1, input.length()-1);
            List<String> inputSplitted = Arrays.asList(input.split(","));
            start = inputSplitted.get(0);
            goal = inputSplitted.get(1);
//            test.searchPath(start, goal);
            System.out.println(test.doSearchPathCoordinate(start, goal));
            
        } catch (IOException ex) {
            Logger.getLogger(StreetProlog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchPath(String start, String goal){
        ArrayList<String> visited = new ArrayList<>();

	// Create an array to store paths
	 ArrayList<String> path = new ArrayList<>();

	// Call the recursive helper function to print all paths
	searchAllPathsUtil(start, goal, visited, path);
    }
    
    public void searchAllPathsUtil (String start, String goal, ArrayList<String> visited, ArrayList<String> path) {
        // Mark the current node and store it in path[]
	visited.add(start);
	path.add(start);
        // If current vertex is same as destination, then print
	// current path[]
//        System.out.println("start, goal: " + start + ", " + goal);
        if (start.equals(goal))
        {
            for (int i = 0; i<path.size(); i++)
                System.out.print(path.get(i) + ' ');
            listOfPath.add((ArrayList<String>) path.clone());
            System.out.println("");
        }
        else // If current vertex is not destination
        {
            // Recur for all the vertices adjacent to current vertex
            ArrayList<String> pathToEvaluate = maps.get(start);

//            System.out.println("pathToEvalute: " + pathToEvaluate);
            
            if(pathToEvaluate!=null) {
                for(int i=0; i<pathToEvaluate.size(); i++){
                    if(!visited.contains(pathToEvaluate.get(i))){
                        searchAllPathsUtil(pathToEvaluate.get(i), goal, visited, path);
                    }
                } 
            }
        }
        
        path.remove(path.size()-1);
	visited.remove(start);
    }
    
    public void readInputFile() throws FileNotFoundException, IOException{
        String file = "streetConnection2Hub.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase();
                String[] splited = line.split(" # ");
//                System.out.println("splited: " + splited[0] + ", " + splited[1] + ", " + splited[2]);
                if(maps.containsKey(splited[0])){
                    maps.get(splited[0]).add(splited[1]);
                } else{
                    ArrayList<String> value = new ArrayList<>();
                    value.add(splited[1]);
                    maps.put(splited[0], value);
                }
                ArrayList<String> streetHub = new ArrayList<>();
                ArrayList<String> coordinateTemp = new ArrayList<>();
                streetHub.add(0, splited[0]);
                streetHub.add(1, splited[1]);
                coordinateTemp.add (0, splited[2].split(", ")[0]);
                coordinateTemp.add (1, splited[2].split(", ")[1]);
                coordinate.put(streetHub, coordinateTemp);
            }
        }
    }
}
