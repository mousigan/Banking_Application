package com.mousigan.service;

import com.mousigan.entity.User;
import com.mousigan.repo.UserRepo;

import java.util.List;
import java.util.Map;

import static com.mousigan.repo.UserRepo.users;


public class UserService {
    private UserRepo userRepo=new UserRepo();

    public void printAllUsers(){
        userRepo.printAllUsers();
    }

    public User login(String username, String password){
        return userRepo.login(username,password);
    }

    public boolean addCustomer(String username,String password,String contactNumber){
         return userRepo.addNewCustomer(username,password,contactNumber);
    }

    public Double checkBankBalance(String username){
        return userRepo.checkBankBalance(username);
    }

    public User getUser(String userId){
        return userRepo.getUser(userId);
    }

    public boolean transferAmount(String userId, String payUserId,double amount){
       return userRepo.transferAmount(userId,payUserId,amount);
    }

    public void printTransactions(String userId){
        userRepo.printTransactions(userId);
    }

    public void raiseCheckBookRequest(String userId){
        userRepo.raiseCheckBookRequest(userId);
    }

    public Map<String, Boolean> getAllCheckBookRequest(){
        return userRepo.getAllCheckBookRequest();
    }

    public List<String> getUserIdsForRequest(){
        return userRepo.getUserIdsForRequest();
    }

    public void approveCheckBookRequest(String userId){
        userRepo.approveCheckBookRequest(userId);
    }
}
