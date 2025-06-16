package in.singhakshay.customer_service.controller;

import in.singhakshay.customer_service.model.Customer;
import in.singhakshay.customer_service.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();

        customer1 = new Customer();
        customer1.setId(UUID.randomUUID().toString());
        customer1.setName("John Doe");
        customer1.setEmail("john@example.com");

        customer2 = new Customer();
        customer2.setId(UUID.randomUUID().toString());
        customer2.setName("Jane Smith");
        customer2.setEmail("jane@example.com");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    @Test
    void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/api/v1/customers/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", anyOf(is("John Doe"), is("Jane Smith"))));
    }

    @Test
    void testGetCustomerById() throws Exception {
        mockMvc.perform(get("/api/v1/customers")
                .param("customerId", customer1.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }
}