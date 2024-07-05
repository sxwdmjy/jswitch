package com.jswitch.server.cache;

import com.jswitch.server.transaction.ClientSipTransaction;
import com.jswitch.server.transaction.ServerSipTransaction;
import com.jswitch.server.transaction.Transaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author danmo
 * @date 2024-07-05 14:49
 **/
public class TransactionManageCache {

    public static Map<String, ServerSipTransaction> serverTransactionMap = new ConcurrentHashMap<>();
    public static Map<String, ClientSipTransaction> clientTransactionMap = new ConcurrentHashMap<>();

    public static void addServerTransaction(String transactionId, ServerSipTransaction serverSipTransaction) {
        serverTransactionMap.put(transactionId, serverSipTransaction);
    }

    public static void addClientTransaction(String transactionId, ClientSipTransaction clientSipTransaction) {
        clientTransactionMap.put(transactionId, clientSipTransaction);
    }

    public static ServerSipTransaction getServerTransaction(String transactionId) {
        return serverTransactionMap.get(transactionId);
    }

    public static ClientSipTransaction getClientTransaction(String transactionId) {
        return clientTransactionMap.get(transactionId);
    }

    public static void removeServerTransaction(String transactionId) {
        serverTransactionMap.remove(transactionId);
    }

    public static void removeClientTransaction(String transactionId) {
        clientTransactionMap.remove(transactionId);
    }

    public static Boolean isServerTransactionExist(String transactionId) {
        return serverTransactionMap.containsKey(transactionId);
    }

    public static Boolean isClientTransactionExist(String transactionId) {
        return clientTransactionMap.containsKey(transactionId);
    }

    public static Transaction getTransaction(String transactionId) {
        if (serverTransactionMap.containsKey(transactionId)) {
            return serverTransactionMap.get(transactionId);
        }
        if (clientTransactionMap.containsKey(transactionId)) {
            return clientTransactionMap.get(transactionId);
        }
        return null;
    }

    public static void removeTransaction(String transactionId) {
        serverTransactionMap.remove(transactionId);
        clientTransactionMap.remove(transactionId);
    }

    public static Boolean isTransactionExist(String transactionId) {
        return serverTransactionMap.containsKey(transactionId) || clientTransactionMap.containsKey(transactionId);
    }

    public static void clear() {
        serverTransactionMap.clear();
        clientTransactionMap.clear();
    }
}
