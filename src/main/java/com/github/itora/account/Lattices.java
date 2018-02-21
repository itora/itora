package com.github.itora.account;

import com.github.andrewoma.dexx.collection.internal.hashmap.ListMap;

public final class Lattices {
	private Lattices() {
	}
	
	public static final Lattice EMPTY = new Lattice(ListMap.empty());
}
