package com.github.itora.request;

import com.github.itora.account.Account;
import com.github.itora.tx.AccountTxId;
import com.github.itora.amount.Amount;
import java.time.Instant;
import com.github.itora.tx.TxId;

public abstract class Request {

    Request() {
    }

    public interface Factory {

        public static OpenRequest openRequest(Account account, long pow, Instant timestamp) {
            return new OpenRequest(account, pow, timestamp);
        }

        public static SendRequest sendRequest(TxId previous, Account from, Account to, Amount amount, long pow, Instant timestamp) {
            return new SendRequest(previous, from, to, amount, pow, timestamp);
        }

        public static ReceiveRequest receiveRequest(TxId previous, AccountTxId source, long pow, Instant timestamp) {
            return new ReceiveRequest(previous, source, pow, timestamp);
        }
    }

    public static <R> R visit(Request request, Request.Visitor<R> visitor) {
        return request.visit(visitor);
    }

    abstract <R> R visit(Request.Visitor<R> visitor);

    public abstract long pow();

    public abstract Request withPow(long pow);

    public abstract Instant timestamp();

    public abstract Request withTimestamp(Instant timestamp);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    public interface Visitor<R> {

        R visitOpenRequest(OpenRequest openRequest);

        R visitSendRequest(SendRequest sendRequest);

        R visitReceiveRequest(ReceiveRequest receiveRequest);
    }
}
