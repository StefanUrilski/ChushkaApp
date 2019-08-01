package chushkaApp.web;

import chushkaApp.models.view.ProductViewModel;
import chushkaApp.service.ProductService;
import chushkaApp.util.HtmlReader;
import chushkaApp.util.ModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/")
public class IndexServlet extends HttpServlet {

    private final HtmlReader htmlReader;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Inject
    public IndexServlet(HtmlReader htmlReader,
                        ModelMapper modelMapper,
                        ProductService productService) {
        this.htmlReader = htmlReader;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String indexPage = htmlReader.readHtmlFile("index")
                .replace("{{allProducts}}", formatProducts());
        resp.getWriter().println(indexPage);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/products/create");
    }

    private String formatProducts() {
        List<ProductViewModel> allProducts =
                productService.findAllProducts()
                .stream()
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());

        if (productService.findAllProducts() == null) return "";
        List<String> productInfo = new ArrayList<>();
        allProducts.stream()
                .map(product ->
                        String.format("<li><a href=\"/products/details?name=%s\">%s</a></li>",
                                product.getName(),
                                product.getName())
                ).forEach(productInfo::add);

        return String.join(System.lineSeparator(), productInfo);
    }

}
