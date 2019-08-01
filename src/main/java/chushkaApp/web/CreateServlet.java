package chushkaApp.web;

import chushkaApp.domain.entites.Type;
import chushkaApp.models.service.ProductServiceModel;
import chushkaApp.service.ProductService;
import chushkaApp.util.HtmlReader;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/products/create")
public class CreateServlet extends HttpServlet {

    private final HtmlReader htmlReader;
    private final ProductService productService;

    @Inject
    public CreateServlet(HtmlReader htmlReader,
                         ProductService productService) {
        this.htmlReader = htmlReader;
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String createPage = htmlReader.readHtmlFile("create-product")
                .replace("{{typeOptions}}", formatType());

        resp.getWriter().println(createPage);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductServiceModel productModel = new ProductServiceModel();

        productModel.setName(req.getParameter("name"));
        productModel.setDescription(req.getParameter("description"));
        productModel.setType(req.getParameter("type"));

        productService.saveProduct(productModel);

        resp.sendRedirect("/");
    }

    private String formatType() {
        List<String> allTypes = new ArrayList<>();
        Arrays.stream(Type.values())
                .map(type -> "<option>" + type.toString() + "</option>")
                .forEach(allTypes::add);

        return String.join(System.lineSeparator(), allTypes);
    }
}
