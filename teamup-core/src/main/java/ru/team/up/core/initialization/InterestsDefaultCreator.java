package ru.team.up.core.initialization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.Interests;
import ru.team.up.core.repositories.InterestsRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class InterestsDefaultCreator {

    private final InterestsRepository interestsRepository;

    @Autowired
    public InterestsDefaultCreator(InterestsRepository interestsRepository) {
        this.interestsRepository = interestsRepository;
    }


    @Bean("InterestsDefaultCreator")
    public void interestsDefaultCreator() {
        interestsRepository.save(Interests.builder()
                .id(1L)
                .title("Программирование")
                .shortDescription("Web-разработка, базы данных.")
                .build());

        interestsRepository.save(Interests.builder()
                .id(2L)
                .title("Искусство")
                .shortDescription("Картины, живопись, графика.")
                .build());

        interestsRepository.save(Interests.builder()
                .id(3L)
                .title("Музыка")
                .shortDescription("Игра на музыкальных инструментах")
                .build());

        interestsRepository.save(Interests.builder()
                .id(4L)
                .title("Компьютерные игры")
                .shortDescription("Онлайн, аркады, симуляторы, стратегии")
                .build());

        interestsRepository.save(Interests.builder()
                .id(5L)
                .title("Концерты")
                .shortDescription("Мюзиклы, театр, выступления артистов")
                .build());

        interestsRepository.save(Interests.builder()
                .id(6L)
                .title("Иностранные языки")
                .shortDescription("Изучение иностранных языков, общение с носителями языка")
                .build());

        interestsRepository.save(Interests.builder()
                .id(7L)
                .title("Фитнес")
                .shortDescription("Йога, Бег, CrossFit, Силовые тренировки")
                .build());

        interestsRepository.save(Interests.builder()
                .id(8L)
                .title("Кулинария")
                .shortDescription("Рецепты, Блюда со всего мира")
                .build());

        interestsRepository.save(Interests.builder()
                .id(9L)
                .title("Спортивные игры")
                .shortDescription("Футбол, волейбол, баскетбол, хоккей")
                .build());

        interestsRepository.save(Interests.builder()
                .id(10L)
                .title("Рыбалка")
                .shortDescription("Рыболовство, Зимняя рыбалка, Морская рыбалка")
                .build());

        interestsRepository.save(Interests.builder()
                .id(11L)
                .title("Плавание")
                .shortDescription("Обучение плаванию, дайвинг")
                .build());

        interestsRepository.save(Interests.builder()
                .id(12L)
                .title("Путешествия")
                .shortDescription("Посещение достопримечательностей, пляжный отдых, туризм")
                .build());

        interestsRepository.save(Interests.builder()
                .id(13L)
                .title("Танцы")
                .shortDescription("Диско, Танго, Сальса, Hip Hop")
                .build());
    }
}
