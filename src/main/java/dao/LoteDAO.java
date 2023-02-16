package dao;

import model.Lote;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class LoteDAO extends DAO {	
	public LoteDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Lote lote) {
		boolean status = false;
		try {
			String sql = "INSERT INTO lote (data_compra, validade) "
		               + "VALUES (?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(lote.getDataCompra()));
			st.setDate(2, Date.valueOf(lote.getDataValidade()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Lote get(int id) {
		Lote lote = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM lote WHERE id_lote="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 lote = new Lote(rs.getInt("id_lote"),
	        			               rs.getTimestamp("data_compra").toLocalDateTime(),
	        			               rs.getDate("validade").toLocalDate());
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return lote;
	}
	
	
	public List<Lote> get() {
		return get("");
	}

	
	public List<Lote> getOrderByID() {
		return get("id_lote");		
	}
	
	
	public List<Lote> getOrderByDataCompra() {
		return get("data_compra");		
	}
	
	
	public List<Lote> getOrderValidade() {
		return get("validade");		
	}
	
	
	private List<Lote> get(String orderBy) {
		List<Lote> lotes = new ArrayList<Lote>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM lote" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Lote p = new Lote(rs.getInt("id_lote"), 
	        			                rs.getTimestamp("data_compra").toLocalDateTime(),
	        			                rs.getDate("validade").toLocalDate());
	            lotes.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return lotes;
	}
	
	
	public boolean update(Lote lote) {
		boolean status = false;
		try {  
			String sql = "UPDATE lote SET "
					   + "datafabricacao = ?, " 
					   + "datavalidade = ? WHERE id_lote = " + lote.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(lote.getDataCompra()));
			st.setDate(2, Date.valueOf(lote.getDataValidade()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM lote WHERE id_lote = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}