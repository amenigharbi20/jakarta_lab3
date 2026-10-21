package com.course.catalog.web;

import com.course.catalog.model.ProductCatalog;
import com.course.catalog.model.ProductDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/cart", "/cart/add", "/cart/remove"})
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        if ("/cart/remove".equals(path)) {
            removeFromCart(req, resp);
        } else {
            addToCart(req, resp);
        }
    }

    @SuppressWarnings("unchecked")
    private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("productId");
        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing productId");
            return;
        }

        ProductDTO product;
        try {
            product = ProductCatalog.findById(Long.valueOf(idParam));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid productId");
            return;
        }

        if (product == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            return;
        }

        HttpSession session = req.getSession();
        List<ProductDTO> cart = (List<ProductDTO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(product);
        session.setAttribute("cart", cart);

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @SuppressWarnings("unchecked")
    private void removeFromCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            List<ProductDTO> cart = (List<ProductDTO>) session.getAttribute("cart");
            String indexParam = req.getParameter("index");
            if (cart != null && indexParam != null) {
                try {
                    int index = Integer.parseInt(indexParam);
                    if (index >= 0 && index < cart.size()) {
                        cart.remove(index);
                        session.setAttribute("cart", cart);
                    }
                } catch (NumberFormatException ignored) {
                    // invalid index: nothing is removed
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}