package com.github.itora.request;

import java.nio.ByteBuffer;

import com.github.itora.crypto.Signed;
import com.github.itora.pow.Powed;

public interface SignedPowRequestSerializer {
	ByteBuffer serialize(Signed<Powed<Request>> request);
	Signed<Powed<Request>> deserialize(ByteBuffer buffer);
}
