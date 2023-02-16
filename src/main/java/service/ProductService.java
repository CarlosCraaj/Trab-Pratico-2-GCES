package service;

import dao.ProductDAO;
import dao.LoteDAO;
import dao.UsersDAO;
import model.Product;
import spark.Request;
import spark.Response;


public class ProductService {

    private ProductDAO productDAO = new ProductDAO();
    private LoteDAO loteDAO = new LoteDAO();
	private UsersDAO usersDAO = new UsersDAO();

	public Object insert(Request request, Response response) {
        int quantidade_comprada = Integer.parseInt(request.queryParams("quantidade_comprada"));
        String nome_produto = request.queryParams("nome_produto");
        int id_lote = Integer.parseInt(request.queryParams("id_lote"));
        int id_user = Integer.parseInt(request.queryParams("id_user"));

        String resp = "";

		Product product = new Product(-1, quantidade_comprada, nome_produto, id_lote, id_user);

        if(productDAO.insert(product) == true) {
            resp = "Produto (" + nome_produto + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Produto (" + nome_produto + ") não inserido!";
			response.status(404); // 404 Not found
		}

		return resp;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id_product"));
		
		Product product = (Product) productDAO.get(id);
		
		if (product != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<product>\n" + 
            		"\t<id_product>" + product.getID() + "</id_product>\n" +
            		"\t<quantidade_comprada>" + product.getQuantidadeComprada() + "</quantidade_comprada>\n" +
            		"\t<nome_produto>" + product.getNomeProduto() + "</nome_produto>\n" +
            		"\t<id_lote>" + product.getId_lote().getID() + "</id_lote>\n" +
            		"\t<id_user>" + product.getId_user().getID() + "</id_user>\n" +
            		"</product>\n";
        } else {
            response.status(404); // 404 Not found
            return "Produto " + id + " não encontrado.";
        }

	}
    

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_product"));
		Product product = productDAO.get(id);
        String resp = "";

        if (product != null) {
        	product.setQuantidadeComprada(Integer.parseInt(request.queryParams("quantidade_comprada")));
            product.setNomeProduto(request.queryParams("nome_produto"));
            product.setId_lote( loteDAO.get(Integer.parseInt(request.queryParams("id_lote"))) );
            product.setId_user( usersDAO.get(Integer.parseInt(request.queryParams("id_user"))) );

        	productDAO.update(product);
            response.status(200);
            resp = "Produto (ID " + product.getID() + ") atualizado!";
            
        } else {
            response.status(404); // 404 Not found
            resp = "Produto não encontrado!";
        }

        return resp;
	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_product"));
        Product product = productDAO.get(id);
        String resp = "";

        if (product != null) {
            productDAO.delete(product.getID());
            response.status(200); // success
            resp = "Produto (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Produto (" + id + ") não excluído!";
        }

        return resp;
	}

}