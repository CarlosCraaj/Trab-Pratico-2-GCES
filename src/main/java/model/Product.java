package model;

import dao.LoteDAO;
import dao.UsersDAO;


public class Product {
	private int idProduct;
	private int quantidadeComprada;
	private String nomeProduto;
	private Users id_user;
	private Lote id_lote;

	private LoteDAO loteDAO = new LoteDAO();
	private UsersDAO usersDAO = new UsersDAO();
	
	public Product() {
		idProduct = -1;
		quantidadeComprada = 0;
        nomeProduto = "";
    }

	public Product(int id, int qntdComprada, String nomeProduto, int idlote, int iduser) {
		setID(id);
        setQuantidadeComprada(qntdComprada);
        setNomeProduto(nomeProduto);
		setId_lote(loteDAO.get(idlote));
		setId_user(usersDAO.get(iduser));
	}
	
	public int getID() {
		return idProduct;
	}

	public void setID(int id) {
		this.idProduct = id;
	}


    public int getQuantidadeComprada() {
        return quantidadeComprada;
    }

    public void setQuantidadeComprada(int qntdComprada) {
        this.quantidadeComprada = qntdComprada;
    }
	

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public void setId_user(Users user) {
		this.id_user = user;
	}

	public Users getId_user() {
		return id_user;
	}

	public void setId_lote(Lote lote) {
		this.id_lote = lote;
	}

	public Lote getId_lote() {
		return id_lote;
	}


	@Override
	public String toString() {
		return "Produto: " + nomeProduto + "   Quantidade Comprada: " + quantidadeComprada;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Product) obj).getID());
	}	
}