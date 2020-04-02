package se.su.dsv.online_concurrent;

import de.heidelberg.pvs.container_bench.generators.ElementGenerator;
import de.heidelberg.pvs.container_bench.generators.GeneratorFactory;
import de.heidelberg.pvs.container_bench.generators.PayloadType;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class OnlineConcurrentListBench extends AbstractOnlineConcurrentBench{
    private final int generatorSize = 1048576*2;

    @Param
    OnlineConcurrentListFact impl;

    String values[];

    ElementGenerator<String> valuesGenerator;

    Blackhole blackhole;

    Collection<Object> adaptiveList;
    OperationGenerator operations;

    @Param("STRING_UNIFORM")
    PayloadType payloadType;

    @Param("even")
    String testType;

    @Setup(Level.Trial)
    @SuppressWarnings("unchecked")
    public void init(Blackhole bh) throws IOException {
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
        valuesGenerator = (ElementGenerator<String>) GeneratorFactory.buildRandomGenerator(payloadType);
        valuesGenerator.init(generatorSize, seed);

        values = valuesGenerator.generateArray(generatorSize);

        adaptiveList = impl.maker.get();
        adaptiveList.addAll(Arrays.asList(Arrays.copyOfRange(values, 0, size)));

        blackhole = bh;
    }

    @Setup(Level.Iteration)
    @SuppressWarnings("unchecked")
    public void setup(Blackhole bh) throws IOException {
        operations.reset();
        adaptiveList = impl.maker.get();
        adaptiveList.addAll(Arrays.asList(Arrays.copyOfRange(values, 0, size)));
    }


    public void operationsRunner(){
        switch (operations.getOperation()){
            case 1:
                contains();
                break;
            case 2:
                insert();
                break;
            case 3:
                //iterate();
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

    @Benchmark
    public void iterate(Blackhole bh) {
        for(Object obj: adaptiveList){
            if (Thread.currentThread().isInterrupted())
                break;
            bh.consume(obj);
        }
    }

    public void remove(){
        adaptiveList.remove(values[valuesGenerator.generateIndex(generatorSize)]);
    }
}
