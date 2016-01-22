package de.heidelberg.pvs.container_bench.koloboke.maps;

import java.util.Map;

import com.gs.collections.impl.factory.primitive.IntIntMaps;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKMapTest;
import de.heidelberg.pvs.container_bench.random.IntegerRandomGenerator;
import de.heidelberg.pvs.container_bench.random.RandomGenerator;
import net.openhft.koloboke.collect.map.IntIntMapFactory;
import net.openhft.koloboke.collect.map.hash.HashIntIntMaps;

public class Koloboke_IntegerInteger_HashMap_Test extends AbstractJDKMapTest<Integer, Integer> {

	@Override
	protected Map<Integer, Integer> getNewMap(int size, int range) {
		return HashIntIntMaps.newMutableMap();
	}

	@Override
	protected Map<Integer, Integer> copyMap(Map<Integer, Integer> fullMap2) {
		return HashIntIntMaps.newMutableMap(fullMap2);
	}

	@Override
	protected RandomGenerator<Integer> instantiateRandomKeyGenerator() {
		return new IntegerRandomGenerator();
	}

	@Override
	protected RandomGenerator<Integer> instantiateRandomValueGenerator() {
		return new IntegerRandomGenerator();
	}

}
