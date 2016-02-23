package de.heidelberg.pvs.container_bench.abstracts.hppc;

import org.openjdk.jmh.annotations.Benchmark;

import com.carrotsearch.hppc.ObjectArrayList;
import com.carrotsearch.hppc.cursors.ObjectCursor;

import de.heidelberg.pvs.container_bench.abstracts.AbstractListTest;

public abstract class AbstractHPPCListTest<T> extends AbstractListTest<T> {

	ObjectArrayList<T> fullList;
	T[] values;

	protected abstract ObjectArrayList<T> getNewList(int size);
	protected abstract ObjectArrayList<T> copyList(ObjectArrayList<T> original);
	
	@Override
	public void testSetup() {
		fullList = this.getNewList(size);
		values = this.generator.generateArray(size);
		for (int i = 0; i < size; i++) {
			fullList.add(values[i]);
		}

	}

	@Override
	@Benchmark
	public void getAll() {
		for (ObjectCursor<T> element : fullList) {
			blackhole.consume(element);
		}

	}

	@Override
	@Benchmark
	public void getElement() {
		Integer index = generator.generateIndex(size);
		blackhole.consume(fullList.get(index));
	}

	@Override
	@Benchmark
	public void containsElement() {
		Integer index = generator.generateIndex(size);
		blackhole.consume(fullList.contains(values[index]));
	}

	@Override
	@Benchmark
	public void addAll() {
		ObjectArrayList<T> newList = new ObjectArrayList<>();
		for (int i = 0; i < size; i++) {
			newList.add(values[i]); // void
		}
		blackhole.consume(newList);
	}

	@Override
	@Benchmark
	public void copyList() {
		ObjectArrayList<T> newList = new ObjectArrayList<>(fullList);
		blackhole.consume(newList);
	}
	
	@Override
	@Benchmark
	public void addAndRemoveElement() {
		Integer index = generator.generateIndex(size);
		fullList.add(values[index]);
		blackhole.consume(fullList.remove(size)); 
	}
	
	@Override
	@Benchmark
	public void getSize() {
		blackhole.consume(fullList.size());
	}


}