package model;

import to.AnimalTo;
import to.CustomerTo;
import to.ItemTo;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnimalModel {
        public static boolean addAnimal(AnimalTo Animal) throws SQLException, ClassNotFoundException {
            return CrudUtil.execute("INSERT INTO animal VALUES(?,?,?,?)",
                    Animal.getId(),
                    Animal.getType(),
                    Animal.getQty(),
                    Animal.getDate()
            );
        }

    public static AnimalTo search(String type) throws SQLException, ClassNotFoundException {
        ResultSet rst =CrudUtil.execute("SELECT * FROM animal WHERE type='"+type+"'");
        System.out.println(rst);
        if (rst.next()){
            return new AnimalTo(rst.getString(1),rst.getString(2),rst.getInt(3), rst.getDate(4));
        }
        return null;
    }

    public static boolean updateQtyAnimal(AnimalTo animal) throws SQLException, ClassNotFoundException {
        Boolean i = CrudUtil.execute("UPDATE animal SET qty = ? WHERE type = ?",
                animal.getQty(),
                animal.getType());
        return i;
    }

        public static boolean updateAnimal(AnimalTo animal) throws SQLException, ClassNotFoundException {
            String sql = "UPDATE animal SET id = ? , qty = ? WHERE id = ?";
            return CrudUtil.execute(sql,animal.getId(),animal.getType(),animal.getQty(),animal.getDate());
        }

        public static boolean deleteAnimal(AnimalTo animal) throws SQLException, ClassNotFoundException {
            String sql = "DELETE FROM animal WHERE id = ?";
            return CrudUtil.execute(sql,animal.getId());
        }


        public static ArrayList<AnimalTo> getAllAnimal() throws SQLException, ClassNotFoundException {
        ArrayList<AnimalTo> list = new ArrayList<>();
        ResultSet al = CrudUtil.execute("SELECT * FROM animal");
        while (al.next()){
            AnimalTo animal = new AnimalTo(al.getString(1),al.getString(2),al.getInt(3),al.getDate(4));
            list.add(animal);
        }
        //new CustomerTo("","","","");
        return list;
    }
}




