package com.mywebflux;

import com.mywebflux.helper.TestHelper;
import com.mywebflux.model.Customer;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class TestPracticeFlux {
    PracticeFlux testFlux = new PracticeFlux();

    @Test
    void test_GetCountryNames() {
        var countryNames = testFlux.getCountryNames();
        StepVerifier.create(countryNames)
                .expectNext("India","US","UK","France")
                .verifyComplete();
    }

    @Test
    void test_GetCountryNames1() {
        testFlux.customersConvertListToFlux1();
        //testFlux.convertFluxToMono();
    }

    @Test
    void test_GetCustomers() {
        TestHelper testHelper = new TestHelper();
        List<Customer> lst = testHelper.getCustomerDetails();
        lst.stream().forEach(x -> System.out.println(x));
    }

}
