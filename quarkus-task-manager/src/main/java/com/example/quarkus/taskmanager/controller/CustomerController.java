package com.example.quarkus.taskmanager.controller;


import com.example.quarkus.taskmanager.model.Customer;
import com.example.quarkus.taskmanager.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path(value = "/customers")
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
                    .entity("Erro ao obter a lista de clientes. Detalhes: " + e.getMessage())
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
                    .entity("ERRO: Ocorreu um erro durante a adição do cliente. Por favor, tente novamente mais tarde. Detalhes: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response customerGetId(@PathParam("id") UUID id) {
        try {
            Optional<Customer> optionalCustomer = customerService.getOneCustomer(id);
            if (optionalCustomer.isPresent()) {
                return Response.ok(optionalCustomer.get()).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("ERRO: Cliente não existe")
                        .build();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERRO: Ocorreu um erro durante a busca do cliente. Detalhes: " + e.getMessage())
                    .build();
        }
    }


    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") UUID id) {
        try {
            Optional<Customer> optionalCustomer = customerService.getOneCustomer(id);
            if (optionalCustomer.isPresent()) {
                customerService.deleteCustomerById(id);
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("ERRO: Cliente não existe")
                        .build();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERRO: Ocorreu um erro durante a exclusão do cliente. Por favor, tente novamente mais tarde. Detalhes: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") UUID id, Customer customer) {
        try {
            Optional<Customer> customerOptional = customerService.getOneCustomer(id);
            if (customerOptional.isPresent()) {
                customerService.customerUpdate(id, customer);
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("ERRO: Cliente não existe")
                        .build();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERRO: Ocorreu um erro durante a atualização do cliente. Por favor, tente novamente mais tarde. Detalhes: " + e.getMessage())
                    .build();
        }
    }
}
