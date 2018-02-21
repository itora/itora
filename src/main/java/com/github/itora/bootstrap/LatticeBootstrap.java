package com.github.itora.bootstrap;

import com.github.itora.account.Lattice;
import com.github.itora.account.Lattices;

public interface LatticeBootstrap {
    
    Lattice bootstrap();

    
    public static final LatticeBootstrap EMPTY = new EmptyLatticeBootstrap();
    public static final class EmptyLatticeBootstrap implements LatticeBootstrap {
        @Override
        public Lattice bootstrap() {
            return Lattices.EMPTY;
        }
    }
    
}
