package com.github.itora.util;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.io.BaseEncoding;

public final class ByteArray {

	private static final char SEPARATOR = '\n';
	
    public final byte[][] bytes;
    private int cachedHashCode = Undefineds.HASH_CODE;

    public ByteArray(byte[] bytes) {
        this.bytes = new byte[][] { bytes };
    }

	public ByteArray(byte[][] bytes) {
        this.bytes = bytes;
    }

    public ByteArray(ByteArray left, ByteArray right) {
        bytes = new byte[left.bytes.length + right.bytes.length][];
        System.arraycopy(left.bytes, 0, bytes, 0, left.bytes.length);
        System.arraycopy(right.bytes, 0, bytes, left.bytes.length, right.bytes.length);
    }

    public ByteArray duplicate() {
    	return new ByteArray(bytes);
    }
    
    public byte[] flattened() {
    	if (bytes.length == 0) {
    		return new byte[] {};
    	}
    	if (bytes.length == 1) {
    		return bytes[0];
    	}
    	int s = 0;
    	for (byte[] b : bytes) {
    		s += b.length;
    	}
        byte[] bb = new byte[s];
        int i = 0;
    	for (byte[] b : bytes) {
            System.arraycopy(b, 0, bb, i, b.length);
    		i += b.length;
    	}
    	return bb;
    }
    
    @Override
    public String toString() {
		StringBuilder b = new StringBuilder();
    	for (int i = 0; i < bytes.length; i++) {
	    	if (i > 0) {
	    		b.append(SEPARATOR);
	    	}
	    	b.append(BaseEncoding.base64().encode(bytes[i]));
		}
    	return b.toString();
    }
    
    @Override
    public final int hashCode() {
    	if (cachedHashCode == Undefineds.HASH_CODE) {
	    	int[] hash = new int[bytes.length];
	    	for (int i = 0; i < bytes.length; i++) {
				hash[i] = Arrays.hashCode(bytes[i]);
			}
	    	cachedHashCode = Arrays.hashCode(hash);
    	}
    	return cachedHashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        ByteArray that = (ByteArray) o;
        if (bytes.length != that.bytes.length) {
            return false;
        }
    	for (int i = 0; i < bytes.length; i++) {
			if (!Arrays.equals(bytes[i], that.bytes[i])) {
				return false;
			}
		}
    	return true;
    }
    
    public static ByteArray from(String base64) {
    	List<String> l = Splitter.on(SEPARATOR).splitToList(base64);
        byte[][] bytes = new byte[l.size()][];
        int i = 0;
        for (String s : l) {
        	bytes[i] = BaseEncoding.base64().decode(s);
        	i++;
        }
    	return new ByteArray(bytes);
    }
}
