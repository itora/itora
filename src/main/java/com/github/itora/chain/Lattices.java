package com.github.itora.chain;

import com.github.andrewoma.dexx.collection.internal.hashmap.ListMap;

public final class Lattices {
	private Lattices() {
	}
	
	public static final Lattice EMPTY = new Lattice(ListMap.empty());
}
