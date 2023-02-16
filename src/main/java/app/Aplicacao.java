package app;

import static spark.Spark.*;
import service.LoteService;
import service.ProductService;
import service.UsersService;

public class Aplicacao {

    private static LoteService loteService = new LoteService();
    private static ProductService productService = new ProductService();
    private static UsersService userService = new UsersService();

    public static void main(String[] args) {
        port(300);

        staticFiles.location("/public");

        // LoteService
        post("/lote/create", (request, response) -> loteService.insert(request, response));
        get("/lote/detalhe/:id", (request, response) -> loteService.get(request, response));
        post("/lote/update/:id", (request, response) -> loteService.update(request, response));
        get("/lote/delete/:id", (request, response) -> loteService.remove(request, response));

        // UsersService

        post("/user/create", (request, response) -> userService.insert(request, response));
        get("/user/detalhe/:id", (request, response) -> userService.get(request, response));
        post("/user/update/:id", (request, response) -> userService.update(request, response));
        get("/user/delete/:id", (request, response) -> userService.remove(request, response));
        post("/user/login", (request, response) -> userService.login(request, response));
        // ProductService

        post("/product/create", (request, response) -> productService.insert(request, response));
        get("/product/detalhe/:id", (request, response) -> productService.get(request, response));
        post("/product/update/:id", (request, response) -> productService.update(request, response));
        get("/product/delete/:id", (request, response) -> productService.remove(request, response));

    }
}