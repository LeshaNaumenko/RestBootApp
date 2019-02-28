package com.epam.producing.testProject.configuration;

import com.epam.producing.testProject.model.Address;
import com.epam.producing.testProject.model.Employee;
import com.epam.producing.testProject.repository.AddressRepository;
import com.epam.producing.testProject.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, AddressRepository addressRepository) {
        return args -> {

            List<Address> kyiv = Arrays.asList(addressRepository.save(new Address("Kyiv", "Ukraine", "Grushevskogo 1001")),
                    addressRepository.save(new Address("Kyiv1", "Ukraine", "Grushevskogo 1002")),
                    addressRepository.save(new Address("Kyiv2", "Ukraine", "Grushevskogo 1003")),
                    addressRepository.save(new Address("Kyiv3", "Ukraine", "Grushevskogo 1004")));
            List<Address> brovary = Arrays.asList(
                    addressRepository.save(new Address("Brovary", "Ukraine", "Kyivska 1001")),
                    addressRepository.save(new Address("Brovary1", "Ukraine", "Kyivska 1002")),
                    addressRepository.save(new Address("Brovary2", "Ukraine", "Kyivska 1003")),
                    addressRepository.save(new Address("Brovary3", "Ukraine", "Kyivska 1004"))
            );
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar", kyiv)));
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief", brovary)));
        };


    }

   /* @Bean
    CommandLineRunner initDatabase(AddressRepository addressRepository) {
        return args -> {

            List<Address> addressList = Arrays.asList(addressRepository.save(new Address("Kyiv", "Ukraine", "Grushevskogo 1001")),
            addressRepository.save(new Address("Kyiv1", "Ukraine", "Grushevskogo 1002")),
            addressRepository.save(new Address("Kyiv2", "Ukraine", "Grushevskogo 1003")),
            addressRepository.save(new Address("Kyiv3", "Ukraine", "Grushevskogo 1004")));
        };
    }*/


}