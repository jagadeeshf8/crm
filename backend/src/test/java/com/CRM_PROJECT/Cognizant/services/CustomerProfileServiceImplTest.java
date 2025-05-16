package com.CRM_PROJECT.Cognizant.services;

import com.CRM_PROJECT.Cognizant.ServiceImplementations.CustomerProfileServiceImpl;
import com.CRM_PROJECT.Cognizant.entity.CustomerProfile;
import com.CRM_PROJECT.Cognizant.repository.CustomerProfileRepository;

import com.CRM_PROJECT.Cognizant.exception.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerProfileServiceImplTest {

    @Mock
    private CustomerProfileRepository repository;

    @InjectMocks
    private CustomerProfileServiceImpl service;

    @Test
    public void testGetAllCustomers() {
        CustomerProfile customer1 = new CustomerProfile();
        CustomerProfile customer2 = new CustomerProfile();
        List<CustomerProfile> customers = Arrays.asList(customer1, customer2);

        when(repository.findAll()).thenReturn(customers);

        List<CustomerProfile> result = service.getAllCustomers();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetCustomerById() {
        CustomerProfile customer = new CustomerProfile();
        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerProfile result = service.getCustomerById(1L);

        assertEquals(customer, result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testGetCustomerById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            service.getCustomerById(1L);
        });

        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void testAddCustomer() {
        CustomerProfile customer = new CustomerProfile();
        when(repository.save(customer)).thenReturn(customer);

        CustomerProfile result = service.addCustomer(customer);

        assertEquals(customer, result);
        verify(repository, times(1)).save(customer);
    }

    @Test
    public void testUpdateCustomer() {
        CustomerProfile customer = new CustomerProfile();
        CustomerProfile updatedCustomer = new CustomerProfile();

        when(repository.findById(1L)).thenReturn(Optional.of(customer));
        when(repository.save(customer)).thenReturn(updatedCustomer);

        CustomerProfile result = service.updateCustomer(1L, updatedCustomer);

        assertEquals(updatedCustomer, result);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(customer);
    }

    @Test
    public void testDeleteCustomer() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.deleteCustomer(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCustomer_NotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () -> {
            service.deleteCustomer(1L);
        });

        verify(repository, times(1)).existsById(1L);
    }
}