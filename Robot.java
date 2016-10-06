package com.company;

import com.company.AStar.State;

import java.util.ArrayList;

public class Robot {















/*d) Останавливаемся если:

Добавили целевую клетку в открытый список, в этом случае путь найден.
Или открытый список пуст и мы не дошли до целевой клетки. В этом случае путь отсутствует.
3) Сохраняем путь. Двигаясь назад от целевой точки, проходя от каждой точки к ее родителю до тех пор, пока не дойдем до стартовой точки. Это и будет наш путь. */

    public static void main(String[] args) {
        Main main = new Main(3);
        State startPosition = new State(main.board, 0);
        AStar aStar = new AStar(startPosition); //1) Добавляем стартовую клетку в открытый список.
        int count = 0;
        while (count<2100000) { //2) Повторяем следующее:
            State currentPosition = aStar.findCurrentPosition(); //a) Ищем в открытом списке клетку с наименьшей стоимостью F. Делаем ее текущей клеткой.
         //   if (count==308) aStar.printCurrentPosition();
         //   aStar.printCurrentPosition();
            aStar.fromOpenToCloseList(currentPosition); //b) Помещаем ее в закрытый список. (И удаляем с открытого)
            ArrayList<State> neighbors = null;
            try {
                neighbors = aStar.findNeighbors(currentPosition);
                for (State neighbor :                   //c) Для каждой из соседних 8-ми клеток ...
                        neighbors) {                    //Если клетка непроходимая или она находится в закрытом списке, игнорируем ее.   //учтено в findNeighbors()
                    //В противном случае делаем следующее.
                    aStar.putOnOpenList(neighbor);     //Если клетка еще не в открытом списке, то добавляем ее туда.
                    //Делаем текущую клетку родительской для это клетки.  //учтено в findNeighbors()
                    //Расчитываем стоимости F, G и H клетки.              //учтено при создании элемента класса State()
                    //Если клетка уже в открытом списке, то проверяем, не дешевле ли будет путь через эту клетку.
                    // Для сравнения используем стоимость G. Более низкая стоимость G указывает на то, что путь будет дешевле.
                    // Эсли это так, то меняем родителя клетки на текущую клетку и пересчитываем для нее стоимости G и F.
                    // Если вы сортируете открытый список по стоимости F, то вам надо отсортировать свесь список в соответствии с изменениями.



                }
            } catch (NullPointerException e) {
                System.out.println(count);
                e.printStackTrace();
            }




        //    if (count==308) aStar.printAStar();
         //  aStar.printAStar();



            if (aStar.win()) {    //d) Останавливаемся если:
                System.out.println("WIN");      //Добавили целевую клетку в открытый список, в этом случае путь найден.
                System.out.println("Количество ходов - "+ (count+1));//Или открытый список пуст и мы не дошли до целевой клетки. В этом случае путь отсутствует.
                break;
            }
            else if (aStar.lose()){
                System.out.println("LOSE");
                break;
            }
            else count++;
            if (count == 2100000) System.out.println("Не нашли решение за "+(count+1)+" ходов");
            if (count%1000 == 0) System.out.println(count);
       }
    }
}
