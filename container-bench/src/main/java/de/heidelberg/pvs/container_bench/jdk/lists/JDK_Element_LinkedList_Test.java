package de.heidelberg.pvs.container_bench.jdk.lists;

import java.util.LinkedList;
import java.util.List;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKListTest;
import de.heidelberg.pvs.container_bench.element.Element;

public class JDK_Element_LinkedList_Test extends AbstractJDKListTest<Element>{

	@Override
	protected Element[] generateRandomArray(int size) {
		return randomGenerator.generateElements(size);
	}

	@Override
	protected Integer generateRandomIndex(int range) {
		return randomGenerator.generateIntegerInRange(range);
	}

	@Override
	protected List<Element> getNewList(int size) {
		return new LinkedList<Element>();
	}

	@Override
	protected List<Element> copyList(List<Element> fullList2) {
		return new LinkedList<Element>(fullList2);
	}

}
