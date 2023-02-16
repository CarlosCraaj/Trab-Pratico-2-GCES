package service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import dao.LoteDAO;
import model.Lote;
import spark.Request;
import spark.Response;

public class LoteService {

    private LoteDAO loteDAO = new LoteDAO();

	public Object insert(Request request, Response response) {
        LocalDateTime dataCompra = LocalDateTime.parse(request.queryParams("data_compra"));
	    LocalDate dataValidade = LocalDate.parse(request.queryParams("validade"));

        String resp = "";

		Lote lote = new Lote(-1, dataCompra, dataValidade);

        if(loteDAO.insert(lote) == true) {
            resp = "Lote inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Lote não inserido!";
			response.status(404); // 404 Not found
		}

		return resp;
		
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id_lote"));
		
		Lote lote = (Lote) loteDAO.get(id);
		
		if (lote != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<lote>\n" + 
            		"\t<id_lote>" + lote.getID() + "</id_lote>\n" +
            		"\t<data_compra>" + lote.getDataCompra() + "</data_compra>\n" +
            		"\t<validade>" + lote.getDataValidade() + "</validade>\n" +
            		"</lote>\n";
        } else {
            response.status(404); // 404 Not found
            return "Lote " + id + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_lote"));
		Lote lote = loteDAO.get(id);
        String resp = "";

        if (lote != null) {
        	lote.setDataCompra(LocalDateTime.parse(request.queryParams("data_compra")));
        	lote.setDataValidade(LocalDate.parse(request.queryParams("validade")));

        	loteDAO.update(lote);
            response.status(200);
            resp = "Lote (ID " + lote.getID() + ") atualizado!";
            
        } else {
            response.status(404); // 404 Not found
            resp = "Usuário não encontrado!";
        }

        return resp;
	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_users"));
        Lote lote = loteDAO.get(id);
        String resp = "";

        if (lote != null) {
            loteDAO.delete(lote.getID());
            response.status(200); // success
            resp = "Lote (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Lote (" + id + ") não excluído!";
        }

        return resp;
	}

}
