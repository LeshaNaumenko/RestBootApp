package com.epam.producing.testProject.exception;

public class AddressNotFoundException extends Exception {
    public AddressNotFoundException(Long id) {
        super("Could not find address by user id: " + id);

    }
}
