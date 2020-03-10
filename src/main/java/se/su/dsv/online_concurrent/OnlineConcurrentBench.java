package se.su.dsv.online_concurrent;

import org.openjdk.jmh.infra.Blackhole;
import de.heidelberg.pvs.container_bench.generators.ElementGenerator;
import de.heidelberg.pvs.container_bench.generators.GeneratorFactory;
import de.heidelberg.pvs.container_bench.generators.PayloadType;
import org.openjdk.jmh.annotations.*;
import se.su.dsv.OnlineAdaptiveConcurrentDataStructure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OnlineConcurrentBench extends AbstractOnlineConcurrentBench{

    @Param
    OnlineConcurrentFact impl;

    String values[];

    ElementGenerator<String> valuesGenerator;

    Blackhole blackhole;

    OnlineAdaptiveConcurrentDataStructure<Object> sharedEmptyList;
    OperationGenerator operations;

    int iterations = 0;

    @Param("STRING_DICTIONARY")
    PayloadType payloadType;

    @Param("even")
    String testType;

    @Setup(Level.Trial)
    @SuppressWarnings("unchecked")
    public void init(Blackhole bh) throws  IOException {
        System.out.println("=====Initiating operation distibution: " + testType + "=====");
        switch (testType){
            case "even":
                operations = new OperationGenerator(34,20,33,13);
                break;
            case "iterate":
                operations = new OperationGenerator(0,10,85,5);
                break;
            case "update":
                operations = new OperationGenerator(20, 28, 30, 22);
                break;
            default:
                throw new RuntimeException("Wrong test type: " + testType);
        }
        sharedEmptyList = impl.maker.get();
        valuesGenerator = (ElementGenerator<String>) GeneratorFactory.buildRandomGenerator(PayloadType.STRING_DICTIONARY);
        valuesGenerator.init(size, seed);

        values = valuesGenerator.generateArray(size);

        blackhole = bh;
    }

    @Setup(Level.Iteration)
    @SuppressWarnings("unchecked")
    public void setup(Blackhole bh) throws IOException {
        operations.reset();
    }


    @Benchmark
    public void operationsRunner(){
        switch (operations.getOperation()){
            case 1:
                contains();
                break;
            case 2:
                insert();
                break;
            case 3:
                iterate();
                break;
            case 4:
                remove();
                break;
        }
    }

    public void contains() {
        int index = valuesGenerator.generateIndex(size);
        blackhole.consume(sharedEmptyList.contains(values[index]));
    }

    public void insert(){
        sharedEmptyList.add(values[valuesGenerator.generateIndex(size)]);
    }

    public void iterate() {
        for(Object obj: sharedEmptyList){
            blackhole.consume(obj);
        }
    }

    public void remove(){
        sharedEmptyList.remove(values[valuesGenerator.generateIndex(size)]);
    }
}
