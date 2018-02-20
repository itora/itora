package com.github.itora.chain;

import java.util.Iterator;

import com.github.itora.tx.Tx;

public final class Chains {
	private Chains() {
	}
	
	public static final Chain ROOT = Chain.Factory.chainRoot();
	
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
					    return Chain.visit(next, new Chain.Visitor<Tx>() {

                            @Override
                            public Tx visitChainRoot(ChainRoot chainRoot) {
                             throw new IllegalStateException("Cannot get Tx on the ChainRoot");
                            }

                            @Override
                            public Tx visitChainLink(ChainLink link) {
                                Tx tx = link.tx;
                                next = link.previous;
                                return tx;
                            }
                        });
					
					}
				};
			}
		};
	}
}
