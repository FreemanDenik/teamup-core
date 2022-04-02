package ru.team.up.input.controllerPublicTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.entity.User;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.input.controller.publicController.UserRestControllerPublic;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.response.EventDtoListResponse;
import ru.team.up.input.response.UserDtoResponse;
import ru.team.up.input.service.UserServiceRest;
import ru.team.up.sup.service.ParameterService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TeamupInputUserPublicControllerTest {

    @Mock
    private UserServiceRest userService;
    @Mock
    private ParameterService parameterService;
    @InjectMocks
    private UserRestControllerPublic testedUserRestControllerPublic;
    AutoCloseable mocks;
    User testUser = User.builder()
            .firstName("Marina")
            .lastName("Sysenko")
            .middleName("Alexsandrovna")
            .username("test")
            .email("testemail@gmail.com")
            .password("1234")
            .accountCreatedTime(LocalDate.of(2021, 11, 20))
            .lastAccountActivity(LocalDateTime.of(2021, 11, 20, 19, 00))
            .city("Volgograd")
            .birthday(LocalDate.of(1967, 1, 20))
            .aboutUser("I like to cook")
            .build();

    @Before
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        mocks.close();
    }

    //Тесты метода getUserById
    @Test
    public void getUserByIdWithParam() {
        //given
        SupParameterDto param = SupParameterDto.builder()
                .parameterValue(true)
                .build();
        given(parameterService.getParamByName("TEAMUP_CORE_GET_USER_BY_ID_ENABLED")).willReturn(param);
        //when
        testedUserRestControllerPublic.getUserById(1L);
        //then
        verify(userService).getUserById(1L);
    }

    @Test
    public void getUserByIdEnabledParameterIsFalse() {
        //given
        SupParameterDto param = SupParameterDto.builder()
                .parameterValue(false)
                .build();
        String paramName = "TEAMUP_CORE_GET_USER_BY_ID_ENABLED";
        given(parameterService.getParamByName(paramName)).willReturn(param);
        //when
        //then
        assertThatThrownBy(() -> testedUserRestControllerPublic.getUserById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Method getUserById disabled by parameter " + paramName);
    }

    @Test
    public void getUserByIdEnabledParameterIsNull() {
        //given
        given(parameterService.getParamByName("TEAMUP_CORE_GET_USER_BY_ID_ENABLED")).willReturn(null);
        //when
        testedUserRestControllerPublic.getUserById(1L);
        //then
        verify(userService).getUserById(1L);
    }

    @Test
    public void getUserByIdNotFound() {
        Assert.assertEquals(new UserDtoResponse(null),
                testedUserRestControllerPublic.getUserById(0L));
    }

    //TODO Сделать серверную валидацию/обработку ошибок.
    @Test
    public void testGetByIdUserBadRequest() {
        assertThatThrownBy(() -> testedUserRestControllerPublic.getUserById(Long.parseLong("anything")))
                .isInstanceOf(NumberFormatException.class);
    }

    //Тесты метода getUserByEmail
    @Test
    public void testGetByEmail() {
        //when
        testedUserRestControllerPublic.getUserByEmail(testUser.getEmail());
        //then
        verify(userService).getUserByEmail(testUser.getEmail());
    }

    @Test
    public void testGetByEmailNotFound() {
        Assert.assertEquals(new UserDtoResponse(null),
                testedUserRestControllerPublic.getUserByEmail(testUser.getEmail()));
    }

    //Тесты метода getUserByUsername
    @Test
    public void testGetByUsername() {
        //when
        testedUserRestControllerPublic.getUserByUsername(testUser.getUsername());
        //then
        verify(userService).getUserByUsername(testUser.getUsername());
    }

    @Test
    public void testGetByUsernameNotFound() {
        Assert.assertEquals(new UserDtoResponse(null),
                testedUserRestControllerPublic.getUserByUsername(testUser.getUsername()));
    }

    //Тесты метода getUsersList
    @Test
    public void getAllUsers() {
        //given
        given(userService.getAllUsers()).willReturn(List.of(testUser));
        //when
        testedUserRestControllerPublic.getUsersList();
        //then
        verify(userService).getAllUsers();
    }

    @Test
    public void getAllUsersEmptyList() {
        //given
        given(userService.getAllUsers()).willReturn(List.of());
        //when
        //then
        assertThatThrownBy(() -> testedUserRestControllerPublic.getUsersList())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Список пользователей пуст");
    }

    //Тесты метода getUsersList
    @Test
    public void getEventsByOwnerId() {
        //when
        testedUserRestControllerPublic.getEventsByOwnerId(testUser.getId());
        //then
        verify(userService).getEventsByOwnerId(testUser.getId());
    }

    @Test
    public void getEventsByOwnerIdNotFound() {
        Assert.assertEquals(new EventDtoListResponse(List.of()),
                testedUserRestControllerPublic.getEventsByOwnerId(testUser.getId()));
    }

    //Тесты метода getEventsBySubscriberId
    @Test
    public void getEventsBySubscriberId() {
        //when
        testedUserRestControllerPublic.getEventsBySubscriberId(testUser.getId());
        //then
        verify(userService).getEventsBySubscriberId(testUser.getId());
    }

    @Test
    public void getEventsBySubscriberIdNotFound() {
        Assert.assertEquals(new EventDtoListResponse(List.of()),
                testedUserRestControllerPublic.getEventsBySubscriberId(testUser.getId()));
    }

    //Тесты метода updateUser
    @Test
    public void updateUser() {
        //given
        UserRequest userRequest = new UserRequest(User.builder()
                .firstName("Aleksandr")
                .lastName("Losikhin")
                .middleName("Semyonovich")
                .username("test2")
                .email("test2email@mail.ru")
                .password("4321")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .city("Ryazan")
                .birthday(LocalDate.of(1995, 9, 16))
                .aboutUser("I don't like to cook")
                .build());
        given(userService.getUserById(testUser.getId())).willReturn(testUser);
        //when
        testedUserRestControllerPublic.updateUser(userRequest, testUser.getId());
        //then
        verify(userService).updateUser(userRequest, testUser.getId());
    }

    @Test
    public void updateUserNotFound() {
        //given
        given(userService.getUserById(testUser.getId())).willReturn(null);
        //when
        //then
        assertThatThrownBy(() -> testedUserRestControllerPublic.updateUser(new UserRequest(testUser), 0L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Пользователь не найден");
    }

    //Тесты метода deleteUserById
    @Test
    public void testDeleteUserById() {
        when(userService.getUserById(testUser.getId())).thenReturn(testUser);
        Assert.assertEquals(200, testedUserRestControllerPublic.deleteUserById(testUser.getId()).getStatusCodeValue());
    }

    @Test
    public void testDeleteUserByIdNotFound() {
        when(userService.getUserById(0L)).thenReturn(testUser);
        Assert.assertEquals(204, testedUserRestControllerPublic.deleteUserById(testUser.getId()).getStatusCodeValue());
    }

    //Тесты метода getTopUsersListInCity
    @Test
    public void getTopUsersListInCity() {
        testedUserRestControllerPublic.getTopUsersListInCity(testUser.getCity());
        //then
        verify(userService).getTopUsersInCity(testUser.getCity());
    }
}
