package de.heidelberg.pvs.container_bench.abstracts.jdk;

import java.util.List;

import org.openjdk.jmh.annotations.Benchmark;

import de.heidelberg.pvs.container_bench.abstracts.AbstractListTest;

/**
 * Abstract class for every test with JDK Lists implementation
 * 
 * @author Diego
 *
 * @param <T>
 *            The held type of the {@link List} implementation
 */
public abstract class AbstractJDKListTest<T> extends AbstractListTest<T> {

	private List<T> fullList;
	protected T[] values;

	protected abstract List<T> getNewList();

	protected abstract List<T> copyList(List<T> fullList2);

	@Override
	public void testSetup() {
		fullList = this.getNewList();
		values = generator.generateArray(size);
		for (int i = 0; i < size; i++) {
			fullList.add(values[i]);
		}
	}

	@Override
	@Benchmark
	public void iterate() {
		for (T element : fullList) {
			blackhole.consume(element);
		}
	}

	@Override
	@Benchmark
	public void getElement() {
		int index = generator.generateIndex(size);
		blackhole.consume(fullList.get(index));
	}

	@Override
	@Benchmark
	public void containsElement() {
		int index = generator.generateIndex(size);
		blackhole.consume(fullList.contains(values[index]));
	}

	@Override
	@Benchmark
	public void populate() {
		List<T> newList = this.getNewList();
		for (int i = 0; i < size; i++) {
			newList.add(values[i]);
		}
		blackhole.consume(newList);
	}

	@Override
	@Benchmark
	public void copy() {
		List<T> newList = this.copyList(fullList);
		blackhole.consume(newList);
	}

	@Override
	@Benchmark
	public void addElement() {
		int index = generator.generateIndex(size);
		blackhole.consume(fullList.add(values[index]));
		blackhole.consume(fullList.remove(size));
	}

	@Override
	@Benchmark
	public void removeElement() {
		int index = generator.generateIndex(size);
		blackhole.consume(fullList.remove(values[index]));
		blackhole.consume(fullList.add(values[index]));

	}
	
	@Override
	protected Object getFullCollection() {
		return fullList;
	}

}
