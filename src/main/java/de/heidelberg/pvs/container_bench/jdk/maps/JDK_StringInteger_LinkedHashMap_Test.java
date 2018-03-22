package de.heidelberg.pvs.container_bench.jdk.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import de.heidelberg.pvs.container_bench.abstracts.jdk.AbstractJDKMapTest;

public class JDK_StringInteger_LinkedHashMap_Test extends AbstractJDKMapTest<String, Integer>{

	@Override
	protected Map<String, Integer> getNewMap() {
		return new LinkedHashMap<String, Integer>();
	}


	@Override
	protected Map<String, Integer> copyMap(Map<String, Integer> fullMap2) {
		return new LinkedHashMap<>(fullMap2);
	}


}