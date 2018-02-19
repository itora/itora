package com.github.itora.account;

public final class Account {

    public final long number;

    public Account(long number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (number ^ (number >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Account other = (Account) obj;
        if (number != other.number) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Account [number=" + number + "]";
    }

}
