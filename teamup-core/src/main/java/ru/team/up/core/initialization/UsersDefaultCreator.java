package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Transactional
@Profile("cdb")
public class UsersDefaultCreator {

    private final AccountRepository accountRepository;

    @Autowired
    public UsersDefaultCreator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Bean("UsersDefaultCreator")
    public void usersDefaultCreator() {
        accountRepository.save(User.builder()
                .firstName("User")
                .lastName("DefaultUser")
                .username("user")
                .password(BCrypt.hashpw("user", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("user@gmail.com")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1922, 1, 20))
                .aboutUser("Default user")
                .city("Default city")
                .build());

        accountRepository.save(User.builder()
                .firstName("Иван")
                .lastName("Петров")
                .middleName("Иванович")
                .username("ivan")
                .password(BCrypt.hashpw("ivan", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("ivan@mail.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1992, 5, 20))
                .aboutUser("Любит играть в футбол.")
                .city("Иваново")
                .build());

        accountRepository.save(User.builder()
                .firstName("Ольга")
                .lastName("Смирнова")
                .middleName("Васильевна")
                .username("olga")
                .password(BCrypt.hashpw("olga", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("olga@yandex.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1998, 6, 15))
                .aboutUser("Увлекается фотографией")
                .city("Санкт-Петербург")
                .build());

        accountRepository.save(User.builder()
                .firstName("Фёдор")
                .lastName("Жуков")
                .middleName("Семёнович")
                .username("fedor")
                .password(BCrypt.hashpw("fedor", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("fedor@hotmail.com")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1977, 2, 10))
                .aboutUser("Увлекается охотой и рыбалкой.")
                .city("Самара")
                .build());

        accountRepository.save(User.builder()
                .firstName("Роман")
                .lastName("Соколов")
                .middleName("Иванович")
                .username("roman")
                .password(BCrypt.hashpw("roman", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("roman@gmail.com")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1996, 3, 29))
                .aboutUser("Любит писать программы на Java.")
                .city("Москва")
                .build());

        accountRepository.save(User.builder()
                .firstName("Мария")
                .lastName("Морозова")
                .middleName("Михайловна")
                .username("mariya")
                .password(BCrypt.hashpw("mariya", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("mariya@mail.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1997, 1, 20))
                .aboutUser("Увлекается искусством")
                .city("Санкт-Петербург")
                .build());

        accountRepository.save(User.builder()
                .firstName("Александр")
                .lastName("Павлов")
                .middleName("Сергеевич")
                .username("alex")
                .password(BCrypt.hashpw("alex", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("alex@yahoo.com")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1995, 1, 20))
                .aboutUser("Занимается Web-разработкой")
                .city("Москва")
                .build());

        accountRepository.save(User.builder()
                .firstName("Василий")
                .lastName("Сергеев")
                .middleName("Петрович")
                .username("vasiliy")
                .password(BCrypt.hashpw("vasiliy", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("vasiliy@rambler.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1983, 1, 20))
                .aboutUser("Женат. Любит путешествовать.")
                .city("Казань")
                .build());

        accountRepository.save(User.builder()
                .firstName("Елена")
                .lastName("Орлова")
                .middleName("Сергеевна")
                .username("lena")
                .password(BCrypt.hashpw("lena", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("lena@yandex.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1993, 1, 20))
                .aboutUser("Занимается спортом. Ведёт свой блог и тренировки.")
                .city("Сочи")
                .build());

        accountRepository.save(User.builder()
                .firstName("Михаил")
                .lastName("Яковлев")
                .middleName("Максимович")
                .username("mihail36")
                .password(BCrypt.hashpw("mihail", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("mihail36@yandex.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1989, 10, 10))
                .aboutUser("Интересна археология. Ведёт свой канал в Телеграмм")
                .city("Абакан")
                .build());

        accountRepository.save(User.builder()
                .firstName("Дарья")
                .lastName("Антонова")
                .middleName("Романовна")
                .username("darya1984")
                .password(BCrypt.hashpw("darya", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("darya1984@hotmail.com")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1984, 6, 15))
                .aboutUser("Увлекается танцами")
                .city("Екатеринбург")
                .build());

        accountRepository.save(User.builder()
                .firstName("Михаил")
                .lastName("Волков")
                .middleName("Сергеевич")
                .username("michail87")
                .password(BCrypt.hashpw("michail", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("michail87@yandex.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1987, 8, 10))
                .aboutUser("Занимается спортом")
                .city("Мурманск")
                .build());

        accountRepository.save(User.builder()
                .firstName("Филипп")
                .lastName("Симонов")
                .middleName("Арсеньев")
                .username("filipp")
                .password(BCrypt.hashpw("filipp", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("filipp.simonov@mail.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1992, 9, 20))
                .aboutUser("Профессиональный фотограф. Проводит выставки и мастер-классы")
                .city("Владивосток")
                .build());

        accountRepository.save(User.builder()
                .firstName("Максим")
                .lastName("Мухин")
                .middleName("Васильевич")
                .username("maksim1975")
                .password(BCrypt.hashpw("maksim", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("maksim1975@yandex.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1975, 4, 4))
                .aboutUser("Занимается спортом. Увлекается походами и туризмом")
                .city("Краснодар")
                .build());

        accountRepository.save(User.builder()
                .firstName("Ирина")
                .lastName("Тенишева")
                .middleName("Федоровна")
                .username("irina69")
                .password(BCrypt.hashpw("irina", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("irina69@yandex.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1974, 9, 2))
                .aboutUser("Историк. Любит пешие прогулки по городу")
                .city("Санкт-Петербург")
                .build());

        accountRepository.save(User.builder()
                .firstName("Семен")
                .lastName("Топоров")
                .middleName("Герасимович")
                .username("semen22031980")
                .password(BCrypt.hashpw("semen", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("semen22031980@gmail.com")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1982, 4, 23))
                .aboutUser("Увлекается плавание")
                .city("Феодосия")
                .build());

        accountRepository.save(User.builder()
                .firstName("Макар")
                .lastName("Сергеев")
                .middleName("Евгениевич")
                .username("makar7158")
                .password(BCrypt.hashpw("makar", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("makar7158@ya.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1993, 3, 17))
                .aboutUser("Занимается спортом. Любит смотреть фильмы")
                .city("Тверь")
                .build());

        accountRepository.save(User.builder()
                .firstName("Михаил")
                .lastName("Обухов")
                .middleName("Константинович")
                .username("mihail20111966")
                .password(BCrypt.hashpw("mihail", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("mihail20111966@yandex.ru")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1966, 11, 20))
                .aboutUser("Читает книги. Увлекается туристическими походами")
                .city("Новосибирск")
                .build());

        accountRepository.save(User.builder()
                .firstName("Ефим")
                .lastName("Носов")
                .middleName("Всеволодович")
                .username("efim13")
                .password(BCrypt.hashpw("efim", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("efim13@gmail.com")
                .role(Role.ROLE_USER)
                .birthday(LocalDate.of (1969, 10, 7))
                .aboutUser("Увлекается музыкой и восточными боевыми искусствами")
                .city("Хабаровск")
                .build());
    }
}
