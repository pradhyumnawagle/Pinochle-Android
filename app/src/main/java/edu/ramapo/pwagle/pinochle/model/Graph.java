/*************************************************************
 * Name:  Pradhyumna Wagle                                    *
 * Project:  Project 1 Pinochle Java Android                  *
 * Class:  CMPS 366 01 OPL									  *
 * Date:  12/09/2020										  *
 *************************************************************/


package edu.ramapo.pwagle.pinochle.model;

import java.util.ArrayList;

public class Graph {

    private int V;
    private int index = 0;

    private ArrayList<ArrayList<Integer>> pairs  = new ArrayList<ArrayList<Integer>>();

    private ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();

    /**
     This is the default constructor for the Graph Class
     */
    public Graph(){

    }

    /**
     This is the parameterized constructor for the Graph Class
     @param v int, the number of vertices for the graph
     */
    public Graph(int v) {
        V = v;
        initializePairs();
    }

    /**
     This function initializes an the member variable pairs with V(number of vertices) array lists
     */
    public void initializePairs(){
        for (int i = 0; i < V; i++) {
            pairs.add(new ArrayList<Integer>());
        }
    }

    /**
     This function adds an edge in the array containing adjacency lists
     @param first int, the integer value whose list we are accessing
     @param second int, the integer value to be added into 'first's' list
     */
    public void addEdge(int first, int second){
        pairs.get(first).add(second);
    }

    /**
     This function evaluates all paths from vertex s to vertex d
     @param s int, source vertex as an integer
     @param d int, destination vertex as an integer
     */
    public void evaluateAllPaths(int s, int d){

        ArrayList<Integer> vertexVisited = new ArrayList<Integer>();
        ArrayList<Integer> path = new ArrayList<Integer>();

        index = 0;

        for(int i=0;i<V;i++){
            vertexVisited.add(0);
        }

       getPaths(vertexVisited, path, s,d);
    }


    /**
     This function evaluates all paths from vertex s to vertex d
     @param vertexVisited ArrayList<Integer>, an array that stores if a vertex has been visited
     @param path ArrayList<Integer>, an index in path array
     @param s int, source vertex as an integer
     @param d int, destination vertex as an integer
     */
    private void getPaths(ArrayList<Integer> vertexVisited, ArrayList<Integer> path, int s, int d ){

        vertexVisited.set(s,1);

        if(path.size() == index){
            path.add(0);
        }
        path.set(index,s);

        index += 1;

        // If destination reached, add to vector
        if(s==d){
            ArrayList<Integer> singlePath = new ArrayList<Integer>();
            for(int i=0;i<index;i++){
                singlePath.add(path.get(i));
            }
            paths.add(singlePath);
        }

        //If destination not reached
        else{
            //for all adjacent nodes, recursively call self
            for (Integer i : pairs.get(s)) {
                if ((int) vertexVisited.get(i) == 0) {
                    getPaths(vertexVisited, path, i, d);
                }
            }

            /*for(int i=0;i<pairs.get(s).size();i++){
                Integer intVal = pairs.get(s).get(i);
                if((int)vertexVisited.get(intVal) == 0){
                    getPaths(vertexVisited,path,pairs.get(s).get(i),d);
                }
            }*/
        }

        vertexVisited.set(s,0);
        index -= 1;
    }

    /**
     This function returns an ArrayList with all paths generated
     @return An array list containing array lists of integers that stores all paths
     */
    public ArrayList<ArrayList<Integer>> getAllPaths(){
        return paths;
    }

}
