package com.github.itora.request;

import java.nio.ByteBuffer;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.crypto.PublicKey;
import com.github.itora.serialization.Serializations;
import com.github.itora.tx.AccountTxId;
import com.github.itora.tx.TxId;

public final class RegularPowRequestSerializer implements PowRequestSerializer {
	public RegularPowRequestSerializer() {
	}
	
	@Override
	public ByteBuffer serialize(PowRequest powRequest) {
		return Request.visit(powRequest.request(), new Request.Visitor<ByteBuffer>() {
    		@Override
    		public ByteBuffer visitOpenRequest(OpenRequest request) {
    			return Serializations.build(
    				RequestKind.OPEN,
					Serializations.to(request.account().key().value()),
					Serializations.to(request.timestamp()),
					Serializations.to(powRequest.pow())
    			);
    		}
    		@Override
    		public ByteBuffer visitReceiveRequest(ReceiveRequest request) {
    			return Serializations.build(
					RequestKind.RECEIVE,
					Serializations.to(request.previous().account().key().value()),
					Serializations.to(request.previous().txId().id()),
					Serializations.to(request.source().account().key().value()),
					Serializations.to(request.source().txId().id()),
					Serializations.to(request.timestamp()),
					Serializations.to(powRequest.pow())
    			);
    		}
    		@Override
    		public ByteBuffer visitSendRequest(SendRequest request) {
    			return Serializations.build(
					RequestKind.SEND,
					Serializations.to(request.previous().account().key().value()),
					Serializations.to(request.previous().txId().id()),
					Serializations.to(request.destination().key().value()),
					Serializations.to(request.amount().value()),
					Serializations.to(request.timestamp()),
					Serializations.to(powRequest.pow())
    			);
    		}
    	});
	}
	
	@Override
	public PowRequest deserialize(ByteBuffer buffer) {
		switch (RequestKind.requestKindFrom(buffer)) {
		case OPEN:
			return PowRequest.Factory.powRequest(
				Request.Factory.openRequest(
					Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
					Serializations.instantFrom(buffer)
				),
				Serializations.byteArrayFrom(buffer)
			);
		case RECEIVE:
			return PowRequest.Factory.powRequest(
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
				Serializations.byteArrayFrom(buffer)
			);
		case SEND:
			return PowRequest.Factory.powRequest(
				Request.Factory.sendRequest(
					AccountTxId.Factory.accountTxId(
						Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
						TxId.Factory.txId(Serializations.byteArrayFrom(buffer))
					),
					Account.Factory.account(PublicKey.Factory.publicKey(Serializations.byteArrayFrom(buffer))),
					Amount.Factory.amount(Serializations.longFrom(buffer)),
					Serializations.instantFrom(buffer)
				),
				Serializations.byteArrayFrom(buffer)
			);
		default:
			// Impossible case
			return null;
		}
	}
}