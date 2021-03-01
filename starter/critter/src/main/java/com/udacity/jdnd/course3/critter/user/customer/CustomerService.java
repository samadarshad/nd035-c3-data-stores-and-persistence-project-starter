package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.User;
import com.udacity.jdnd.course3.critter.user.UserService;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService extends UserService<Customer> {
    @Autowired
    CustomerRepository customerRepository;

    public Customer get(Long id) {
        Optional<Customer> optional = customerRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new CustomerNotFoundException();
        }
    }

    public List<Customer> getByName(String name) {
        return customerRepository.getAllByName(name);
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        customerRepository.save(customer);
        assert(customer.getId() != null);

        customer.getPets().forEach(pet -> pet.setOwner(customer));
        return customer;
    }

    public Customer getOwnerByPet(Pet pet) {
        return pet.getOwner();
    }

}
