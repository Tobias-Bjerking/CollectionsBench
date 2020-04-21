package se.su.dsv.online_concurrent;

import org.openjdk.jmh.infra.Blackhole;
import de.heidelberg.pvs.container_bench.generators.ElementGenerator;
import de.heidelberg.pvs.container_bench.generators.GeneratorFactory;
import de.heidelberg.pvs.container_bench.generators.PayloadType;
import org.openjdk.jmh.annotations.*;
import se.su.dsv.OnlineAdaptiveConcurrentDataStructure;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

//TODO
// Use two element generators, one for populating at start and to be used in the delete method
// and one to be used for adding new elements in the add method.
public class OnlineTestBench extends AbstractOnlineConcurrentBench{

    private final int generatorSize = 1048576*2;

    @Param
    OnlineConcurrentFact impl;

    String values[];

    ElementGenerator<String> valuesGenerator;

    Blackhole blackhole;

    CopyOnWriteArrayList<Object> adaptiveList;
    OperationGenerator operations;

    int iterations = 0;

    @Param("STRING_UNIFORM")
    PayloadType payloadType;

    @Setup(Level.Trial)
    @SuppressWarnings("unchecked")
    public void init(Blackhole bh) throws  IOException {
        
		valuesGenerator = (ElementGenerator<String>) GeneratorFactory.buildRandomGenerator(payloadType);
        valuesGenerator.init(generatorSize, seed);

        values = valuesGenerator.generateArray(generatorSize);

        adaptiveList = new CopyOnWriteArrayList<>();
        adaptiveList.addAll(Arrays.asList(Arrays.copyOfRange(values, 0, size)));

        blackhole = bh;
    }

    @Setup(Level.Iteration)
    @SuppressWarnings("unchecked")
    public void setup(Blackhole bh) throws IOException {
        adaptiveList = new CopyOnWriteArrayList<>();
        adaptiveList.addAll(Arrays.asList(Arrays.copyOfRange(values, 0, size)));
    }

	@Threads(2)
	@Benchmark
    public void iterate() {
        for(Object obj: adaptiveList){
            blackhole.consume(obj);
        }
    }
}
