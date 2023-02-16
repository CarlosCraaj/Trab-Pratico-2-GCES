package service;

import dao.UsersDAO;
import model.Users;
import spark.Request;
import spark.Response;

public class UsersService {

    private UsersDAO usersDAO = new UsersDAO();

	public Object insert(Request request, Response response) {
        String email = request.queryParams("email");
		String password = request.queryParams("password");
		String username = request.queryParams("username");

        String resp = "";

		Users users = new Users(-1, email, password, username);

        if(usersDAO.insert(users) == true) {
            resp = "Usuário (" + username + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuário (" + username + ") não inserido!";
			response.status(404); // 404 Not found
		}

		return resp;
		
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id_users"));
		
		Users users = (Users) usersDAO.get(id);
		
		if (users != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<users>\n" + 
            		"\t<id_users>" + users.getID() + "</id_users>\n" +
            		"\t<email>" + users.getEmail() + "</email>\n" +
            		"\t<password>" + users.getPassword() + "</password>\n" +
            		"\t<username>" + users.getUsername() + "</username>\n" +
            		"</users>\n";
        } else {
            response.status(404); // 404 Not found
            return "Usuário " + id + " não encontrado.";
        }

	}

    public Object login(Request request, Response response) {
        String email = request.queryParams("email");
        String password = request.queryParams("password");
		
		Users users = (Users) usersDAO.login(email, password);
		
		if (users != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<users>\n" + 
            		// "\t<id_users>" + users.getID() + "</id_users>\n" +
            		"\t<email>" + users.getEmail() + "</email>\n" +
            		"\t<password>" + users.getPassword() + "</password>\n" +
            		// "\t<username>" + users.getUsername() + "</username>\n" +
            		"</users>\n";
        } else {
            response.status(404); // 404 Not found
            return "Usuário " + id + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_users"));
		Users users = usersDAO.get(id);
        String resp = "";

        if (users != null) {
        	users.setEmail(request.queryParams("email"));
        	users.setPassword(request.queryParams("password"));
        	users.setUsername(request.queryParams("username"));

        	usersDAO.update(users);
            response.status(200);
            resp = "Usuário (ID " + users.getID() + ") atualizado!";
            
        } else {
            response.status(404); // 404 Not found
            resp = "Usuário não encontrado!";
        }

        return resp;
	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id_users"));
        Users users = usersDAO.get(id);
        String resp = "";

        if (users != null) {
            usersDAO.delete(users.getID());
            response.status(200); // success
            resp = "Usuário (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuário (" + id + ") não excluído!";
        }

        return resp;
	}

}
