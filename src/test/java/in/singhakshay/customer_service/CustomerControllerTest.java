// Java
package in.singhakshay.customer_service;

import in.singhakshay.customer_service.model.Customer;
import in.singhakshay.customer_service.controller.CustomerController;
import in.singhakshay.customer_service.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;






class CustomerControllerTest {

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private CustomerController customerController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllCustomers() {
    Customer customer1 = new Customer();
    customer1.setId("1");
    Customer customer2 = new Customer();
    customer2.setId("2");
    List<Customer> customers = Arrays.asList(customer1, customer2);

    when(customerService.listAllCustomers()).thenReturn(customers);

    ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    verify(customerService, times(1)).listAllCustomers();
  }

  @Test
  void testGetCustomerById() {
    String customerId = "123";
    Customer customer = new Customer();
    customer.setId(customerId);

    when(customerService.getCustomerById(customerId)).thenReturn(customer);

    ResponseEntity<Customer> response = customerController.getCustomerById(customerId);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(customerId, response.getBody().getId());
    verify(customerService, times(1)).getCustomerById(customerId);
  }
}