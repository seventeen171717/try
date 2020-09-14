package dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.student;


public class studentdao {
	private Connection conn;
	private PreparedStatement pst;
	private ResultSet rs;
	private int flag;
	private ArrayList<student> list;
	private student student;
	private boolean flags = false;
	/**
	 * 连接数据库
	 * @param args
	 */
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf8", "root",
						"123456");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("链接字符串出错！");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("驱动加载出错！");
		}
		return conn;
	}
	/**
	 * 关闭数据库
	 * @param args
	 */
	public void releasedao(ResultSet rs, PreparedStatement pst, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 查询所有用户ArrayList存储
	 * @param args
	 */
	public ArrayList<student> queryallstudent(){
		conn = getConnection();
		list = new ArrayList<student>();
		String sql = "select * from student";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				student student = new student();
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String userpwd = rs.getString("userpwd");
				student.setId(id);
				student.setUsername(username);
				student.setUserpwd(userpwd);
				list.add(student);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			releasedao(rs,pst,conn);
		}
		return list;
	}
	/**
	 * 带有封装的插入一条数据，并返回是否插入flag
	 * @param args
	 */
	public int addstudent(student student){
		conn = getConnection();
		String sql = "insert into student(username,userpwd) values(?,?)";
		try{
			pst = conn.prepareStatement(sql);
			pst.setString(1, student.getUsername());
			pst.setString(2, student.getUserpwd());
			flag = pst.executeUpdate();
			
		}catch(SQLException e){
			e.addSuppressed(e);
		}finally{
			releasedao(rs, pst, conn);
		}
		return flag;
	}
	/**
	 * 根据id删除记录,返回是否删除flag
	 * @param args
	 */
	public int deletebyid(int id){
		conn = getConnection();
		String sql="delete from student where id=?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			flag = pst.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			releasedao(rs, pst, conn);
		}
		return flag;
	}
	/**
	 * 修改信息
	 * @param args
	 */
	public int updatebyid(student s,int id){
		conn = getConnection();
		String sql = "update student set username=?,userpwd=? where id=?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setString(1, s.getUsername());
			pst.setString(2, s.getUserpwd());
			pst.setInt(3, id);
			flag = pst.executeUpdate();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			releasedao(rs, pst, conn);
		}
		return flag;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		studentdao s = new studentdao();
		Connection conn = s.getConnection();
		if(!conn.equals(null))
		{
			System.out.println("数据库连接成功!");
		}
		
		ArrayList<student> list = s.queryallstudent();
		for(int i=0;i<list.size();i++)
		{
			student st = new student();
			st = list.get(i);
			System.out.print(st.getId()+"---");
			System.out.print(st.getUsername()+"---");
			System.out.print(st.getUserpwd()+"---");
			
			System.out.println();
			
		}
	}

}
