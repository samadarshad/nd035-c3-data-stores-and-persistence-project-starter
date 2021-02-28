package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;


}
