package controller;

import model.*;
/**
 * Created by anonymous on 10.05.2017.
 */
public class Controller {
    private DataBase dataBase;

    public DataBase getDataBase() {
        return dataBase;
    }

    public Controller(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public int size() {
        return dataBase.size();
    }

    public Object get(int i) {
        return dataBase.get(i);
    }

    public void add(Double d){
        dataBase.add(d);
    }
}
