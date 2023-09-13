package ru.rasim.functionalinterfaces;

import org.hibernate.Session;

public interface TransactionScope {
    void doInTransaction(Session session);

}