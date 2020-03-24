package com.xw.lmis.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xw.lmis.dal.SQLHelper;
import com.xw.lmis.model.AbstractModel;
import com.xw.lmis.model.Book;


public class BookDAL extends AbstractDAL{
	
	
	private String[] disColNames = new String[]{
			"序号","书名","作者","出版社","出版时间","价格","入馆时间","状态"
	};
	
	private String[] methodNames = new String[]{
		"getBkID","getBkName","getBkAuthor","getBkPress","getBkDatePress"
		,"getBkPrice","getBkDateIn","getBkStatus"
	};
	
	
	public int add(AbstractModel object)throws Exception {
		if(object instanceof Book == false){
			throw new Exception("Can only handle Book");
		}
		Book rt=(Book)object;
		String sql="insert into TB_Book (bkID,"
		+"bkName,bkAuthor,"
		+"bkPress,bkDatePress,"
		+"bkprice,bkDateIn,bkBrief,"
		+"bkCover,bkStatus)"
		+"VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[]params=new Object[10];
		params[0]=rt.getBkID();
		params[1]=rt.getBkName();
		params[2]=rt.getBkAuthor();
		params[3]=rt.getBkPress();
		params[4]=rt.getBkDatePress();
		params[5]=rt.getBkPrice();
		params[6]=rt.getBkDateIn();
		params[7]=rt.getBkBrief();
		params[8]=rt.getBkCover();
		params[9]=rt.getBkStatus();
		return SQLHelper.ExecSql(sql,params);
	}
	
	public int delete(AbstractModel object) throws Exception {
		if(object instanceof Book == false){
			throw new Exception("Can only handle Book");
		}
		Book rt=(Book) object;
		String sql="delete from TB_Book where bkID=?";
		Object[] params=new Object[]{rt.getBkID()};
		int rows=SQLHelper.ExecSql(sql,params);
		return rows;
	}
	
	public int update(AbstractModel object)throws Exception {
		if(object instanceof Book == false){
			throw new Exception("Can only handle Book");
		}
		Book rt=(Book) object;
		String sql="update TB_Book set bkName=?,"
				+"bkAuthor=?,"
				+"bkPress=?,bkDatePress=?,"
				+"bkprice=?,bkDateIn=?,"
				+"bkBrief=?,bkCover=?,"
				+"bkStatus=? where bkID=?";
		Object[]params=new Object[]{
				rt.getBkName(),rt.getBkAuthor(),
				rt.getBkPress(),rt.getBkDatePress(),
				rt.getBkPrice(),rt.getBkDateIn(),
				rt.getBkBrief(),rt.getBkCover(),
				rt.getBkStatus(),rt.getBkID()};
		return SQLHelper.ExecSql(sql,params);
	}
	public int updatebookIn(AbstractModel object)throws Exception {
		if(object instanceof Book == false){
			throw new Exception("Can only handle Book");
		}
		Book rt=(Book) object;
		String sql="update TB_Book set bkStatus=?"
				+" where bkID=?";
		Object[]params=new Object[]{
				"在馆",rt.getBkID()};
		return SQLHelper.ExecSql(sql,params);
	}
	public int updatebookOut(AbstractModel object)throws Exception {
		if(object instanceof Book == false){
			throw new Exception("Can only handle Book");
		}
		Book rt=(Book) object;
		String sql="update TB_Book set bkStatus=?"
				+" where bkID=?";
		Object[]params=new Object[]{
				"外借",rt.getBkID()};
		return SQLHelper.ExecSql(sql,params);
	}
	private Book initBook(ResultSet rs) throws SQLException{
		Book bk = new Book();
		bk.setBkID(rs.getInt("bkID"));
		bk.setBkName(rs.getString("bkName"));
		bk.setBkAuthor(rs.getString("bkAuthor"));
		bk.setBkPress(rs.getString("bkPress"));
		bk.setBkDatePress(rs.getDate("bkDatePress"));
		bk.setBkPrice(rs.getFloat("bkPrice"));
		bk.setBkDateIn(rs.getDate("bkDateIn"));
		bk.setBkBrief(rs.getString("bkBrief"));
		bk.setBkCover(rs.getBytes("bkCover"));
		bk.setBkStatus(rs.getString("bkStatus"));
		return bk;
	}
	

	@Override
	public AbstractModel[] getAllObjects() throws Exception {
		ArrayList<Book> objects = new ArrayList<Book>();
		ResultSet rs = SQLHelper.getResultSet("select * from tb_book");
		if(rs != null){
			while(rs.next()){
				Book bk = initBook(rs);
				objects.add(bk);
			}
			rs.close();
		}
		Book[] books = new Book[objects.size()];
		objects.toArray(books);
		return books;
	}

	@Override
	public AbstractModel getObjectByID(int bkID) throws SQLException {
		Book bk = null;
		ResultSet rs = SQLHelper.getResultSet(""
				+ "select bkID,bkName,bkAuthor,bkPress,"
				+ "bkDatePress,bkPrice,bkDateIn,bkBrief,"
				+ "bkCover,bkStatus from tb_book where bkID ="
				+ bkID);
		if(rs != null){
			if(rs.next()){
				bk = initBook(rs);
			}
			rs.close();
		}
		return bk;
	}

	@Override
	public String[] getMethodNames() {
		return this.methodNames;
	}

	@Override
	public String[] getPrettyColumnNames() {
		return this.disColNames;
	}
	
	public Book[] getObjectBy(String value,String content) throws SQLException{
		ArrayList<Book> books = new ArrayList<Book>();
		String sql = "Select * from TB_Book where "
				+ value +" like ?";
		Object[] params = new Object[]{"%"+ content + "%"};
		ResultSet rs = SQLHelper.getResultSet(sql,params);
		if(rs != null){
			while(rs.next()){
				Book book = initBook(rs);
				books.add(book);
			}
			rs.close();
		}
		if(books.size() > 0){
			Book[] array = new Book[books.size()];
			books.toArray(array);
			return array;
		}
		return null;
	}
	
	public Book[] getObjectByIdandName(String bkID, String bkName) throws SQLException{
		ArrayList<Book> books = new ArrayList<Book>();
		String sql = "select * from tb_book where bkID like ? "
				+ "and bkName like ? ";
		Object[] params = new Object[]{
			"%" + bkID + "%",
			"%" + bkName + "%"
		};
		ResultSet rs = SQLHelper.getResultSet(sql,params);
		if(rs != null){
			while(rs.next()){
				Book book = initBook(rs);
				books.add(book);
			}
			rs.close();
		}
		if(books.size() > 0){
			Book[] array = new Book[books.size()];
			books.toArray(array);
			return array;
		}
		return null;
	}
	
	public Book[] getObjectBy(String bkName,String bkAuthor,String bkPress) throws SQLException{
		ArrayList<Book> books = new ArrayList<Book>();
		String sql = "select * from tb_book where bkName like ? "
				+ "and bkAuthor like ? and bkPress like ?";
		Object[] params = new Object[]{
			"%" + bkName + "%",
			"%" + bkAuthor + "%",
			"%" + bkPress + "%"
		};
		ResultSet rs = SQLHelper.getResultSet(sql,params);
		if(rs != null){
			while(rs.next()){
				Book book = initBook(rs);
				books.add(book);
			}
			rs.close();
		}
		if(books.size() > 0){
			Book[] array = new Book[books.size()];
			books.toArray(array);
			return array;
		}
		return null;
		
	}
}
