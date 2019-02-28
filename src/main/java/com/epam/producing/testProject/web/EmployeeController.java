package com.epam.producing.testProject.web;

import com.epam.producing.testProject.exception.EmployeeNotFoundException;
import com.epam.producing.testProject.model.Address;
import com.epam.producing.testProject.model.Employee;
import com.epam.producing.testProject.repository.AddressRepository;
import com.epam.producing.testProject.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, AddressRepository addressRepository) {
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
    }


    @GetMapping("/employees")
    List<Employee> all() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    ResponseEntity<Employee> newEmployee(@RequestBody Employee newEmployee) {
        return new ResponseEntity<>(employeeRepository.save(newEmployee), HttpStatus.OK);
    }

    // Single item

    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return employeeRepository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

    //for addresses

    @GetMapping("/addresses")
    List<Address> allAddresses() {
        return employeeRepository.findAll()
                .stream()
                .flatMap(employee -> employee.getAddresses().stream())
                .collect(Collectors.toList());
    }

    @GetMapping("/employees/{id}/addresses")
    List<Address> allAddressesByUserId(@PathVariable Long id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id))
                .getAddresses();
    }

    @PostMapping("/employees/{id}/addresses")
    ResponseEntity<Address> newAddresses(@PathVariable Long id, @RequestBody Address address) {
        employeeRepository.findById(id)
                .map(employee -> {
                    employee.addAddress(address);
                    employeeRepository.save(employee);
                    return address;
                })
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return new ResponseEntity<>(addressRepository.save(address), HttpStatus.OK);
    }

    @DeleteMapping("addresses/{id}")
    void deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);
    }
}
