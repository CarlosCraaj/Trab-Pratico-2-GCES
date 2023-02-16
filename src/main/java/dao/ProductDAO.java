package dao;

import model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ProductDAO extends DAO {	
	public ProductDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Product product) {
		boolean status = false;
		try {
			String sql = "INSERT INTO product (quantidade_comprada, nome_produto, id_lote, id_user) "
		               + "VALUES (?, ?, ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setInt(1, product.getQuantidadeComprada());
			st.setString(2, product.getNomeProduto());
			st.setInt(3, product.getId_lote().getID());
			st.setInt(4, product.getId_user().getID());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Product get(int id) {
		Product product = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM product WHERE id_product="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	product = new Product(rs.getInt("id_product"),
											rs.getInt("quantidade_comprada"),
											rs.getString("nome_produto"),
											rs.getInt("id_lote"),
											rs.getInt("id_user"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return product;
	}
	
	
	public List<Product> get() {
		return get("");
	}

	
	public List<Product> getOrderByID() {
		return get("id_product");		
	}
	
	
	public List<Product> getOrderByQuantidadeComprada() {
		return get("quantidade_comprada");		
	}

	public List<Product> getOrderByNomeProduto() {
		return get("nome_produto");
	}
	
	
	public List<Product> getOrderIDLote() {
		return get("id_lote");		
	}
	
	
	public List<Product> getOrderIDUser() {
		return get("id_user");		
	}
	
	
	private List<Product> get(String orderBy) {
		List<Product> products = new ArrayList<Product>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM product" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Product p = new Product(rs.getInt("id_product"),
											rs.getInt("quantidade_comprada"),
											rs.getString("nome_produto"),
											rs.getInt("id_lote"),
											rs.getInt("id_user"));
	            products.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return products;
	}
	
	
	public boolean update(Product product) {
		boolean status = false;
		try {  
			String sql = "UPDATE product SET quantidade_comprada=?, nome_produto=?, id_lote=?, id_user=? WHERE id_product=?";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, product.getQuantidadeComprada());
			st.setString(2, product.getNomeProduto());
			st.setInt(3, product.getId_lote().getID());
			st.setInt(4, product.getId_user().getID());
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
			st.executeUpdate("DELETE FROM product WHERE id_product = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}