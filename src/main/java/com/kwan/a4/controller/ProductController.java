package com.kwan.a4.controller;

import com.kwan.a4.annotation.HttpMethod;
import com.kwan.a4.annotation.Mapping;
import com.kwan.a4.domain.Product;
import com.kwan.a4.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Mapping("/products")
public class ProductController {

    private ProductController() {
    }

    @Mapping(value = "/", method = HttpMethod.GET)
    public static String products( HttpServletRequest request,
                                  HttpServletResponse response)
        throws ServletException, IOException {
        request.setAttribute("products", ProductService.getInstance().getList());
        request.setAttribute("subject", "Product List");

        return "forward:products.jsp";
    }

    @Mapping(value = "/add", method = HttpMethod.GET)
    public static String addProductGet(HttpServletRequest request,
                                       HttpServletResponse response)
        throws ServletException, IOException {

        request.setAttribute("subject", "Add a new product");

        return "forward:addProduct.jsp";
    }

    @Mapping(value = "/add", method = HttpMethod.POST)
    public static String addProductPost(HttpServletRequest request,
                                        HttpServletResponse response)
        throws ServletException, IOException {

        boolean edit = Boolean.valueOf(request.getParameter("edit"));

        String code = request.getParameter("code");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        Map<String, String> errorMessages = validate(code, description, price);
        Product product = new Product(code, description, 0);

        if (errorMessages.size() > 0) {

            request.setAttribute("edit", edit);
            request.setAttribute("product", product);
            request.setAttribute("subject", edit ? "Edit Product" : "Add a new product");
            request.setAttribute("errorMessages", errorMessages);
            return "forward:addProduct.jsp";
        }

        product.setPrice(Double.parseDouble(price));

        if (ProductService.getInstance().exists(code)) {
            ProductService.getInstance().update(product);
            request.getSession().setAttribute("footer", "[Edited] " + product);
        } else {
            ProductService.getInstance().add(product);
            request.getSession().setAttribute("footer", "[Added] " + product);
        }

        return "redirect:/products";
    }

    private static Map<String, String> validate(String code, String description,
                                                String price) {

        Map<String, String> errorMessages = new HashMap<>();

        if (code == null || code.trim().length() == 0)
            errorMessages.put("codeError", "Code can't be empty.");

        if (description == null || description.trim().length() == 0)
            errorMessages.put("descError", "Description can't be empty.");

        if (price == null ||
            !price.matches("\\d*[.]?\\d*") || Double.parseDouble(price) <= 0)
            errorMessages.put("priceError",
                "Invalid value. Please input a decimal greater than 0.");

        return errorMessages;
    }

    @Mapping(value = "/edit", method = HttpMethod.GET)
    public static String editProduct(HttpServletRequest request,
                                     HttpServletResponse response)
        throws ServletException, IOException {

        String code = request.getParameter("code");

        if (ProductService.getInstance().exists(code)) {
            request.setAttribute("product", ProductService.getInstance().get(code));
            request.setAttribute("edit", true);
            request.setAttribute("subject", "Edit Product");

            return "forward:addProduct.jsp";

        } else {
            return "redirect:/products";
        }
    }

    @Mapping(value = "/delete", method = HttpMethod.GET)
    public static String confirmDelete(HttpServletRequest request,
                                       HttpServletResponse response)
        throws ServletException, IOException {

        String code = request.getParameter("code");

        if (ProductService.getInstance().exists(code)) {
            request.setAttribute("product", ProductService.getInstance().get(code));
            request.setAttribute("subject", "Are you sure you want to delete this product?");

            return "forward:deleteProduct.jsp";

        } else {
            return "redirect:/products";
        }
    }

    @Mapping(value = "/delete", method = HttpMethod.POST)
    public static String deleteProduct(HttpServletRequest request,
                                       HttpServletResponse response)
        throws ServletException, IOException {

        if (request.getParameter("confirm").equals("Yes")) {
            Product product =
                ProductService.getInstance().delete(request.getParameter("code"));

            if (product != null)
                request.getSession()
                    .setAttribute("footer", "[Deleted] " + product);
        }

        return "redirect:/products";
    }
}
