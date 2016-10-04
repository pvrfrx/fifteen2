package com.company;

import com.company.AStar.State;

import java.util.ArrayList;

public class Robot {


    public static void main(String[] args) {
        Main main = new Main(3);
       // AStar aStar = new AStar();
        State startPosition = new State(main.board, 0);
        AStar aStar = new AStar(startPosition);
        int count = 0;
        while (count<10) {
            State currentPosition = aStar.findCurrentPosition();
            aStar.fromOpenToCloseList(currentPosition);
            ArrayList<State> neighbors = aStar.findNeighbors(currentPosition);
            aStar.putOnOpenList(neighbors);
            aStar.printAStar();
            if (currentPosition.getH()==0) break;
            else count++;
       }
    }
}
