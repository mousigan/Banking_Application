package com.mousigan.main;

import com.mousigan.entity.User;
import com.mousigan.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private  static final Scanner scanner=new Scanner(System.in);
    static Main main=new Main();
    static UserService userService=new UserService();

    public static void main(String[] args){


        while (true){
            System.out.println("Enter your username");
            String username=scanner.next();
            System.out.println("Enter your password");
            String password=scanner.next();

            User user=userService.login(username,password);
            if(user!=null && user.getRole().equals("admin")){
                System.out.println("You logged in Successfully as Admin!!");
                main.initAdmin();
            }else if(user!=null && user.getRole().equals("user")){
                System.out.println("You logged in Successfully!!");
                main.initCustomer(user);
            } else{
                System.out.println("Logged failed");
            }
        }

    }

    private void initAdmin(){
        boolean flag=true;
        String userId;
        while(flag){
            System.out.println("Enter your choice");
            System.out.println("1. Exit/Logout");
            System.out.println("2. Create Account");
            System.out.println("3. Display all Transactions");
            System.out.println("4. Check bank Balance");
            System.out.println("5. Aprroval for request");
            System.out.println("6. Show All waiting Approvals");

            int choice=scanner.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Logout  Successfull!!");
                    flag=false;
                    break;
                case 2:
                    main.addCustomer();
                    break;
                case 3:
                    System.out.println("Enter UserId for History!!");
                    userId=scanner.next();
                    printTransactions(userId);
                    break;
                case 4:
                    System.out.println("Enter UserId to get Balance!!");
                    userId=scanner.next();
                    double amountBalance=checkBankBalance(userId);
                    System.out.println("The balance for "+userId+" is "+amountBalance);
                    break;
                case 5:
                    List<String> userIds=main.getUserIdsForRequest();
                    System.out.println("Select the user");
                    System.out.println(userIds);
                    userId=scanner.next();
                    approveCheckBookRequest(userId);
                    System.out.println("Check book request Approved!!");
                    break;
                case 6:
                    System.out.println("Users waiting for approval");
                    List<String> allUsersWaitingForApproval=main.getUserIdsForRequest();
                    if(allUsersWaitingForApproval.size()!=0) {
                        System.out.println(allUsersWaitingForApproval);
                    }else{
                        System.out.println("There is no users waiting for approval");
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void approveCheckBookRequest(String userId){
        userService.approveCheckBookRequest(userId);
    }

    private List<String> getUserIdsForRequest(){
        return userService.getUserIdsForRequest();
    }

    private void addCustomer(){
        System.out.println("Enter username");
        String username=scanner.next();
        System.out.println("Enter password");
        String password=scanner.next();
        System.out.println("Enter contact number");
        String contactNumber=scanner.next();
        System.out.println("Enter email");
        boolean result=userService.addCustomer(username,password,contactNumber);
        if(result){
            System.out.println("Customer account created successfully");
        }else{
            System.out.println("Customer account creation failed");
        }
    }

    private void initCustomer(User user){
        boolean flag=true;
        while(flag){
            System.out.println("1. Exit/Logout");
            System.out.println("2. Check account Balance");
            System.out.println("3. Amount Transfer");
            System.out.println("4. See all Transactions");
            System.out.println("5. Raise checkbook request");
            int choice=scanner.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Logout  Successfull!!");
                    flag=false;
                    break;
                case 2:
                    Double balance= main.checkBankBalance(user.getUsername());
                    if(balance!=null){
                        System.out.println("Your balance is "+balance);
                    }else{
                        System.out.println("Your balance is null");
                    }
                    break;
                case 3:
                    main.amountTransfer(user);
                    break;
                case 4:
                    main.printTransactions(user.getUsername());
                    break;
                case 5:
                    String userId=user.getUsername();
                    Map<String, Boolean> map=main.getAllCheckBookRequest();
                    if(map.containsKey(userId) && map.get(userId)){
                        System.out.println("You hava already raised a request!!");
                    }else if(map.containsKey(userId) && !map.get(userId)){
                        System.out.println("You hava already raised a request pending for approval!!");
                    }else{
                        raiseCheckBookRequest(userId);
                        System.out.println("Request was Raised successfully!!");
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private Map<String, Boolean> getAllCheckBookRequest(){
        return userService.getAllCheckBookRequest();
    }

    private void raiseCheckBookRequest(String userId){
        userService.raiseCheckBookRequest(userId);
    }
    private void amountTransfer(User userDetails){
        System.out.println("Enter user to transfer amount");
        String payAccountId=scanner.next();
        User user=getUser(payAccountId);
        if(user!=null){
            System.out.println("Enter amount to transfer");
            double amount=scanner.nextDouble();
            Double balance=main.checkBankBalance(user.getUsername());
            if(amount <= balance){
                boolean result=userService.transferAmount(userDetails.getUsername(),payAccountId,amount);
                if(result){
                    System.out.println("Your amount transfer was successful");
                }else{
                    System.out.println("Your amount transfer was failed");
                }
            }else{
                System.out.println("Your balance is "+balance+"is not enough");
            }

        }else{
            System.out.println("Please enter valid user name!!");
        }
    }

    private void printTransactions(String userId){
        userService.printTransactions(userId);
    }

    private User getUser(String userId){
        return userService.getUser(userId);
    }

    private Double checkBankBalance(String username){
         return userService.checkBankBalance(username);
    }
}
