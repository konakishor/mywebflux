package com.mywebflux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {
    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("kishor","vijay","kona")).log();
    }

    public Flux<String> example_Map() {
        return Flux.fromIterable(List.of("kishor","vijay","kona"))
                .map(x -> x.toUpperCase())
                .log();
    }

    public Flux<String> example_Immunitable() {
        var namesFlux =  Flux.fromIterable(List.of("kishor","vijay","kona"));
        namesFlux.map(x -> x.toUpperCase()).log();
        return namesFlux;
    }

    // Filters
    public Flux<String> example_Filter_Names(int stringLength) {
        return Flux.fromIterable(List.of("kishor","vijay","kona"))
                .map(x -> x.toUpperCase())
                .filter(s -> s.length()>stringLength)
                .map(x -> stringLength+"-"+x)
                .log();
    }

    public Flux<String> example_Filter_AndConcnate(int stringLength) {
        return Flux.fromIterable(List.of("kishor","vijay","kona"))
                .map(x -> x.toUpperCase())
                .filter(s -> s.length()>stringLength)
                .map(x -> stringLength+"-"+x)
                .log();
    }

    //FlatMap
    public Flux<String> example_FlatMap(int stringLength) {
        return Flux.fromIterable(List.of("kishor","vijay","kona"))
                .map(x -> x.toUpperCase())
                .filter(s -> s.length()>stringLength)
                .flatMap(s -> splitStringFlux(s))
                .log();
    }
    private Flux<String> splitStringFlux(String name) {
        System.out.println("Name="+name);
        var charArr = name.split("");
        return Flux.fromArray(charArr);
    }

    //flatMap with Async
    public Flux<String> example_FlatMapAsync(int stringLength) {
        return Flux.fromIterable(List.of("kishor","vijay","kona"))
                .map(x -> x.toUpperCase())
                .filter(s -> s.length()>stringLength)
                .flatMap(s -> splitStringWithDelay(s))
                .log();
    }
    private Flux<String> splitStringWithDelay(String name) {
        System.out.println("Name="+name);
        var delay = new Random().nextInt(1000);
        var charArr = name.split("");
        return Flux.fromArray(charArr)
                .delayElements(Duration.ofMillis(delay));
    }

    //concatMap() operator
    public Flux<String> example_ConcatMapAsync(int stringLength) {
        return Flux.fromIterable(List.of("kishor","vijay","kona"))
                .map(x -> x.toUpperCase())
                .filter(s -> s.length()>stringLength)
                .concatMap(s -> splitStringWithDelay(s))
                .log();
    }

    //flatMap() operator in Mono
    public Mono<List<String>> example_FlatMapAsMono(int stringLength) {
        return Mono.just("kishor")
                .map(x -> x.toUpperCase())
                .filter(s -> s.length()>stringLength)
                .flatMap(s -> splitStringMono(s))
                .log();
    }
    private Mono<List<String>> splitStringMono(String str) {
        var charArr = str.split("");
        return Mono.just(List.of(charArr));
    }

    //flatMapMany() operator in Mono
    public Flux<String> example_FlatMapMany(int stringLength) {
        return Mono.just("kishor")
                .map(x -> x.toUpperCase())
                .filter(s -> s.length()>stringLength)
                .flatMapMany(s -> splitStringFlux(s))
                .log();
    }

    //transform() operator in Mono
    public Flux<String> example_Transform(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length()>stringLength);
        return Flux.fromIterable(List.of("kishor","vijay","kona"))
                .transform(filterMap)
                .flatMap(s -> splitStringFlux(s))
                .log();
    }

    //defaultIfEmpty() operator in Mono
    public Flux<String> example_DefaultIfEmpty(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length()>stringLength);
        return Flux.fromIterable(List.of("kishor","vijay","kona"))
                .transform(filterMap)
                .flatMap(s -> splitStringFlux(s))
                .defaultIfEmpty("default")
                .log();
    }

    //switchIfEmpty() operator in Mono
    public Flux<String> example_SwitchIfEmpty(int stringLength) {
        Function<Flux<String>, Flux<String>> filterMap =
                name -> name.map(String::toUpperCase)
                .filter(s -> s.length()>stringLength)
                .flatMap(s -> splitStringFlux(s));

        var defaultFlux = Flux.just("default")
                .transform(filterMap); // "D","E","F","A","U","L","T"

        return Flux.fromIterable(List.of("kishor","vijay","kona"))
                .transform(filterMap)
                .switchIfEmpty(defaultFlux)
                .log();
    }

    //concat()
    public Flux<String> example_Concat() {
        var abcFlux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        return Flux.concat(abcFlux, defFlux).log();
    }

    //concatWith()
    public Flux<String> example_ConcatWith() {
        /*
        var abcFlux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        return abcFlux.concatWith(defFlux).log();
        */

        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.concatWith(bMono).log();
    }

    public Mono<String> namesMono() {
        return Mono.just("kishor").log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();
        /*
        service.namesFlux().subscribe(x -> {
            System.out.println(x);
        });
        */
        service.namesMono().subscribe(x -> {
            System.out.println(x);
        });
    }
}
