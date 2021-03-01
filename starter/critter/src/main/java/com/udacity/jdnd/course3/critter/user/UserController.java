package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
//        customerDTO.setId(null);
        Customer customer = convertDTOToEntity(customerDTO);
        customer = customerService.save(customer);
        return convertEntityToDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.list();
        return customers.stream().map(UserController::convertEntityToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.get(petId);
        Customer customer = pet.getOwner();
        return convertEntityToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertDTOToEntity(employeeDTO);
        employee = employeeService.save(employee);
        return convertEntityToDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.get(employeeId);
        return convertEntityToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    private static CustomerDTO convertEntityToDTO(Customer entity) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getPets() != null) {
            dto.setPetIds(
                    entity.getPets().stream().map(Pet::getId).collect(Collectors.toList())
            );
        }
        return dto;
    }

    private Customer convertDTOToEntity(CustomerDTO dto) {
        Customer entity = new Customer();
        BeanUtils.copyProperties(dto, entity);
        if (dto.getPetIds() != null) {
            entity.setPets(
                    dto.getPetIds().stream().map(petId -> petService.get(petId)).collect(Collectors.toList())
            );
        }
        return entity;
    }

    private static EmployeeDTO convertEntityToDTO(Employee entity) {
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    private static Employee convertDTOToEntity(EmployeeDTO dto) {
        Employee entity = new Employee();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

}
