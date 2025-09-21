package com.mousigan.repo;

import com.mousigan.entity.Transaction;
import com.mousigan.entity.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class UserRepo {
    public static final Set<User> users=new HashSet<>();
    private static List<Transaction> transactions=new ArrayList<>();
    Map<String, Boolean> chequeBookRequest=new HashMap<>();

    static{
        User user1=new User("admin","admin","1234567891","admin",0.0);
        User user2=new User("user2","user2","1234567819","user",10000.0);
        User user3=new User("user3","user3","1234567918","user",20000.0);
        User user4=new User("user4","user4","1234569718","user",30000.0);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
    }
    public void printAllUsers(){
        System.out.println(users);
    }

    public User login(String username,String password){
         List<User> finalList=users.stream()
                 .filter(user->user.getUsername().equals(username) && user.getPassword().equals(password))
                 .collect(Collectors.toList());
         if(!finalList.isEmpty()){
             return finalList.get(0);
         }else {
             return null;
         }
    }

    public boolean addNewCustomer(String username,String password,String contactNumber){
        User user=new User(username, password,contactNumber,"user",1000.0);
        return users.add(user);
    }

    public Double checkBankBalance(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .map(User::getAccountBalance)
                .findFirst()
                .orElse(0.0);
    }

    public User getUser(String userId){
        List<User> result=users.stream().filter(user->user.getUsername()
                .equals(userId)).collect(Collectors.toList());
        if(!result.isEmpty()){
            return result.get(0);
        }else{
            return null;
        }
    }

    public boolean transferAmount(String userId, String payUserId,double amount){
        boolean isDebit=debit( userId, payUserId, amount);
        boolean isCredit=credit( userId, payUserId, amount);
        return isDebit && isCredit;
    }

    private boolean debit(String userId, String payUserId, double amount){
        User user=getUser(userId);
        double accountBalance= user.getAccountBalance();
        users.remove(user);
        double finalBalance=accountBalance-amount;
        user.setAccountBalance(finalBalance);
        Transaction transaction=new Transaction(
                LocalDate.now(),
                payUserId,
                amount,
                "Debit",
                accountBalance,
                finalBalance,
                userId
        );
//        System.out.println(transaction);
        transactions.add(transaction);
        return users.add(user);

    }

    private boolean credit(String userId, String payUserId,double amount){
        User user=getUser(payUserId);
        double accountBalance= user.getAccountBalance();
        users.remove(user);
        double finalBalance=accountBalance+amount;
        user.setAccountBalance(finalBalance);
        Transaction transaction=new Transaction(
                LocalDate.now(),
                userId,
                amount,
                "Credit",
                accountBalance,
                finalBalance,
                payUserId
        );
//        System.out.println(transaction);
        transactions.add(transaction);
        return users.add(user);
    }

    public void printTransactions(String userId){
        List<Transaction> result=transactions.stream().filter(transaction->transaction.getTransactionPerformedBy().equals(userId))
                .collect(Collectors.toList());
        if(!result.isEmpty()){
            System.out.println("Date\tUserId\t\tAmount\tType\tIntialBalance\tFinalBalance");
            System.out.println("-----------------------------------------------------------------------");
            for(Transaction transaction:result){
                System.out.println(
                        transaction.getTransactionDate()+"\t"
                        + transaction.getTransactionPerformedBy()+"\t"
                        + transaction.getTransactionType()+"\t\t"
                        + transaction.getAmount()+"\t\t"
                        + transaction.getInitialBalance()+"\t\t"
                        + transaction.getFinalbalance()
                );
            }
            System.out.println("-----------------------------------------------------------------------");
        }else{
            System.out.println("No Transactions was found");
        }
    }

    public void raiseCheckBookRequest(String userId){
        chequeBookRequest.put(userId,false);
    }

    public Map<String, Boolean> getAllCheckBookRequest(){
        return chequeBookRequest;
    }

    public List<String> getUserIdsForRequest(){
        List<String> userIds=new ArrayList<>();
        for(Map.Entry<String,Boolean> entry:chequeBookRequest.entrySet()){
            if(!entry.getValue()){
                userIds.add(entry.getKey());
            }
        }
        return userIds;
    }

    public void approveCheckBookRequest(String userId){
        if(chequeBookRequest.containsKey(userId)){
            chequeBookRequest.put(userId,true);
        }else{
            System.out.println("No request found");
        }
    }
}
