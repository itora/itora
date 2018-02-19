package com.github.itora.account;

import java.util.Iterator;

import com.github.itora.tx.Tx;

public final class PersonalChains {
	private PersonalChains() {
	}
	
	public static Iterable<Tx> iterate(PersonalChain chain) {
		return new Iterable<Tx>() {
			@Override
			public Iterator<Tx> iterator() {
				return new Iterator<Tx>() {
					private PersonalChain next = chain;
					@Override
					public boolean hasNext() {
						return (next != null);
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
