package com.mywebflux;

import com.mywebflux.helper.TestHelper;
import com.mywebflux.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class PracticeFlux {
    TestHelper testHelper = new TestHelper();

    public Flux<String> getCountryNames() {
        return Flux.fromIterable(List.of("India","US","UK","France")).log();
    }

    public Flux<String> getCountryNamesInCaps() {
        return Flux.fromIterable(List.of("India","US","UK","France"))
                .map(x -> x.toUpperCase());
    }

    public Flux<String> getFilterCountries(int count) {
        return Flux.fromIterable(List.of("India","US","UK","France"))
                .map(x -> x.toUpperCase())
                .filter(x -> x.length()>count)
                .map(x -> x.length()+"-"+x);
    }

    public Flux<String> getFlatMap(int count) {
        /*
        var rslt = Flux.fromIterable(List.of("India","US","UK","France"))
                .map(x -> x.toUpperCase())
                .filter(x -> x.length()>count);
         */
        System.out.println("------ Start -------");
        var flex1 = splitStringReturnFlux("France");
        /*
        flex1.subscribe(x -> {
            System.out.println("kona"+x);
        });
         */
        System.out.println("------ End -------");
        return flex1;
    }

    private Flux<String> splitStringReturnFlux(String name) {
        System.out.println("Value="+name);
        var charArr = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(charArr);
                //.delayElements(Duration.ofMillis(delay));
    }

    public void customersConvertListToFlux() {
        Flux<Customer> custFlux = Flux.fromIterable(testHelper.getCustomerDetails());
        Flux<String> rslt = custFlux.map(x -> {
            return x.getEmail();
        });
        rslt.subscribe(x -> {
            System.out.println("kona->"+x);
        });
    }

    public void convertFluxToMono() {
        Flux<Customer> custFlux = Flux.fromIterable(testHelper.getCustomerDetails());
        Flux<String> rslt = custFlux.flatMap(x -> {
            return Flux.fromIterable(x.getPhoenNos());
        });
        //Mono<String> k = Mono.from(rslt);
        Mono<List<String>> custList = rslt.collectList();
        System.out.println(custList.block());
    }

    public void getFilterCountries2(int count) {
        Flux<String> rest = Flux.fromIterable(List.of("India","US","UK","France"))
                .map(x -> x.toUpperCase())
                .filter(x -> x.length()>count)
                .map(x -> x.length()+"-"+x)
                .flatMap(x -> splitStringFlux(x));
        System.out.println("--------- start printing ---------");
        rest.subscribe(x -> {
            System.out.println(x);
        });
    }

    private Flux<String> splitStringFlux(String name) {
        System.out.println("Name="+name);
        var charArr = name.split("");
        return Flux.fromArray(charArr);
    }

    public void customersConvertListToFlux1() {
        Flux<Customer> custFlux = Flux.fromIterable(testHelper.getSomeCustomerDetails(1));
        //jbutt@gmail.com
        Flux<String> rslt = custFlux.map(x -> {
            return x.getEmail();
        }).flatMap(x -> splitStringFlux(x));
        rslt.subscribe(x -> {
            System.out.println("kona->"+x);
        });
    }

    private Flux<String> splitString(String value) {
        return Flux.fromArray(value.split("")).delayElements(Duration.ofMillis(1000L));
    }

}
