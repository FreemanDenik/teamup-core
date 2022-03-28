package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.Interests;
import ru.team.up.core.repositories.InterestsRepository;

import javax.transaction.Transactional;

@Component
@Transactional
@Profile("cdb")
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

        interestsRepository.save(Interests.builder()
                .id(14L)
                .title("Рукоделие")
                .shortDescription("Вышивка, оригами, вязание вещей и игрушек, лоскутное шитье")
                .build());

        interestsRepository.save(Interests.builder()
                .id(15L)
                .title("Моделирование одежды")
                .shortDescription("Создание дизайнерской одежды")
                .build());

        interestsRepository.save(Interests.builder()
                .id(16L)
                .title("Шахматы")
                .shortDescription("Игра в классические шахматы")
                .build());

        interestsRepository.save(Interests.builder()
                .id(17L)
                .title("Фотография")
                .shortDescription("Портрет, Жанровая, Документальная, Научно-прикладная")
                .build());

        interestsRepository.save(Interests.builder()
                .id(18L)
                .title("Охота")
                .shortDescription("Активная, Пассивная, Зимняя охота")
                .build());

        interestsRepository.save(Interests.builder()
                .id(19L)
                .title("Лепка")
                .shortDescription("Пластелин, Воск, Глина")
                .build());

        interestsRepository.save(Interests.builder()
                .id(20L)
                .title("Лыжи")
                .shortDescription("Классические, горные")
                .build());

        interestsRepository.save(Interests.builder()
                .id(21L)
                .title("Пение")
                .shortDescription("Диско, Танго, Сальса, Hip Hop")
                .build());

        interestsRepository.save(Interests.builder()
                .id(22L)
                .title("Робототехника")
                .shortDescription("Создание и модернизация автоматизированных технических систем")
                .build());

        interestsRepository.save(Interests.builder()
                .id(23L)
                .title("Геокешинг")
                .shortDescription("Нахождение тайников оставленных другими игроками")
                .build());

        interestsRepository.save(Interests.builder()
                .id(24L)
                .title("Стекло")
                .shortDescription("Изготовление украшений и предметов интерьера")
                .build());

        interestsRepository.save(Interests.builder()
                .id(25L)
                .title("Коллекционирование")
                .shortDescription("Монеты, Марки, Украшения, Значки")
                .build());

        interestsRepository.save(Interests.builder()
                .id(26L)
                .title("Компьютерная графика")
                .shortDescription("Дизайн, 3D, flash, спецэффекты и т.д.")
                .build());

        interestsRepository.save(Interests.builder()
                .id(27L)
                .title("Кроссворды")
                .shortDescription("Составление и разгадывание")
                .build());

        interestsRepository.save(Interests.builder()
                .id(28L)
                .title("Лошади")
                .shortDescription("Верховая езда, уход")
                .build());

        interestsRepository.save(Interests.builder()
                .id(29L)
                .title("Моделирование")
                .shortDescription("Самолёты, корабли, воздушные змеи, из спичек, из дерева,…")
                .build());

        interestsRepository.save(Interests.builder()
                .id(30L)
                .title("Плетение")
                .shortDescription("Бисер, корзины, коробочки, кружева, макраме")
                .build());

        interestsRepository.save(Interests.builder()
                .id(31L)
                .title("Радиовещание")
                .shortDescription("Подкасты, интернет-радиостанции, радиопередатчики")
                .build());

        interestsRepository.save(Interests.builder()
                .id(32L)
                .title("Рисование")
                .shortDescription("Акварель, холст, бумага, карандаши, стекло, гуашь ")
                .build());

        interestsRepository.save(Interests.builder()
                .id(33L)
                .title("Скрапбукинг")
                .shortDescription("Фотоальбомы своими руками")
                .build());
    }
}
