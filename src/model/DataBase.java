package model;

import java.util.ArrayList;

/**
 * Created by anonymous on 10.05.2017.
 */
public class DataBase {
    public ArrayList<Double> dataArrayList = new ArrayList<>();

    public ArrayList<Double> getArrayList() {
        return dataArrayList;
    }

    public int size() {
        return dataArrayList.size();
    }

    public Object get(int i) {
        return dataArrayList.get(i);
    }

    public void add(Double d) {
        dataArrayList.add(d);
    }
}
