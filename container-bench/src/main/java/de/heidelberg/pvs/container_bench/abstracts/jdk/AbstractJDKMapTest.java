package de.heidelberg.pvs.container_bench.abstracts.jdk;

import java.util.Map;
import java.util.Map.Entry;

import org.openjdk.jmh.annotations.Benchmark;

import de.heidelberg.pvs.container_bench.abstracts.AbstractMapTest;

public abstract class AbstractJDKMapTest<K, V> extends AbstractMapTest<K, V> {

	private Map<K, V> fullMap;
	private K[] keys;
	private K[] newKeys;
	private V[] values;
	
	protected abstract Map<K, V> getNewMap(int size, int range);
	protected abstract Map<K, V> copyMap(Map<K, V> fullMap2);
	
	@Override
	public void testSetup() {
		int varietyOfKeys = (int) (size * ((double)percentageRangeKeys / 100));
		fullMap = this.getNewMap(size, varietyOfKeys);

		keys = keyGenerator.generateArrayInRange(size, varietyOfKeys);
		newKeys = keyGenerator.generateArrayInRange(size, 2 * varietyOfKeys);
		
		values = valueGenerator.generateArray(size);

		for(int i = 0; i < size; i++) {
			fullMap.put(keys[i], values[i]);
		}
		
	}
	
	@Override
	@Benchmark
	public void addAll() {
		Map<K, V> newMap = this.getNewMap(size, percentageRangeKeys);
		for(int i = 0; i < size; i++) {
			blackhole.consume(newMap.put(keys[i], values[i]));
		}
	}
	
	@Override
	@Benchmark
	public void addElement() {
		Integer index = this.keyGenerator.generateIndex(size);
		blackhole.consume(this.fullMap.put(newKeys[index], values[index]));
		blackhole.consume(this.fullMap.remove(newKeys[index]));
	}
	
	

	@Override
	@Benchmark
	public void containsElement() {
		int index = keyGenerator.generateIndex(size);
		blackhole.consume(fullMap.containsKey(keys[index]));
	}

	@Override
	@Benchmark
	public void getElement() {
		int index = keyGenerator.generateIndex(size);
		blackhole.consume(fullMap.get(keys[index]));
		
	}

	@Override
	@Benchmark
	public void copy() {
		Map<K, V> newMap = this.copyMap(fullMap); 
		blackhole.consume(newMap);
	}
	
	@Override
	@Benchmark
	public void removeElement() {
		Integer index = this.keyGenerator.generateIndex(size);
		blackhole.consume(this.fullMap.remove(keys[index]));
		blackhole.consume(this.fullMap.put(keys[index], values[index])); // Keeping the steady-state
	}
	
	@Override
	@Benchmark
	public void getAll() {
		for(Entry<K, V> entry : this.fullMap.entrySet()) {
			blackhole.consume(entry);
		}
	}
	
}
