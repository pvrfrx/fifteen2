package com.company;

import com.company.AStar.State;

import java.util.ArrayList;

public class Robot {


    public static void main(String[] args) {
        Main main = new Main(3);
        State startPosition = new State(main.board, 0);
        AStar aStar = new AStar(startPosition);
        int count = 0;
        while (count<1000) {
            State currentPosition = aStar.findCurrentPosition();
         //   if (count==308) aStar.printCurrentPosition();
            aStar.printCurrentPosition();
            aStar.fromOpenToCloseList(currentPosition);
            ArrayList<State> neighbors = null;
            try {
                neighbors = aStar.findNeighbors(currentPosition);
            } catch (NullPointerException e) {
                System.out.println(count);
                e.printStackTrace();
            }
            aStar.putOnOpenList(neighbors);
        //    if (count==308) aStar.printAStar();
            aStar.printAStar();
            if (currentPosition.getH()==0) {
                System.out.println("WIN");
                break;
            }
            else count++;
       }
    }
}
