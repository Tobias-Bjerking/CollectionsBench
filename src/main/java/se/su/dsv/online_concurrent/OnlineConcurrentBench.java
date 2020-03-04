package se.su.dsv.online_concurrent;

import org.openjdk.jmh.infra.Blackhole;
import de.heidelberg.pvs.container_bench.generators.ElementGenerator;
import de.heidelberg.pvs.container_bench.generators.GeneratorFactory;
import de.heidelberg.pvs.container_bench.generators.PayloadType;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.List;

public class OnlineConcurrentBench extends AbstractOnlineConcurrentBench{

    static int x = 0;

    @Param
    OnlineConcurrentFact impl;

    String values[];

    ElementGenerator<String> valuesGenerator;

    Blackhole blackhole;

    List<Object> sharedEmptyList;

    @Param("STRING_DICTIONARY")
    PayloadType payloadType;

    @Setup(Level.Iteration)
    @SuppressWarnings("unchecked")
    public void setup(Blackhole bh) throws IOException {
        System.out.println("=================" + x++ + "=================");
        sharedEmptyList = impl.maker.get();

        valuesGenerator = (ElementGenerator<String>) GeneratorFactory.buildRandomGenerator(PayloadType.STRING_DICTIONARY);
        valuesGenerator.init(size, seed);

        values = valuesGenerator.generateArray(size);

        blackhole = bh;
    }

    // contains / look up
    @Benchmark
    @Group("OnlineConcurrent")
    @GroupThreads(1)
    public void contains() {
        int index = valuesGenerator.generateIndex(size);
        blackhole.consume(sharedEmptyList.contains(values[index]));
    }


    // iteration / for each
    @Benchmark
    @Group("OnlineConcurrent")
    @GroupThreads(1)
    public void iterate() {
        for(Object obj: sharedEmptyList){
            blackhole.consume(obj);
        }
    }

    // index operation / insert
    @Benchmark
    @Group("OnlineConcurrent")
    @GroupThreads(1)
    public void insert(){
        sharedEmptyList.add(values[valuesGenerator.generateIndex(size)]);
    }

    // search and remove / remove
    @Benchmark
    @Group("OnlineConcurrent")
    @GroupThreads(1)
    public void remove(){
        sharedEmptyList.remove(values[valuesGenerator.generateIndex(size)]);
    }



}
