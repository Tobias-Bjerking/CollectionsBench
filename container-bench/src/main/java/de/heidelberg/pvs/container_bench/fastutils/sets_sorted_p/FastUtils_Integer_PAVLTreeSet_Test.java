package de.heidelberg.pvs.container_bench.fastutils.sets_sorted_p;

import java.util.Set;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKSetTest;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;

public class FastUtils_Integer_PAVLTreeSet_Test extends AbstractJDKSetTest<Integer> {

	@Override
	protected Set<Integer> getNewSet(int size) {
		return new IntAVLTreeSet();
	}

	@Override
	protected Set<Integer> copySet(Set<Integer> fullSet2) {
		return new IntAVLTreeSet(fullSet2);
	}

}
