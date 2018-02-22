package com.github.itora.request;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.crypto.PublicKey;
import com.github.itora.crypto.Signature;
import com.github.itora.crypto.Signed;
import com.github.itora.pow.Pow;
import com.github.itora.pow.Powed;
import com.github.itora.serialization.Serializations;
import com.github.itora.tx.AccountTxId;
import com.github.itora.tx.TxId;
import com.github.itora.util.ByteArray;
import com.github.itora.util.ConsumableByteArray;

public final class RegularSignedPowRequestSerializer implements SignedPowRequestSerializer {
	public RegularSignedPowRequestSerializer() {
	}
	
	@Override
	public ByteArray serialize(Signed<Powed<Request>> signedRequest) {
		return Request.visit(signedRequest.element().element(), new Request.Visitor<ByteArray>() {
    		@Override
    		public ByteArray visitOpenRequest(OpenRequest request) {
    			return Serializations.build(
    				RequestKind.OPEN,
					Serializations.to(request.account().key().value()),
					Serializations.to(request.timestamp()),
					Serializations.to(signedRequest.element().pow().value()),
					Serializations.to(signedRequest.signature().value())
    			);
    		}
    		@Override
    		public ByteArray visitReceiveRequest(ReceiveRequest request) {
    			return Serializations.build(
					RequestKind.RECEIVE,
					Serializations.to(request.previous().account().key().value()),
					Serializations.to(request.previous().txId().id()),
					Serializations.to(request.source().account().key().value()),
					Serializations.to(request.source().txId().id()),
					Serializations.to(request.timestamp()),
					Serializations.to(signedRequest.element().pow().value()),
					Serializations.to(signedRequest.signature().value())
    			);
    		}
    		@Override
    		public ByteArray visitSendRequest(SendRequest request) {
    			return Serializations.build(
					RequestKind.SEND,
					Serializations.to(request.previous().account().key().value()),
					Serializations.to(request.previous().txId().id()),
					Serializations.to(request.destination().key().value()),
					Serializations.to(request.amount().value()),
					Serializations.to(request.timestamp()),
					Serializations.to(signedRequest.element().pow().value()),
					Serializations.to(signedRequest.signature().value())
    			);
    		}
    	});
	}
	
	@Override
	public Signed<Powed<Request>> deserialize(ByteArray b) {
		ConsumableByteArray buffer = new ConsumableByteArray(b);
		switch (RequestKind.requestKindFrom(buffer)) {
		case OPEN:
			return Signed.Factory.signed(
				Powed.Factory.powed(
					Request.Factory.openRequest(
						Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
						Serializations.instantFrom(buffer)
					),
					Pow.Factory.pow(Serializations.byteArrayFrom(buffer))
				),
				Signature.Factory.signature(Serializations.byteArrayFrom(buffer))
			);
		case RECEIVE:
			return Signed.Factory.signed(
				Powed.Factory.powed(
					Request.Factory.receiveRequest(
						AccountTxId.Factory.accountTxId(
							Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
							TxId.Factory.txId(Serializations.byteArrayFrom(buffer))
						),
						AccountTxId.Factory.accountTxId(
							Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
							TxId.Factory.txId(Serializations.byteArrayFrom(buffer))
						),
						Serializations.instantFrom(buffer)
					),
					Pow.Factory.pow(Serializations.byteArrayFrom(buffer))
				),
				Signature.Factory.signature(Serializations.byteArrayFrom(buffer))
			);
		case SEND:
			return Signed.Factory.signed(
				Powed.Factory.powed(
					Request.Factory.sendRequest(
						AccountTxId.Factory.accountTxId(
							Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
							TxId.Factory.txId(Serializations.byteArrayFrom(buffer))
						),
						Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
						Amount.Factory.amount(Serializations.longFrom(buffer)),
						Serializations.instantFrom(buffer)
					),
					Pow.Factory.pow(Serializations.byteArrayFrom(buffer))
				),
				Signature.Factory.signature(Serializations.byteArrayFrom(buffer))
			);
		default:
			// Impossible case
			return null;
		}
	}
}
