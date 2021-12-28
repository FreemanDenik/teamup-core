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

    private final UserRepository userRepository;

    @Autowired
    public UsersDefaultCreator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Bean("UsersDefaultCreator")
    public void usersDefaultCreator() {
        userRepository.save(User.builder()
                .id(1L)
                .name("User")
                .lastName("DefaultUser")
                .login("user")
                .password(BCrypt.hashpw("user", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("user@gmail.com")
                .role(Role.ROLE_USER)
                .age(100)
                .aboutUser("Default user")
                .city("Default city")
                .build());

        userRepository.save(User.builder()
                .id(2L)
                .name("Иван")
                .lastName("Петров")
                .middleName("Иванович")
                .login("ivan")
                .password(BCrypt.hashpw("ivan", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("ivan@mail.ru")
                .role(Role.ROLE_USER)
                .age(30)
                .aboutUser("Любит играть в футбол.")
                .city("Иваново")
                .build());

        userRepository.save(User.builder()
                .id(3L)
                .name("Ольга")
                .lastName("Смирнова")
                .middleName("Васильевна")
                .login("olga")
                .password(BCrypt.hashpw("olga", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("olga@yandex.ru")
                .role(Role.ROLE_USER)
                .age(24)
                .aboutUser("Увлекается фотографией")
                .city("Санкт-Петербург")
                .build());

        userRepository.save(User.builder()
                .id(4L)
                .name("Фёдор")
                .lastName("Жуков")
                .middleName("Семёнович")
                .login("fedor")
                .password(BCrypt.hashpw("fedor", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("fedor@hotmail.com")
                .role(Role.ROLE_USER)
                .age(45)
                .aboutUser("Увлекается охотой и рыбалкой.")
                .city("Самара")
                .build());

        userRepository.save(User.builder()
                .id(5L)
                .name("Роман")
                .lastName("Соколов")
                .middleName("Иванович")
                .login("roman")
                .password(BCrypt.hashpw("roman", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
//                .email("roman@gmail.com")
                .email("steffun@mail.ru")
                .role(Role.ROLE_USER)
                .age(28)
                .aboutUser("Любит писать программы на Java.")
                .city("Москва")
                .build());

        userRepository.save(User.builder()
                .id(6L)
                .name("Мария")
                .lastName("Морозова")
                .middleName("Михайловна")
                .login("mariya")
                .password(BCrypt.hashpw("mariya", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("mariya@mail.ru")
                .role(Role.ROLE_USER)
                .age(25)
                .aboutUser("Увлекается искусством")
                .city("Санкт-Петербург")
                .build());

        userRepository.save(User.builder()
                .id(7L)
                .name("Александр")
                .lastName("Павлов")
                .middleName("Сергеевич")
                .login("alex")
                .password(BCrypt.hashpw("alex", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("alex@yahoo.com")
                .role(Role.ROLE_USER)
                .age(27)
                .aboutUser("Занимается Web-разработкой")
                .city("Москва")
                .build());

        userRepository.save(User.builder()
                .id(8L)
                .name("Василий")
                .lastName("Сергеев")
                .middleName("Петрович")
                .login("vasiliy")
                .password(BCrypt.hashpw("vasiliy", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("vasiliy@rambler.ru")
                .role(Role.ROLE_USER)
                .age(39)
                .aboutUser("Женат. Любит путешествовать.")
                .city("Казань")
                .build());

        userRepository.save(User.builder()
                .id(9L)
                .name("Елена")
                .lastName("Орлова")
                .middleName("Сергеевна")
                .login("lena")
                .password(BCrypt.hashpw("lena", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
//                .email("lena@yandex.ru")
                .email("gsteffun@gmail.com")
                .role(Role.ROLE_USER)
                .age(31)
                .aboutUser("Занимается спортом. Ведёт свой блог и тренировки.")
                .city("Сочи")
                .build());
    }
}
