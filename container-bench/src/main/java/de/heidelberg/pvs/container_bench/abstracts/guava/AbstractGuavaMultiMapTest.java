package de.heidelberg.pvs.container_bench.abstracts.guava;

import org.openjdk.jmh.annotations.Benchmark;

import com.google.common.collect.Multimap;

import de.heidelberg.pvs.container_bench.abstracts.AbstractMapTest;

public abstract class AbstractGuavaMultiMapTest<K, V> extends AbstractMapTest<K, V> {

	private K[] keys;
	private K[] newKeys;
	private V[] values;
	private Multimap<K, V> fullMap;

	protected abstract Multimap<K, V> getNewMultiMap(int size, int rangeOfKeys);

	protected abstract Multimap<K, V> copyMultiMap(Multimap<K, V> original);

	@Override
	public void testSetup() {

		fullMap = this.getNewMultiMap(size, rangeOfKeys);

		keys = keyGenerator.generateArrayInRange(size, rangeOfKeys);
		newKeys = keyGenerator.generateArrayInRange(size, 2 * rangeOfKeys); // 50 % of colision
		values = valueGenerator.generateArray(size);

		for (int i = 0; i < size; i++) {
			fullMap.put(keys[i], values[i]);
		}

	}

	@Override
	@Benchmark
	public void putAll() {
		Multimap<K, V> newMap = this.getNewMultiMap(size, rangeOfKeys);
		for(int i = 0; i < size; i++) {
			blackhole.consume(newMap.put(keys[i], values[i]));
		}
	}
	
	@Override
	@Benchmark
	public void putAndRemoveElement() {
		Integer index = this.keyGenerator.generateIndex(size);
		blackhole.consume(this.fullMap.put(newKeys[index], values[index]));
		blackhole.consume(this.fullMap.remove(newKeys[index], values[index]));
	}

	@Override
	@Benchmark
	public void getElement() {
		int index = keyGenerator.generateIndex(size);
		blackhole.consume(fullMap.get(keys[index]));
	}

	@Override
	@Benchmark
	public void containsElement() {
		int index = keyGenerator.generateIndex(size);
		blackhole.consume(fullMap.containsKey(keys[index]));
	}


	@Override
	@Benchmark
	public void copyMap() {
		Multimap<K, V> newMap = this.copyMultiMap(fullMap); 
		blackhole.consume(newMap);
	}

}
