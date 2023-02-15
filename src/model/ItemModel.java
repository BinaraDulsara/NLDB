package model;

import tm.CartTM;
import to.AnimalTo;
import to.ItemTo;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
//    public boolean save(ItemTo itemTo) throws SQLException, ClassNotFoundException {
//        boolean i =CrudUtil.execute("INSERT INTO Item VALUES(?,?,?,?,?)",
//                itemTo.getId(),
//                itemTo.getName(),
//                itemTo.getType(),
//                itemTo.getQty(),
//                itemTo.getPrice()
//        );
//        System.out.println(i);
//        return i;
//    }

    public static ItemTo search(String type) throws SQLException, ClassNotFoundException {
        ResultSet rst =CrudUtil.execute("SELECT * FROM Item WHERE name='"+type+"'");
        System.out.println(rst);
        if (rst.next()){
            return new ItemTo(rst.getString(1),rst.getString(2),rst.getInt(3), rst.getDouble(4), rst.getString(5));
        }
        return null;
    }

    public static ArrayList<ItemTo> searchByType(String type) throws SQLException, ClassNotFoundException {
        ResultSet rst =CrudUtil.execute("SELECT * FROM Item WHERE type='"+type+"'");
        System.out.println(rst);
        ArrayList<ItemTo> itemView=new ArrayList<>();

        while(rst.next()){
            itemView.add(
                    new ItemTo(rst.getString(1),
                            rst.getString(2),
                            rst.getInt(3),
                            rst.getDouble(4),
                            rst.getString(5))
            );
        }
        return itemView;
    }


    public static boolean update(ItemTo itemTo) throws SQLException, ClassNotFoundException {
        boolean i =CrudUtil.execute("UPDATE item set name=?, qty=? , price = ? , type = ? where id=?",
                itemTo.getName(),
                itemTo.getQty(),
                itemTo.getType(),
                itemTo.getType(),
                itemTo.getId()
        );
        System.out.println(i);
        return i;
    }

    public static boolean updateQTY(ItemTo itemTo) throws SQLException, ClassNotFoundException {
        boolean i =CrudUtil.execute("UPDATE item set qty=? where name=?",
                itemTo.getQty(),
                itemTo.getName()
        );
        System.out.println(i);
        return i;
    }

    public static boolean removeQty(ArrayList<CartTM> arrayList) throws SQLException, ClassNotFoundException {
        for (CartTM cartTM : arrayList){
            if(!removeQty(cartTM)){
                return false;
            }
        }
        return true;
    }

    private static boolean removeQty(CartTM cartTM) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("update item set qty = qty-? where id = ? ",cartTM.getQty(),cartTM.getItemCode());
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        boolean i=CrudUtil.execute("DELETE FROM item WHERE id='"+id+"'");
        System.out.println(i);
        return i;
    }

    public static ArrayList<ItemTo> getAll() throws SQLException, ClassNotFoundException {
        String type="PRODUCT";

        ArrayList<ItemTo> itemView=new ArrayList<>();
        ResultSet rst=CrudUtil.execute("SELECT * FROM item WHERE type=?",type);

        while(rst.next()){
            itemView.add(
                    new ItemTo(rst.getString(1),
                            rst.getString(2),
                            rst.getInt(3),
                            rst.getDouble(4),
                            rst.getString(5)
                    )
            );
        }
        return itemView;
    }

}
