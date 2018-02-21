package com.github.itora.request;

import java.nio.ByteBuffer;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.crypto.PublicKey;
import com.github.itora.crypto.Signature;
import com.github.itora.serialization.Serializations;
import com.github.itora.tx.AccountTxId;
import com.github.itora.tx.TxId;

public final class RegularSignedPowRequestSerializer implements SignedPowRequestSerializer {
	public RegularSignedPowRequestSerializer() {
	}
	
	@Override
	public ByteBuffer serialize(SignedPowRequest signedRequest) {
		return Request.visit(signedRequest.powRequest().request(), new Request.Visitor<ByteBuffer>() {
    		@Override
    		public ByteBuffer visitOpenRequest(OpenRequest request) {
    			return Serializations.build(
    				RequestKind.OPEN,
					Serializations.to(request.account().key().value()),
					Serializations.to(request.timestamp()),
					Serializations.to(signedRequest.powRequest().pow()),
					Serializations.to(signedRequest.signature().value())
    			);
    		}
    		@Override
    		public ByteBuffer visitReceiveRequest(ReceiveRequest request) {
    			return Serializations.build(
					RequestKind.RECEIVE,
					Serializations.to(request.previous().id()),
					Serializations.to(request.source().account().key().value()),
					Serializations.to(request.source().txId().id()),
					Serializations.to(request.timestamp()),
					Serializations.to(signedRequest.powRequest().pow()),
					Serializations.to(signedRequest.signature().value())
    			);
    		}
    		@Override
    		public ByteBuffer visitSendRequest(SendRequest request) {
    			return Serializations.build(
					RequestKind.SEND,
					Serializations.to(request.previous().id()),
					Serializations.to(request.from().key().value()),
					Serializations.to(request.to().key().value()),
					Serializations.to(request.amount().value()),
					Serializations.to(request.timestamp()),
					Serializations.to(signedRequest.powRequest().pow()),
					Serializations.to(signedRequest.signature().value())
    			);
    		}
    	});
	}
	
	@Override
	public SignedPowRequest deserialize(ByteBuffer buffer) {
		switch (RequestKind.requestKindFrom(buffer)) {
		case OPEN:
			return SignedPowRequest.Factory.signedPowRequest(
				PowRequest.Factory.powRequest(
					Request.Factory.openRequest(
						Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
						Serializations.instantFrom(buffer)
					),
					Serializations.byteArrayFrom(buffer)
				),
				Signature.Factory.signature(Serializations.byteArrayFrom(buffer))
			);
		case RECEIVE:
			return SignedPowRequest.Factory.signedPowRequest(
				PowRequest.Factory.powRequest(
					Request.Factory.receiveRequest(
						TxId.Factory.txId(Serializations.byteArrayFrom(buffer)),
						AccountTxId.Factory.accountTxId(
							Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
							TxId.Factory.txId(Serializations.byteArrayFrom(buffer))
						),
						Serializations.instantFrom(buffer)
					),
					Serializations.byteArrayFrom(buffer)
				),
				Signature.Factory.signature(Serializations.byteArrayFrom(buffer))
			);
		case SEND:
			return SignedPowRequest.Factory.signedPowRequest(
				PowRequest.Factory.powRequest(
					Request.Factory.sendRequest(
						TxId.Factory.txId(Serializations.byteArrayFrom(buffer)),
						Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
						Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
						Amount.Factory.amount(Serializations.longFrom(buffer)),
						Serializations.instantFrom(buffer)
					),
					Serializations.byteArrayFrom(buffer)
				),
				Signature.Factory.signature(Serializations.byteArrayFrom(buffer))
			);
		default:
			// Impossible case
			return null;
		}
	}
}
