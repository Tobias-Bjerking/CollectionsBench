package de.heidelberg.pvs.container_bench.hppc.sets;

import com.carrotsearch.hppc.ObjectHashSet;

import de.heidelberg.pvs.container_bench.abstracts.hppc.AbstractHPPCSetTest;

public class HPPC_Long_HashSet_Test extends AbstractHPPCSetTest<Long> {

	@Override
	protected ObjectHashSet<Long> getNewSet(int size) {
		return new ObjectHashSet<>();
	}

	@Override
	protected ObjectHashSet<Long> copySet(ObjectHashSet<Long> original) {
		return new ObjectHashSet<>(original);
	}

}
