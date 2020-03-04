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

    //===========================================================
    // READ
    //===========================================================

    // contains / look up
    @Benchmark
    @Group("Read")
    @GroupThreads(1)
    public void containsRead() {
        int index = valuesGenerator.generateIndex(size);
        blackhole.consume(sharedEmptyList.contains(values[index]));
    }


    // iteration / for each
    @Benchmark
    @Group("Read")
    @GroupThreads(1)
    public void iterateRead() {
        for(Object obj: sharedEmptyList){
            blackhole.consume(obj);
        }
    }

    // index operation / insert
    @Benchmark
    @Group("Read")
    @GroupThreads(1)
    public void insertRead(){
        sharedEmptyList.add(values[valuesGenerator.generateIndex(size)]);
    }

    // search and remove / remove
    @Benchmark
    @Group("Read")
    @GroupThreads(1)
    public void removeRead(){
        sharedEmptyList.remove(values[valuesGenerator.generateIndex(size)]);
    }

    //===========================================================
    // WRITE
    //===========================================================

    // contains / look up
    @Benchmark
    @Group("Write")
    @GroupThreads(1)
    public void containsWrite() {
        int index = valuesGenerator.generateIndex(size);
        blackhole.consume(sharedEmptyList.contains(values[index]));
    }


    // iteration / for each
    @Benchmark
    @Group("Write")
    @GroupThreads(1)
    public void iterateWrite() {
        for(Object obj: sharedEmptyList){
            blackhole.consume(obj);
        }
    }

    // index operation / insert
    @Benchmark
    @Group("Write")
    @GroupThreads(1)
    public void insertWrite(){
        sharedEmptyList.add(values[valuesGenerator.generateIndex(size)]);
    }

    // search and remove / remove
    @Benchmark
    @Group("Write")
    @GroupThreads(1)
    public void removeWrite(){
        sharedEmptyList.remove(values[valuesGenerator.generateIndex(size)]);
    }

    //===========================================================
    // ITERATE
    //===========================================================

    // contains / look up
    @Benchmark
    @Group("Iterate")
    @GroupThreads(1)
    public void containsIterate() {
        int index = valuesGenerator.generateIndex(size);
        blackhole.consume(sharedEmptyList.contains(values[index]));
    }


    // iteration / for each
    @Benchmark
    @Group("Write")
    @GroupThreads(1)
    public void iterateIterate() {
        for(Object obj: sharedEmptyList){
            blackhole.consume(obj);
        }
    }

    // index operation / insert
    @Benchmark
    @Group("Iterate")
    @GroupThreads(1)
    public void insertIterate(){
        sharedEmptyList.add(values[valuesGenerator.generateIndex(size)]);
    }

    // search and remove / remove
    @Benchmark
    @Group("Iterate")
    @GroupThreads(1)
    public void removeIterate(){
        sharedEmptyList.remove(values[valuesGenerator.generateIndex(size)]);
    }

}
