package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;

    public Pet get(Long id) {
        Optional<Pet> optional = petRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new PetNotFoundException();
        }
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> list() {
        return petRepository.findAll();
    }

    public List<Pet> listByOwner(Customer customer) {
        return customer.getPets();
    }

}
