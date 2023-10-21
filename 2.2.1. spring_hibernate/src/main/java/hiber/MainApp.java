package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru", new Car(125,"BMW")));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", new Car(254,"MB")));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru", new Car(666,"Lada")));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru", new Car(111,"VW")));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car = "+user.getUsersCar());
         System.out.println();
      }

      int ser = 254;
      String mod = "MB";
      User foundUs = userService.getUserByCar(mod, ser);

      System.out.println();
      System.out.println("For car with Series " + ser + " and model " + mod + " has been found User:");
      System.out.println(foundUs);

      context.close();
   }
}
