package com.github.itora.request;

import com.github.itora.crypto.Signed;
import com.github.itora.pow.Powed;
import com.github.itora.util.ByteArray;

public interface SignedPowRequestSerializer {
	ByteArray serialize(Signed<Powed<Request>> request);
	Signed<Powed<Request>> deserialize(ByteArray buffer);
}
