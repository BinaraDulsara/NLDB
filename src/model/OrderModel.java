package model;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {
    public static String getNewId() throws SQLException, ClassNotFoundException {
        String lastLectureId = getLastId();
        if (lastLectureId == null) {
            return "O-0000000000001";
        } else {
            String[] split = lastLectureId.split("[O][-]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newLectureId = String.format("O-%013d", lastDigits);
            return newLectureId;
        }
    }


    public static String getLastId() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT id from orders order by id DESC limit 1");
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }
}
