package mvc;

import
        java.util.Scanner;

import static java.lang.System.*;

public class Main {
public static Controller controller;

    public static void main(String[] args) {
	// write your code here
        out.println("Hello");
        controller = new Controller(new Model(), new View());
        controller.showCountries();
      task1();
     //  task2();
      // task3();
    }

    public static void task1 () {
        out.println("Please, enter code of country, which insert");
        Scanner scanner = new Scanner(System.in);
        String operator;
        operator = scanner.nextLine();
        System.out.println(operator);
        String code = operator;

        out.println("Please, enter name of new country, which insert");
        Scanner scanner1 = new Scanner(System.in);
        String operator1;
        operator1 = scanner.nextLine();
        System.out.println(operator1);
        String name = operator1;
        //String name = "German";
        controller.insertCountry (code,name);//

        //start update
        out.println("Please, enter code of country, which update");
        scanner = new Scanner(System.in);
        operator = scanner.nextLine();
        System.out.println(operator);

        code = operator;
        out.println("Please, enter name of new country, which update");
        scanner = new Scanner(System.in);
        operator = scanner.nextLine();
        System.out.println(operator);
        name = operator;
        controller.updateCountry (code,name);

        //start delete
        out.println("Please, enter code of country, which delete");
        scanner = new Scanner(System.in);
        operator = scanner.nextLine();
        System.out.println(operator);
        code=operator;


        controller.deleteCountry (code);
        //start select result our program
        controller.showCountries();
            }

    public static void task2 ()
    //events table
    {
       int n = 2;
        System.out.println("Please, enter quantity of outs");
        Scanner scanner = new Scanner(System.in);
        int operator = Integer.parseInt(scanner.nextLine());
        System.out.println(operator);
        n = operator;
        out.println(n);
        controller.eventsRandom (n);
    }
    public static void task3 (){
        selectQuery1();

    }
            public static void selectQuery1 ()
        {
            String country_code = "ua";
            String sity_name = "%yi%";
            String starts1 = "01.04.2012";
            String starts2 = "01.04.2021";
            controller.selectQuery1(country_code, sity_name, starts1, starts2);
        }
    }


