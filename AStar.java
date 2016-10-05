package com.company;

import java.util.ArrayList;

public class AStar {
    private ArrayList<State> openList = new ArrayList<>();
    private ArrayList<State> closeList = new ArrayList<>();
    private State startPosition;
    private State currentPosition;

    public State getStartPosition() {
        return this.startPosition;
    }

    public void addInOpenList(State state){
        this.openList.add(state);
    }

    public AStar(State position){
        this.startPosition = position;
        this.currentPosition = position;
        this.addInOpenList(position);

    }

    public boolean checkStateInList(State stateTo, ArrayList<State> list){
        boolean result = false;
        for (State stateOn :
                list) {
            if (stateOn.equals(stateTo)) result = true;
        }
        return result;
    }
    public ArrayList<State> findNeighbors(State state) throws NullPointerException{
        ArrayList<State> result = new ArrayList<>();
        ArrayList<Integer> possibleStep = state.findPossibleStep();
        for (int i :
                possibleStep) {
            State tempState = new State(Main.move(i,state.currentState),state.getG()+1,state);
            if (!checkStateInList(tempState, this.closeList))   result.add(tempState);
        }
        return result;
    }

    public void putOnOpenList(ArrayList<State> states){
        for (State state :
                states) {
            if (!checkStateInList(state, this.openList)) this.openList.add(state);
          //  else if (state.checkLenght()) state.changeParent();
        }
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
            if (state.parent == null && startPosition.equals(currentPosition)){
                return startPosition;
            }
            else if (state.parent.equals(this.currentPosition)){
                if (state.getF() < min) {
                    result = state;
                    min = state.getF();
                }
            }
        }
        if (min == Integer.MAX_VALUE) return null;
        else {
            this.setCurrentPosition(result);
            return result;
        }
    }

    public void setCurrentPosition(State currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void printCurrentPosition(){
        System.out.println();
        System.out.println("!!!Это текущая позиция!!!");
        this.currentPosition.printState();
        System.out.println();
    }

    static public class State {
        private int[][] currentState;
        private int g; //расстояние от начальной вершины до текущей
        private int h; //эвристическое предположение о расстоянии от текущей вершины, до терминальной
       // private int f; //вес вершины
        private State parent;
        public State(){

        }
        public State(int[][] currentState, int g){
            this.currentState = currentState.clone();
            this.setG(g);
            this.setH(findH(currentState));
            this.setParent(null);
        }

        public State(int[][] currentState, int g, State parent){
            this.currentState = currentState.clone();
            this.setG(g);
            this.setParent(parent);
            this.setH(findH(currentState));
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

        public boolean equals(State obj) {
            //return this.currentState.equals(obj.currentState);
            int y = 0;
            int x = 0;
            for (int[] i :
                    currentState) {
                for (int j :
                        i) {
                    if (j != obj.currentState[y][x]) return false;
                    x++;
                }
                x=0;
                y++;
            }
            return true;
        }

        public ArrayList<Integer> findPossibleStep() throws NullPointerException{
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
                    result.add(this.currentState[yZeroElement-1][xZeroElement]);
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

        public void printState(){
            Main.draw(this.currentState);
        }
    }
}
