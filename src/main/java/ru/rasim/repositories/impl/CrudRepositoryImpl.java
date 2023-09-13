package ru.rasim.repositories.impl;

import org.hibernate.Session;
import ru.rasim.functionalinterfaces.TransactionScope;
import ru.rasim.functionalinterfaces.TransactionScopeWithResult;

public abstract class CrudRepositoryImpl {
    protected void inTransaction(Session session, TransactionScope transactionScope) {
        session.beginTransaction();

        transactionScope.doInTransaction(session);

        session.getTransaction().commit();
        session.close();
    }

    protected <T> T inTransactionWithResult(Session session, TransactionScopeWithResult<T> transactionScope) {
        session.beginTransaction();

        T result = transactionScope.doInTransaction(session);

        session.getTransaction().commit();
        session.close();

        return result;
    }
}
