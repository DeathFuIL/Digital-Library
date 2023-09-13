package ru.rasim.functionalinterfaces;

import org.hibernate.Session;

public interface TransactionScopeWithResult<T>{
    T doInTransaction(Session session);
}