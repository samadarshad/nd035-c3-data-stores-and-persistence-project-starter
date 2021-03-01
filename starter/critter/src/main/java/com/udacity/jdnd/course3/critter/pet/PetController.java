package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertDTOToEntity(petDTO);
        pet = petService.save(pet);

        //update parent
        customerService.get(petDTO.getOwnerId()).addToPets(pet);
        return convertEntityToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.get(petId);
        return convertEntityToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.list();
        return pets.stream().map(PetController::convertEntityToDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.get(ownerId);
        List<Pet> pets = petService.listByOwner(customer);
        return pets.stream().map(PetController::convertEntityToDTO).collect(Collectors.toList());
    }

    private static PetDTO convertEntityToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }

    private Pet convertDTOToEntity(PetDTO dto) {
        Pet entity = new Pet();
        BeanUtils.copyProperties(dto, entity);
        entity.setOwner(
                customerService.get(dto.getOwnerId())
        );
        return entity;
    }
}
