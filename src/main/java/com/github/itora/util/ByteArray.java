package com.github.itora.util;

import com.google.common.io.BaseEncoding;

public final class ByteArray {
    
    public final byte[] bytes;
    
    public ByteArray(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
    	return BaseEncoding.base64().encode(bytes);
    }
    
    @Override
    public final int hashCode() {
        return java.util.Arrays.hashCode(bytes);
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
        return java.util.Arrays.equals(this.bytes, that.bytes);
    }
    
    public static ByteArray from(String base64) {
    	return new ByteArray(BaseEncoding.base64().decode(base64));
    }
}
