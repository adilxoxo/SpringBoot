package com.example.demo.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.beans.User;
import com.google.gson.Gson;

import ConnectionPool.ConnectionUtil;

@RestController
public class Controller {
	@RequestMapping("/demo")
	public List<String> demo() {
		return getString();
	}

	private List<String> getString() {
		int rows = 5;
		List<String> strList = new ArrayList<String>();
		
        for(int i = 1; i <= rows; ++i) {
        	String str = "";
            for(int j = 1; j <= i; ++j) {
                System.out.print(j + " ");
                str+=j + " ";
            }
            strList.add(str+"\n");
            System.out.println();
        }
		return strList;
	}
	
	@RequestMapping("/getData")
	public HashMap<String, Object> getData() throws SQLException {
		return getTableData();
	}

	private HashMap<String, Object> getTableData() throws SQLException {
		Connection con = ConnectionUtil.getConnection();
		Statement stmt=con.createStatement(); 
		List<Map<String, Object>> strList = new ArrayList<Map<String, Object>>();
		ResultSet rs=stmt.executeQuery("select * from user");  
		while(rs.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key",rs.getInt(1));
			map.put("name",rs.getString(2));
			map.put("lastname",rs.getString(3));
			strList.add(map);
		}
		
		//For angular Datatableview send data in the form of map with key as "data"
		HashMap<String, Object> hmap = new HashMap<String,Object>();
		hmap.put("data",strList);
		con.close();
		return hmap;
	}
	
	@RequestMapping(value = "/setData", 
		    method = RequestMethod.POST)
	public Map<String, Object> setData(@RequestBody String data) throws SQLException {
		return setUserData(data);
	}

	private Map<String, Object> setUserData(String data) {
		// TODO Auto-generated method stub
		Map<String, Object> resultSet = new HashMap<String, Object>();
		Gson g = new Gson();
		User user = g.fromJson(data,User.class);
		try {
			Connection con = ConnectionUtil.getConnection();
			Statement stmt = con.createStatement();
			String query = "insert into user(Name,LastName) values('"+user.getName()+"','"+user.getLastname()+"');";
			stmt.executeUpdate(query);
			resultSet.put("message", "Success");
			resultSet.put("errorCode", "0");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultSet.put("message", "Failed");
			resultSet.put("errorCode", "1");
		} 
		return resultSet;
	}
	
	@RequestMapping(value = "/updateUser", 
		    method = RequestMethod.POST)
	public Map<String, Object> updateUser(@RequestBody String data) throws SQLException {
		return updateUserData(data);
	}
	
	private Map<String, Object> updateUserData(String data) {
		// TODO Auto-generated method stub
		Map<String, Object> resultSet = new HashMap<String, Object>();
		Gson g = new Gson();
		User user = g.fromJson(data,User.class);
		try {
			Connection con = ConnectionUtil.getConnection();
			Statement stmt = con.createStatement();
			String query = "Update user set Name='"+user.getName()+"',LastName='"+user.getLastname()+"' where id="+user.getKey();
			stmt.executeUpdate(query);
			resultSet.put("message", "Success");
			resultSet.put("errorCode", "0");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultSet.put("message", "Failed");
			resultSet.put("errorCode", "1");
		} 
		return resultSet;
	}
	
	@RequestMapping(value = "/deleteData", 
		    method = RequestMethod.POST)
	public Map<String, Object> deleteData(@RequestBody String data) throws SQLException {
		return deleteUserData(data);
	}
	
	private Map<String, Object> deleteUserData(String data) {
		// TODO Auto-generated method stub
		Map<String, Object> resultSet = new HashMap<String, Object>();
		Gson g = new Gson();
		User user = g.fromJson(data,User.class);
		try {
			Connection con = ConnectionUtil.getConnection();
			Statement stmt = con.createStatement();
			String query = "Delete from user where id="+user.getKey();
			stmt.executeUpdate(query);
			resultSet.put("message", "Success");
			resultSet.put("errorCode", "0");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultSet.put("message", "Failed");
			resultSet.put("errorCode", "1");
		} 
		return resultSet;
	}
	
	@RequestMapping(value = "/clearData", 
		    method = RequestMethod.POST)
	public Map<String, Object> clearData() throws SQLException {
		return clearUsesrData();
	}

	private Map<String, Object> clearUsesrData() {
		Map<String, Object> resultSet = new HashMap<String, Object>();
		try {
			Connection con = ConnectionUtil.getConnection();
			Statement stmt = con.createStatement();
			String query = "truncate table user";
			stmt.executeUpdate(query);
			resultSet.put("message", "Success");
			resultSet.put("errorCode", "0");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultSet.put("message", "Failed");
			resultSet.put("errorCode", "1");
		} 
		return resultSet;
	}
}
