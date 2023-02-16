package dao;

import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO extends DAO {
	public UsersDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Users user) {
		boolean status = false;
		try {
			String sql = "INSERT INTO users (email, password, username) "
					+ "VALUES ('" + user.getEmail() + "', '" + user.getPassword() + "', '" + user.getUsername() + "');";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Users get(int id) {
		Users user = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM users WHERE id_users=" + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				user = new Users(rs.getInt("id_users"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("username"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return user;
	}

	public Users login(String email, String password) {
		Users user = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT email, username from users as u where u.email = '" + email
					+ "' and u.password ='" + password + "'";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				user = new Users(-1, rs.getString("email"),
				rs.getString("password"),"");
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return user;
	}

	public List<Users> get() {
		return get("");
	}

	public List<Users> getOrderByID() {
		return get("id_users");
	}

	public List<Users> getOrderByEmail() {
		return get("email");
	}

	public List<Users> getOrderUsername() {
		return get("username");
	}

	private List<Users> get(String orderBy) {
		List<Users> users = new ArrayList<Users>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM users" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Users p = new Users(rs.getInt("id_users"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("username"));
				users.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return users;
	}

	public boolean update(Users user) {
		boolean status = false;
		try {
			String sql = "UPDATE users SET email = '" + user.getEmail() + "', "
					+ "password = " + user.getPassword() + ", "
					+ "username = " + user.getUsername() + ","
					+ " WHERE id_users = " + user.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
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
			st.executeUpdate("DELETE FROM users WHERE id_users = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}