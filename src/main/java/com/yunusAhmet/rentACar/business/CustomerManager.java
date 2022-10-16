package com.yunusAhmet.rentACar.business;

import com.yunusAhmet.rentACar.core.constant.Constant;
import com.yunusAhmet.rentACar.core.exception.CustomerEmailAlreadyExistException;
import com.yunusAhmet.rentACar.core.exception.CustomerNotFoundException;
import com.yunusAhmet.rentACar.dataAccess.CustomerDao;
import com.yunusAhmet.rentACar.dto.*;
import com.yunusAhmet.rentACar.dto.converter.CustomerDtoConverter;
import com.yunusAhmet.rentACar.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerManager {

    private final CustomerDao customerDao;

    private final CustomerDtoConverter customerDtoConverter;

    public CustomerManager(CustomerDao customerDao, CustomerDtoConverter customerDtoConverter) {
        this.customerDao = customerDao;

        this.customerDtoConverter = customerDtoConverter;
    }

    protected Customer getCustomerByCustomerId(int customerId){
        return customerDao.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(Constant.CUSTOMER_NOT_FOUND));
    }

    public CustomerDto createCustomer(CreateCustomerRequest request){
        Optional<Customer> customer = customerDao.findCustomerByEmail(request.getEmail());
        if (customer.isPresent()) {
            throw new CustomerEmailAlreadyExistException(Constant.CUSTOMER_EMAIL_ALREADY_EXIST);
        }
       Customer customer1 = new Customer
               (request.getFirstName(),
                       request.getLastName(),
                       request.getEmail(),
                       request.getPassword(),
                       request.getMatchingPassword());
        Customer save = customerDao.save(customer1);
        return customerDtoConverter.convert(save);
    }

    public void deleteCustomerByCustomerId(int customerId){
        customerDao.deleteById(getCustomerByCustomerId(customerId).getCustomerId());
    }

    public CustomerDto updateCustomer(UpdateCustomerRequest request){
        Customer customer1 = getCustomerByCustomerId(request.getCustomerId());
        customer1.setPassword(request.getPassword());
        customer1.setMatchingPassword(request.getMatchingPassword());
        customer1.setFirstName(request.getFirstName());
        customer1.setLastName(request.getLastName());
        return customerDtoConverter.convert(customerDao.save(customer1));
    }
}
