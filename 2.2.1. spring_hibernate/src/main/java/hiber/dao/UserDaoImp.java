package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String carModel, int carSeries) {
      List<Car> listFoundCar = sessionFactory.getCurrentSession().createQuery("from Car where model = :carModel and series = :carSeries")
              .setParameter("carModel", carModel)
              .setParameter("carSeries", carSeries)
              .getResultList();

      if (listFoundCar.size() == 0) {
         return null;
      } else {
         Car foundCar = listFoundCar.get(0);
         List <User> foundUsers = sessionFactory.getCurrentSession().createQuery("from User where usersCar = :usersCar")
                 .setParameter("usersCar", foundCar).getResultList();
         return foundUsers.get(0);

      }
   }

}
