package com.example.quarkus.taskmanager.repository;

import com.example.quarkus.taskmanager.model.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.QueryParam;

import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, UUID> {


    public boolean findByEmail(String email) {
        return find("email", email).firstResultOptional().isPresent();
    }


}



