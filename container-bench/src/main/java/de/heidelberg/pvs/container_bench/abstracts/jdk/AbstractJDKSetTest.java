package de.heidelberg.pvs.container_bench.abstracts.jdk;

import java.util.Set;

import org.openjdk.jmh.annotations.Benchmark;

import de.heidelberg.pvs.container_bench.abstracts.AbstractSetTest;

/**
 * Abstract class for every test with JDK Sets implementation
 * @author Diego
 *
 * @param <T>
 * 		The held type of the {@link Set} implementation
 */
public abstract class AbstractJDKSetTest<T> extends AbstractSetTest<T> {
	
	private Set<T> fullSet;
	private T[] values;
	private T[] newValues;
	private int newValuesSize;
	
	public void testSetup() {
		newValuesSize = 2 * size; // 50% of colision
		fullSet = this.getNewSet(size);
		values = generator.generateArray(size);
		newValues = generator.generateArray(2 * size);
		for(int i = 0; i < size; i++) {
			fullSet.add(values[i]);
		}
	}
	
	protected abstract Set<T> getNewSet(int size);
	protected abstract Set<T> copySet(Set<T> fullSet2);
	
	@Override
	@Benchmark
	public void getAll() { 
		for(T element : fullSet) {
			blackhole.consume(element);
		}
	}
	
	@Override
	@Benchmark
	public void containsElement() {
		Integer index = generator.generateIndex(size);
		blackhole.consume(fullSet.contains(values[index]));
	}

	@Override
	@Benchmark
	public void addAll() {
		Set<T> newSet = this.getNewSet(size);
		for(int i = 0; i < size; i++) {
			blackhole.consume(newSet.add(values[i]));
		}
	}
	
	@Override
	@Benchmark
	public void addAndRemoveElement() {
		Integer index = this.generator.generateIndex(newValuesSize);
		blackhole.consume(this.fullSet.add(newValues[index]));
		blackhole.consume(this.fullSet.remove(newValues[index]));
	}

	@Override
	@Benchmark
	public void copySet() {
		Set<T> newSet = this.copySet(fullSet);
		blackhole.consume(newSet);
	}
	
	@Override
	@Benchmark
	public void getSize() {
		blackhole.consume(fullSet.size());
	}
}