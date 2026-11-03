package com.course.catalog.web;

import com.course.catalog.model.ProductDTO;
import com.course.catalog.service.CartService;
import com.course.catalog.service.ProductService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/cart", "/cart/add", "/cart/remove"})
public class CartServlet extends HttpServlet {

    @Inject
    private CartService cartService;

    @Inject
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("cart", cartService.getItems());
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if ("/cart/remove".equals(req.getServletPath())) {
            removeFromCart(req, resp);
        } else {
            addToCart(req, resp);
        }
    }

    private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("productId");
        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing productId");
            return;
        }

        ProductDTO product;
        try {
            product = productService.getProduct(Long.valueOf(idParam));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid productId");
            return;
        }

        if (product == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            return;
        }

        cartService.addItem(product);
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void removeFromCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String indexParam = req.getParameter("index");
        if (indexParam != null) {
            try {
                cartService.removeAt(Integer.parseInt(indexParam));
            } catch (NumberFormatException ignored) {
                // index invalide : rien n'est supprime
            }
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}