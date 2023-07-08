package com.example.quarkus.taskmanager.service;

import com.example.quarkus.taskmanager.model.Customer;
import com.example.quarkus.taskmanager.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll().list();
    }

    @Transactional
    public void addCustomer(Customer customer) {
        customerRepository.persist(customer);

    }

    public Optional<Customer> getOneCustomer(UUID id) {
        return Optional.ofNullable(customerRepository.findById(id));

    }

    @Transactional
    public boolean deleteCustomerById(UUID id) {
        return customerRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional
    public void customerUpdate(UUID id, Customer customer) {
        Customer existingCustomer = customerRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        existingCustomer.setName(customer.getName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setAge(customer.getAge());
        existingCustomer.setEmail(customer.getEmail());
        customerRepository.persist(existingCustomer);
    }
}