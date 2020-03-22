package com.telnov.software_design.reactive_app.controller;

import com.telnov.software_design.reactive_app.model.Product;
import com.telnov.software_design.reactive_app.model.User;
import com.telnov.software_design.reactive_app.service.Service;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("get/user/{id}")
    public @ResponseBody
    Mono<User> getUser(@PathVariable("id") long id) {
        return service.getUserById(id);
    }

    @PostMapping("add/user")
    public @ResponseBody
    Mono<User> addUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @DeleteMapping("delete/user/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        service.deleteUser(id);
    }

    @GetMapping("get/products/{userId}")
    public @ResponseBody
    Flux<Product> getProducts(@PathVariable("userId") long userId) {
        return service.getProductsForUser(userId);
    }

    @GetMapping("get/product/{id}")
    public @ResponseBody
    Mono<Product> getProduct(@PathVariable("id") long productId) {
        return service.getProductById(productId);
    }

    @PostMapping("add/product")
    public @ResponseBody
    Mono<Product> addProduct(@RequestBody Product product) {
        return service.addProduct(product);
    }

    @DeleteMapping("delete/product/{id}")
    public void deleteProduct(@PathVariable("id") long id) {
        service.deleteProduct(id);
    }
}
