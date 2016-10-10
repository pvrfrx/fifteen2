package com.company;

import com.company.AStar.State;

import java.util.ArrayList;
import java.util.Date;

public class Robot {
    public static void main(String[] args) {
        Main main = new Main(3);
        State startPosition = new State(main.board, 0);
        AStar aStar = new AStar(startPosition); //1) Добавляем стартовую клетку в открытый список.
        int count = 0;
        System.out.println("Старт = " + new Date());
        while (true) {
            State currentPosition = aStar.findCurrentPosition();
            aStar.fromOpenToCloseList(currentPosition);
         //   aStar.printCurrentPosition();
            ArrayList<State> neighbors = null;
            neighbors = aStar.findNeighbors(currentPosition);
            for (State neighbor :
                    neighbors) {
                aStar.putOnOpenList(neighbor);
            }
        //  aStar.printAStar();
            if (aStar.win()) {
                System.out.println("WIN");
                System.out.println("Количество ходов - "+ (count+1));
                break;
            }
            else if (aStar.lose()){
                System.out.println("LOSE");
                break;
            }
            else if (count >= 2100000) {
                System.out.println("Не нашли решение за "+(count+1)+" ходов");
                break;
            }
            else count++;
            if (count%1000 == 0 && count!=0) System.out.println(count);
       }
       System.out.println("Финиш = " + new Date());
    }
}
