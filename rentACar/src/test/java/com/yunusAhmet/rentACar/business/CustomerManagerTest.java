package com.yunusAhmet.rentACar.business;

import com.yunusAhmet.rentACar.core.constant.Constant;

import com.yunusAhmet.rentACar.core.exception.CustomerEmailAlreadyExistException;
import com.yunusAhmet.rentACar.core.exception.CustomerNotFoundException;

import com.yunusAhmet.rentACar.dataAccess.CustomerDao;
import com.yunusAhmet.rentACar.dto.*;

import com.yunusAhmet.rentACar.dto.converter.CustomerDtoConverter;

import com.yunusAhmet.rentACar.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class CustomerManagerTest {

    private CustomerDao customerDao;

    private CustomerDtoConverter customerDtoConverter;

    public CustomerManager customerManager;


    @BeforeEach
    public void setUp() {
        customerDao = mock(CustomerDao.class);
        customerDtoConverter = mock(CustomerDtoConverter.class);
        customerManager = new CustomerManager(customerDao,customerDtoConverter);
    }

    @Test
    public void testCreateCustomer_whenCustomerEmailIsNotSame_shouldReturnCustomerDto(){
        CreateCustomerRequest request =new CreateCustomerRequest
                (
                "ahmet",
                "dayı",
                "ahmetdayı26@gmail.com",
                "Ahmet.26",
                "Ahmet.26"
                );

        Customer customer= new Customer
                (
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getMatchingPassword()
                );
        Customer saveCustomer = new Customer
                (
                1,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getMatchingPassword());
        CustomerDto expected = new CustomerDto
                (
                1,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
              );



        when(customerDao.save(customer)).thenReturn(saveCustomer);
        when(customerDtoConverter.convert(saveCustomer)).thenReturn(expected);


        CustomerDto result= customerManager.createCustomer(request);

        assertEquals(expected,result);
        verify(customerDao).save(customer);
        verify(customerDtoConverter).convert(saveCustomer);
    }

    @Test
    public void testCreateCustomer_whenCustomerEmailAlreadyExists_shouldReturnException() {
        CreateCustomerRequest request =new CreateCustomerRequest
                (
                        "ahmet",
                        "dayı",
                        "ahmetdayı26@gmail.com",
                        "Ahmet.26",
                        "Ahmet.26"
                );
        Customer customer= new Customer
                (
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getMatchingPassword()
                );
        when(customerDao.findCustomerByEmail(customer.getEmail())).
                thenReturn(Optional.of(customer));

        assertThrows( CustomerEmailAlreadyExistException.class,() -> customerManager.createCustomer(request) );
        verify(customerDao).findCustomerByEmail(request.getEmail());

    }
    @Test
    public void testUpdateCustomer_whenCustomerIdExists_shouldReturnCustomerDto(){
        UpdateCustomerRequest request = new UpdateCustomerRequest
                (
                        1,
                        "ahmet",
                        "dayı",
                        "Ahmet.26",
                        "Ahmet.26");
        Customer customer = new Customer
                (
                        1,
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPassword(),
                        request.getMatchingPassword());
        CustomerDto expected = new CustomerDto
                (
                        1,
                        request.getFirstName(),
                        request.getLastName(),
                        "ahmetdayı42@gmail.com"
                );


        when(customerDao.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(customerDao.save(customer)).thenReturn(customer);
        when(customerDtoConverter.convert(customer)).thenReturn(expected);
        CustomerDto result = customerManager.updateCustomer(request);

        assertEquals(expected,result);
        verify(customerDao).findById(customer.getCustomerId());
        verify(customerDao).save(customer);
        verify(customerDtoConverter).convert(customer);
    }
    @Test
    public void testDeleteAndUpdateCustomer_whenCustomerIdDoesntExist_shouldReturnException(){

        when(customerDao.findById(1)).thenThrow(new CustomerNotFoundException(Constant.CUSTOMER_NOT_FOUND));
        assertThrows(CustomerNotFoundException.class,()-> customerManager.getCustomerByCustomerId(1));
    }



}