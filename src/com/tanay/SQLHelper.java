package com.tanay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class SQLHelper {


	public String singleQuotes(String word) {
		return "'" + word + "'";
	}
	
	public static void skipper() {
		System.out.println("\nClick enter to skip this filter");
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
	
	public String merger(HashMap<String, String> map, String operand) {
		String result = "";
		for (String key : map.keySet()) {
			if(result.length() > 0) {
				result += operand;
			}
			result += key + "=" + map.get(key);
//            System.out.println("Key: " + key + ", Value: " + map.get(key));
        }
		return result;
	}

	public String mergerUpdate(HashMap<String, String> map) {
		String result = "";
		for (String key : map.keySet()) {
			if(result.length() > 0) {
				result += ", ";
			}
			result += key + "=" + map.get(key);
//            System.out.println("Key: " + key + ", Value: " + map.get(key));
        }
		return result;
	}

	// public String merger(HashMap<String, String> map, List<String> list) {
	// 	String result = "";
	// 	for (String key: map.keySet()) {
	// 		if (list.contains(key)) {
	// 			if(result.length() > 0) {
	// 				result += " AND ";
	// 			}
	// 			result += key + "=" + Integer.valueOf(map.get(key));
	// 		} else {
	// 			if(result.length() > 0) {
	// 				result += " AND ";
	// 			}
	// 			result += key + "=" + map.get(key);
	// 		}
	// 	} 
	// 	return result;
	// }

	// public String merger(HashMap<String, String> map, List<String> intList, List<String> boolList) {
	// 	String result = "";
	// 	for (String key: map.keySet()) {
	// 		if (intListlist.contains(key)) {
	// 			if(result.length() > 0) {
	// 				result += " AND ";
	// 			}
	// 			result += key + "=" + Integer.valueOf(map.get(key));
	// 		} else if (boolList.contains(key)) {

	// 		} else {
	// 			if(result.length() > 0) {
	// 				result += " AND ";
	// 			}
	// 			result += key + "=" + map.get(key);
	// 		}
	// 	} 
	// 	return result;
	// }
}
