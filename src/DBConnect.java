import java.sql.*;

public class DBConnect {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public DBConnect(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/","sqluser","sqluser123");
			st = con.createStatement();
		}catch(Exception ex){
			System.out.println("Error : "+ ex);
		}
	}
	
	public boolean checkDB(String dbname){
		try{
			String query = "show databases";
			rs = st.executeQuery(query);
			
			while(rs.next()){
				String name = rs.getString("Database");
				if(name.equals(dbname)){
					return true;
				}
			}
			
			
		}catch(Exception ex){
			System.out.println("Error : "+ ex);
			
		}
		
		return false;
	}
	
	public void query(String query){
		try{
			rs = st.executeQuery(query);
			//System.out.println(query+ ": success");
		}catch(Exception ex){
			System.out.println("Error : "+ ex);
			
		}
	}
	
	public void update(String query){
		try{
			st.executeUpdate(query);
		//	System.out.println(query+ ": success");
		}catch(Exception ex){
			System.out.println("Error : "+ ex);
			
		}
	}
	
	public ResultSet getrs(String query){ //get result set
		try{
			rs = st.executeQuery(query);
			//System.out.println(query+ ": success");
			return rs;
			
		}catch(Exception ex){
			System.out.println("Error : "+ ex);
			
		}
		
		return null;
	}
	
	
	public boolean checkTable(String tname, String dbname){
		try{
			String query = "show tables";
			rs = st.executeQuery(query);
			while(rs.next()){
				String name = rs.getString("Tables_in_" + dbname);
				if(name.equals(tname)){
					return true;
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error : "+ ex);
		}
		return false;
	}
	
	public int queryCount(String query){
		try{
			rs = st.executeQuery(query);
			//System.out.println(query+ ": success");
			rs.next();
			return rs.getInt(1);
			
		}catch(Exception ex){
			System.out.println("Error : "+ ex);
			
		}
		return -1;
	}
}
