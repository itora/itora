package com.github.itora.util;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.io.BaseEncoding;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public final class ByteArray {

	private static final char REPRESENTATION_SEPARATOR = '\n';
	
    public final byte[][] bytes;
    private int cachedHashCode = Undefineds.HASH_CODE;

    public ByteArray(byte[] bytes) {
        this.bytes = new byte[][] { bytes };
    }

	public ByteArray(byte[][] bytes) {
        this.bytes = bytes;
    }

	public static ByteArray cat(ByteArray... byteArrays) {
		int s = 0;
		for (ByteArray b : byteArrays) {
			s += b.bytes.length;
		}
		byte[][] bytes = new byte[s][];
		int k = 0;
		for (int i = 0; i < byteArrays.length; i++) {
			int l = byteArrays[i].bytes.length;
			System.arraycopy(byteArrays[i].bytes, 0, bytes, k, l);
			k += l;
		}
		return new ByteArray(bytes);
	}
	
	public long totalLength() {
		long l = 0L;
    	for (byte[] b : bytes) {
    		l += b.length;
    	}
    	return l;
	}

	// Method required to pass a raw byte array to crypto functions
    public byte[] flattened() {
    	if (bytes.length == 0) {
    		return new byte[] {};
    	}
    	if (bytes.length == 1) {
    		return bytes[0];
    	}
    	long s = 0L;
    	for (byte[] b : bytes) {
    		s += b.length;
    	}
        byte[] bb = new byte[(int) s];
        int i = 0;
    	for (byte[] b : bytes) {
            System.arraycopy(b, 0, bb, i, b.length);
    		i += b.length;
    	}
    	return bb;
    }
    
    @Override
    public String toString() {
    	JsonArray a = new JsonArray();
    	for (int i = 0; i < bytes.length; i++) {
        	JsonObject o = new JsonObject();
    		o.add("size", new JsonPrimitive(bytes[i].length));
    		o.add("data", new JsonPrimitive(BaseEncoding.base64().encode(bytes[i])));
        	a.add(o);
		}
    	return a.toString();
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
    
    public String representation() {
		StringBuilder b = new StringBuilder();
    	for (int i = 0; i < bytes.length; i++) {
	    	if (i > 0) {
	    		b.append(REPRESENTATION_SEPARATOR);
	    	}
	    	b.append(BaseEncoding.base64().encode(bytes[i]));
		}
    	return b.toString();
    }
    
    public static ByteArray fromRepresentation(String representationAsBase64) {
    	List<String> l = Splitter.on(REPRESENTATION_SEPARATOR).splitToList(representationAsBase64);
        byte[][] bytes = new byte[l.size()][];
        int i = 0;
        for (String s : l) {
        	bytes[i] = BaseEncoding.base64().decode(s);
        	i++;
        }
    	return new ByteArray(bytes);
    }
}
