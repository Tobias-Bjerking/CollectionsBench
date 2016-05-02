package de.heidelberg.pvs.container_bench.fastutils.sets_p;

import java.util.Set;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKSetTest;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;



public class FastUtils_Long_PHashSet_Test extends AbstractJDKSetTest<Long> {

	@Override
	protected Set<Long> getNewSet(int size) {
		return new LongOpenHashSet();
	}

	@Override
	protected Set<Long> copySet(Set<Long> fullSet2) {
		return new LongOpenHashSet(fullSet2);
	}

}
