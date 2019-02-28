package com.epam.producing.testProject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String role;

    @OneToMany
    @JoinColumn(name = "employee_id")
    private List<Address> addresses = new ArrayList<>();

    public Employee(String name, String role, List<Address> addresses) {
        this.name = name;
        this.role = role;
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        if (!addresses.contains(address))
            addresses.add(address);
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
