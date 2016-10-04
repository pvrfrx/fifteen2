package com.company;

import java.util.ArrayList;

public class AStar {
    private ArrayList<State> openList = new ArrayList<>();
    private ArrayList<State> closeList = new ArrayList<>();
    private State startPosition;

    public State getStartPosition() {
        return this.startPosition;
    }

    public void addInOpenList(State state){
        this.openList.add(state);
    }

    public AStar(State startPosition){
        this.startPosition = startPosition;
        this.addInOpenList(startPosition);
    }

    public ArrayList<State> findNeighbors(State state){
        ArrayList<State> result = new ArrayList<>();
        ArrayList<Integer> possibleStep = state.findPossibleStep();
        for (int i :
                possibleStep) {
            result.add(new State(Main.move(i,state.currentState),state.getG()+1,state));
        }
        return result;
    }

    public void putOnOpenList(ArrayList<State> states){

    }

    public void fromOpenToCloseList(State state){
        closeList.add(state);
        openList.remove(state);
    }

    public void printAStar(){
        System.out.println("!!!!!!Текущее состояние поиска!!!!!");
        System.out.println();
        System.out.println("Закрытый список");
        int k=1;
        for (State i :
                closeList) {
            System.out.println("Элемент номер "+k+". G = "+i.getG()+". H = "+i.getH()+". F = "+i.getF());
            Main.draw(i.currentState);
            System.out.println();
            k++;
        }
        k=1;
        System.out.println("Открытый список");
        for (State i :
                openList) {
            System.out.println("Элемент номер "+k+". G = "+i.getG()+". H = "+i.getH()+". F = "+i.getF());
            Main.draw(i.currentState);
            System.out.println();
            k++;
        }
    }

    public State findCurrentPosition(){
        int min = Integer.MAX_VALUE;
        State result = new State();
        for (State state :
                this.openList) {
            if (state.getF() < min) {
                result = state;
                min = state.getF();
            }
        }
        if (min == Integer.MAX_VALUE) return null;
        else return result;
    }

    static public class State {
        private int[][] currentState;
        private int g = 0; //расстояние от начальной вершины до текущей
        private int h = Integer.MAX_VALUE/2; //эвристическое предположение о расстоянии от текущей вершины, до терминальной
       // private int f; //вес вершины
        private State parent;
        public State(){

        }
        public State(int[][] currentState, int g){
            this.currentState = currentState.clone();
            this.setG(g);
            this.setH(findH(currentState));
            this.setParent(null);
        //    this.setF();
        }
        public State(int[][] currentState, int g, State parent){
            this.currentState = currentState.clone();
            this.setG(g);
            this.setParent(parent);
            this.setH(findH(currentState));
        //    this.setF();
        }

        public int findH(int[][] currentState){
            int result=Main.d*Main.d-1;
            int counter = 1;
            for (int[] y :
                    currentState) {
                for (int x :
                        y) {
                    if (x == counter) result--;
                    counter++;
                }
            }
            return result;
        }

        public int getF() {
            return g + h;
        }

        public int getG() {
            return g;
        }

        public int getH() {
            return h;
        }

      /*  public void setF() {
            this.f = this.g + this.h;
        }*/

        public void setG(int g) {
            this.g = g;
        }

        public void setH(int h) {
            this.h = h;
        }

        public State getParent() {
            return parent;
        }

        public void setParent(State parent) {
            this.parent = parent;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        public ArrayList<Integer> findPossibleStep(){
            ArrayList<Integer> result = new ArrayList<>();
            int[] yxZeroElement = Main.findXYElement(0, this.currentState);
            int yZeroElement = yxZeroElement[0];
            int xZeroElement = yxZeroElement[1];
            if (yZeroElement==0){
                if (xZeroElement == 0){
                    result.add(this.currentState[yZeroElement+1][xZeroElement]);
                    result.add(this.currentState[yZeroElement][xZeroElement+1]);
                }
                else
                if (xZeroElement == Main.d-1){
                    result.add(this.currentState[yZeroElement+1][xZeroElement]);
                    result.add(this.currentState[yZeroElement][xZeroElement-1]);
                }
                else {
                    result.add(this.currentState[yZeroElement+1][xZeroElement]);
                    result.add(this.currentState[yZeroElement][xZeroElement+1]);
                    result.add(this.currentState[yZeroElement][xZeroElement-1]);
                }
            }
            else if (yZeroElement==Main.d-1){
                if (xZeroElement == 0){
                    result.add(this.currentState[yZeroElement-1][xZeroElement]);
                    result.add(this.currentState[yZeroElement][xZeroElement+1]);
                }
                else
                if (xZeroElement == Main.d-1){
                    result.add(this.currentState[yZeroElement-1][xZeroElement]);
                    result.add(this.currentState[yZeroElement][xZeroElement-1]);
                }
                else {
                    result.add(this.currentState[yZeroElement+1][xZeroElement]);
                    result.add(this.currentState[yZeroElement][xZeroElement+1]);
                    result.add(this.currentState[yZeroElement][xZeroElement-1]);
                }
            }
            else {
                if (xZeroElement == 0){
                    result.add(this.currentState[yZeroElement+1][xZeroElement]);
                    result.add(this.currentState[yZeroElement-1][xZeroElement]);
                    result.add(this.currentState[yZeroElement][xZeroElement+1]);
                }
                else if (xZeroElement == Main.d-1){
                    result.add(this.currentState[yZeroElement+1][xZeroElement]);
                    result.add(this.currentState[yZeroElement-1][xZeroElement]);
                    result.add(this.currentState[yZeroElement][xZeroElement-1]);
                }
                else {
                    result.add(this.currentState[yZeroElement+1][xZeroElement]);
                    result.add(this.currentState[yZeroElement-1][xZeroElement]);
                    result.add(this.currentState[yZeroElement][xZeroElement+1]);
                    result.add(this.currentState[yZeroElement][xZeroElement-1]);
                }
            }
            return result;
        }
    }
}
