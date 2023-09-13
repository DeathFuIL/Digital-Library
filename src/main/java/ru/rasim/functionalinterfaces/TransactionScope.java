package ru.rasim.functionalinterfaces;

import org.hibernate.Session;

@FunctionalInterface
public interface TransactionScope {
    void doInTransaction(Session session);
}