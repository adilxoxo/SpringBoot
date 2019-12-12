package ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
	static Connection con;
	public static Connection getConnection() {
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/databasename","root","root");    
			}catch(Exception e){ 
				System.out.println(e);
			} 
		return con;
	}
}  

