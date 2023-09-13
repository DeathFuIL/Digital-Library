package ru.rasim.functionalinterfaces;

import org.hibernate.Session;

@FunctionalInterface
public interface TransactionScopeWithResult<T>{
    T doInTransaction(Session session);
}