package com.github.itora.chain;

import java.util.Iterator;

import com.github.itora.tx.Tx;

public final class Chains {
	private Chains() {
	}
	
	// TODO fixme
	public static final Chain ROOT = Chain.Factory.chain(null, null);
	
	public static Iterable<Tx> iterate(Chain chain) {
		return new Iterable<Tx>() {
			@Override
			public Iterator<Tx> iterator() {
				return new Iterator<Tx>() {
					private Chain next = chain;
					@Override
					public boolean hasNext() {
						return (!next.equals(Chains.ROOT));
					}
					@Override
					public Tx next() {
						Tx tx = next.tx;
						next = next.previous;
						return tx;
					}
				};
			}
		};
	}
}
