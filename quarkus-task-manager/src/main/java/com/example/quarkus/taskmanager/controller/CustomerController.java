package com.example.quarkus.taskmanager.controller;


import com.example.quarkus.taskmanager.model.Customer;
import com.example.quarkus.taskmanager.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path(value = "/customer")
public class CustomerController {

    @Inject
    CustomerService customerService;

    @GET
    public Response allCustomers() {
        try {
            List<Customer> customerList = customerService.findAllCustomers();
            if (customerList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Lista de clientes vazia!")
                        .build();
            } else {
                return Response.ok(customerList).build();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter a lista de clientes")
                    .build();
        }
    }

    @POST
    @Transactional
    public Response addCustomer(Customer customer) {
        if (customerService.existsByEmail(customer.getEmail())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("ERRO: Este e-mail já foi cadastrado, utilize outro.")
                    .build();
        }
        try {
            customerService.addCustomer(customer);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ERRO: Dados inválidos fornecidos para adicionar o cliente.")
                    .build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERRO: Ocorreu um erro durante a adição do cliente. Por favor, tente novamente mais tarde.")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response customerGetId(@PathParam("id") UUID id) {
        try {
            Optional<Customer> optionalCustomer = customerService.getOneCustomer(id);
            if (!optionalCustomer.isPresent()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("ERRO: Cliente não existe")
                        .build();
            }
            return Response.ok(optionalCustomer.get()).build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERRO: Ocorreu um erro durante a busca do cliente")
                    .build();
        }
    }


    @DELETE
    @Transactional
    @Path("/{id}")
    public void deleteCustomer(@PathParam("id") UUID id) {
        customerService.deleteCustomerById(id);

    }
}
