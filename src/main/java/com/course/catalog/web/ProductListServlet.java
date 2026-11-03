package com.course.catalog.web;

import com.course.catalog.model.ProductDTO;
import com.course.catalog.service.ProductService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/catalog/products")
public class ProductListServlet extends HttpServlet {

    @Inject
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String category = req.getParameter("category");
        List<ProductDTO> products = (category != null)
                ? productService.getByCategory(category)
                : productService.getAllProducts();

        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
    }
}