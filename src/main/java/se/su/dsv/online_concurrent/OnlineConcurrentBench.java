package se.su.dsv.online_concurrent;

import org.openjdk.jmh.infra.Blackhole;
import de.heidelberg.pvs.container_bench.generators.ElementGenerator;
import de.heidelberg.pvs.container_bench.generators.GeneratorFactory;
import de.heidelberg.pvs.container_bench.generators.PayloadType;
import org.openjdk.jmh.annotations.*;
import se.su.dsv.OnlineAdaptiveConcurrentDataStructure;

import java.io.IOException;
import java.util.Arrays;

//TODO
// Use two element generators, one for populating at start and to be used in the delete method
// and one to be used for adding new elements in the add method.
public class OnlineConcurrentBench extends AbstractOnlineConcurrentBench{

    private final int generatorSize = 1048576*2;

    @Param
    OnlineConcurrentFact impl;

    String values[];

    ElementGenerator<String> valuesGenerator;

    Blackhole blackhole;

    OnlineAdaptiveConcurrentDataStructure<Object> adaptiveList;
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
            case "iteratepure":
                operations = new OperationGenerator(0,0,100,0);
                break;
            default:
                throw new RuntimeException("Wrong test type: " + testType);
        }
        valuesGenerator = (ElementGenerator<String>) GeneratorFactory.buildRandomGenerator(PayloadType.STRING_UNIFORM);
        valuesGenerator.init(generatorSize, seed);

        values = valuesGenerator.generateArray(generatorSize);

        adaptiveList = impl.maker.get();
        adaptiveList.setup(Arrays.copyOfRange(values, 0, size));

        blackhole = bh;
    }

    @Setup(Level.Iteration)
    @SuppressWarnings("unchecked")
    public void setup(Blackhole bh) throws IOException {
        operations.reset();
        adaptiveList = impl.maker.get();
        adaptiveList.setup(Arrays.copyOfRange(values, 0, size));
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
        int index = valuesGenerator.generateIndex(generatorSize);
        blackhole.consume(adaptiveList.contains(values[index]));
    }

    public void insert(){
        adaptiveList.add(values[valuesGenerator.generateIndex(generatorSize)]);
    }

    public void iterate() {
        for(Object obj: adaptiveList){
            if (Thread.currentThread().isInterrupted())
                break;
            blackhole.consume(obj);
        }
    }

    public void remove(){
        adaptiveList.remove(values[valuesGenerator.generateIndex(generatorSize)]);
    }
}
