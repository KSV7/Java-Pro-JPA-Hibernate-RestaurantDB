package com.gmail.kutepov89.sergey;

import javax.persistence.*;
import javax.sql.RowSetListener;
import java.util.*;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("0 - Add my menu:");
                    System.out.println("1 - Add a dish to the menu:");
                    System.out.println("2 - View all menu items:");
                    System.out.println("3 - View menu items where cost from to:");
                    System.out.println("4 - View menu items with discount:");
                    System.out.println("5 - Get random menu to 1 kg:");
                    System.out.println("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "0":
                            addMyMenu();
                            break;
                        case "1":
                            addDish(sc);
                            break;
                        case "2":
                            viewDishes();
                            break;
                        case "3":
                            costFromTo(sc);
                            break;
                        case "4":
                            getMenuDishesDiscount();
                            break;
                        case "5":
                            getSumWeightOneKG();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void addDish(Scanner sc) {
        System.out.println("Enter dish name: ");
        String dishName = sc.nextLine();
        System.out.print("Enter cost: ");
        String c = sc.nextLine();
        double cost = Double.parseDouble(c);
        System.out.print("Enter weight: ");
        String w = sc.nextLine();
        double weight = Double.parseDouble(w);
        System.out.print("Enter discount: ");
        String d = sc.nextLine();
        double discount = Double.parseDouble(d);

        em.getTransaction().begin();
        try {
            MenuRestaurant mR = new MenuRestaurant(dishName, cost, weight, discount);
            em.persist(mR);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void viewDishes() {
        Query query = em.createNamedQuery("Menu.findAll", MenuRestaurant.class);
        List<MenuRestaurant> dishList = query.getResultList();

        System.out.println("Name \t\t\t Cost \t\t\t Weight \t\t\t Discount");
        for (MenuRestaurant dish : dishList) {
            System.out.println(dish.getDishName()
                    + "\t\t\t\t" + dish.getCost()
                    + "\t\t\t\t" + dish.getWeight()
                    + "\t\t\t\t" + dish.getDiscount());
        }
    }

    private static void costFromTo(Scanner sc) {
        System.out.print("Enter cost from: ");
        String c1 = sc.nextLine();
        double cost1 = Double.parseDouble(c1);
        System.out.print("Enter cost to: ");
        String c2 = sc.nextLine();
        double cost2 = Double.parseDouble(c2);

        Query query = em.createNamedQuery("Menu.findBetween", MenuRestaurant.class);
        query.setParameter("val1", cost1);
        query.setParameter("val2", cost2);

        List<MenuRestaurant> menuList = query.getResultList();

        for (Object dish : menuList) {
            System.out.println(dish);
        }
    }

    private static void getMenuDishesDiscount() {
        Query query = em.createNamedQuery("Menu.withDiscount", MenuRestaurant.class);
        List<MenuRestaurant> menuList = query.getResultList();
        for (Object dish : menuList) {
            System.out.println(dish);
        }
    }

    private static void getSumWeightOneKG() {
        Query query = em.createNamedQuery("Menu.findAll", MenuRestaurant.class);
        List<MenuRestaurant> dishList = query.getResultList();
        List<MenuRestaurant> dishList2 = new ArrayList<>();
        double weight = 0;

        Random rand = new Random();

        while (weight < 1000) {
            MenuRestaurant randomDish = dishList.get(rand.nextInt(dishList.size()));
            weight += randomDish.getWeight();
            if (weight > 1000) {
                break;
            }
            dishList2.add(randomDish);
        }

        for (MenuRestaurant dish : dishList2) {
            System.out.println(dish);
        }
    }

    private static void addMyMenu() {
        List<MenuRestaurant> myDishList = new ArrayList<>();

        myDishList.add(new MenuRestaurant("fish", 30, 150, 0));
        myDishList.add(new MenuRestaurant("soup", 10, 250, 1));
        myDishList.add(new MenuRestaurant("meat", 20, 100, 1));
        myDishList.add(new MenuRestaurant("pizza", 10, 150, 2));
        myDishList.add(new MenuRestaurant("steak", 30, 100, 0));
        myDishList.add(new MenuRestaurant("hot dog", 10, 100, 2));
        myDishList.add(new MenuRestaurant("ice cream", 30, 100, 0));
        myDishList.add(new MenuRestaurant("cheese", 20, 100, 0));
        myDishList.add(new MenuRestaurant("salad", 20, 250, 1));
        myDishList.add(new MenuRestaurant("milk", 15, 300, 1));

        myDishList.add(new MenuRestaurant("ham", 30, 100, 0));
        myDishList.add(new MenuRestaurant("bacon", 40, 100, 0));
        myDishList.add(new MenuRestaurant("sausage", 20, 200, 1));
        myDishList.add(new MenuRestaurant("chicken", 40, 150, 0));
        myDishList.add(new MenuRestaurant("yogurt", 20, 300, 1));
        myDishList.add(new MenuRestaurant("pancake", 20, 250, 0));
        myDishList.add(new MenuRestaurant("porridge", 15, 350, 2));
        myDishList.add(new MenuRestaurant("bread", 5, 150, 1));
        myDishList.add(new MenuRestaurant("seafood", 50, 200, 0));
        myDishList.add(new MenuRestaurant("hamburger", 10, 200, 1));

        em.getTransaction().begin();
        try {
            for (MenuRestaurant dish : myDishList) {
                MenuRestaurant mR = new MenuRestaurant(dish.getDishName(), dish.getCost(), dish.getWeight(), dish.getDiscount());
                em.persist(mR);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

}
