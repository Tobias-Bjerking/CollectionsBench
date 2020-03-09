package se.su.dsv.online_concurrent;

import org.openjdk.jmh.infra.Blackhole;
import de.heidelberg.pvs.container_bench.generators.ElementGenerator;
import de.heidelberg.pvs.container_bench.generators.GeneratorFactory;
import de.heidelberg.pvs.container_bench.generators.PayloadType;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvenOnlineConcurrentBench extends AbstractOnlineConcurrentBench{

    @Param
    OnlineConcurrentFact impl;

    String values[];

    ElementGenerator<String> valuesGenerator;

    Blackhole blackhole;

    List<Object> sharedEmptyList;
    OperationGenerator operations;

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
    }

    @Setup(Level.Iteration)
    @SuppressWarnings("unchecked")
    public void setup(Blackhole bh) throws IOException {
        sharedEmptyList = impl.maker.get();

        valuesGenerator = (ElementGenerator<String>) GeneratorFactory.buildRandomGenerator(PayloadType.STRING_DICTIONARY);
        valuesGenerator.init(size, seed);

        values = valuesGenerator.generateArray(size);

        blackhole = bh;

        operations.reset();
    }


    @Benchmark
    public void evenIntensity(){
        switch (operations.getOperation()){
            case 1:
                containsRead();
                break;
            case 2:
                insertRead();
                break;
            case 3:
                iterateRead();
                break;
            case 4:
                removeRead();
                break;
        }
    }

    public void containsRead() {
        int index = valuesGenerator.generateIndex(size);
        blackhole.consume(sharedEmptyList.contains(values[index]));
    }

    public void insertRead(){
        sharedEmptyList.add(values[valuesGenerator.generateIndex(size)]);
    }

    public void iterateRead() {
        for(Object obj: sharedEmptyList){
            blackhole.consume(obj);
        }
    }

    public void removeRead(){
        sharedEmptyList.remove(values[valuesGenerator.generateIndex(size)]);
    }
}
