package com.mywebflux;

class RunPracticeFlux {

    public static void main(String[] args) {
        RunPracticeFlux obj = new RunPracticeFlux();
        obj.process();
    }

    public void process() {
        PracticeFlux testFlux = new PracticeFlux();
        testFlux.getFlatMap(2).subscribe(x -> {
            System.out.println("kishor"+x);
        });
    }

}
