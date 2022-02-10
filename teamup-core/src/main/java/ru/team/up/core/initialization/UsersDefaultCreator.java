package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Transactional
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
    }
}
