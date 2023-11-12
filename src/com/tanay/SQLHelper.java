package com.tanay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class SQLHelper {
	public String singleQuotes(String word) {
		return "'" + word + "'";
	}
	
	public static boolean tableExists(Connection connection, String tableName) throws SQLException {
        String checkTableQuery = "SHOW TABLES LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkTableQuery)) {
            preparedStatement.setString(1, tableName);
            return preparedStatement.executeQuery().next();
        }
    }
	
	public String merger(HashMap<String, String> map) {
		String result = "";
		for (String key : map.keySet()) {
			if(result.length() > 0) {
				result += " AND ";
			}
			result += key + "=" + map.get(key);
//            System.out.println("Key: " + key + ", Value: " + map.get(key));
        }
		return result;
	}
}
