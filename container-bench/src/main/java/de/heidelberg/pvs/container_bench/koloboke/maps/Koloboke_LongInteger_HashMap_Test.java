package de.heidelberg.pvs.container_bench.koloboke.maps;

import java.util.Map;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKMapTest;
import de.heidelberg.pvs.container_bench.random.IntegerRandomGenerator;
import de.heidelberg.pvs.container_bench.random.LongRandomGenerator;
import de.heidelberg.pvs.container_bench.random.RandomGenerator;
import net.openhft.koloboke.collect.map.hash.HashObjObjMaps;

public class Koloboke_LongInteger_HashMap_Test extends AbstractJDKMapTest<Long, Integer> {

	@Override
	protected Map<Long, Integer> getNewMap(int size, int range) {
		return HashObjObjMaps.newMutableMap();
	}

	@Override
	protected Map<Long, Integer> copyMap(Map<Long, Integer> fullMap2) {
		return HashObjObjMaps.newMutableMap(fullMap2);
	}

	@Override
	protected RandomGenerator<Long> instantiateRandomKeyGenerator() {
		return new LongRandomGenerator();
	}

	@Override
	protected RandomGenerator<Integer> instantiateRandomValueGenerator() {
		return new IntegerRandomGenerator();
	}

}
