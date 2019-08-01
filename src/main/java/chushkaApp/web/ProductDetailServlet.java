package chushkaApp.web;

import chushkaApp.models.view.ProductDetailViewModel;
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

@WebServlet("/products/details")
public class ProductDetailServlet extends HttpServlet {

    private final HtmlReader htmlReader;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Inject
    public ProductDetailServlet(HtmlReader htmlReader,
                                ModelMapper modelMapper,
                                ProductService productService) {
        this.htmlReader = htmlReader;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDetailViewModel productView = modelMapper.map(
                productService.findProductByName(req.getQueryString().split("=")[1]),
                ProductDetailViewModel.class
        );

        String productDetailPage = htmlReader.readHtmlFile("details-product")
                .replace("{{productName}}", productView.getName())
                .replace("{{productDescription}}", productView.getDescription())
                .replace("{{productType}}", productView.getType());

        resp.getWriter().println(productDetailPage);
    }
}
