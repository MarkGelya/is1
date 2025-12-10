# Схема базы данных

![DB schema](docs/img/db.pdf)

1. User - данные для аутентификации и идентификации
2. Profile - хранение персональной информации пользователя
3. UserData - пользовательские данные

# Эндпоинты

### Регистрация пользователя

```bash
curl -X POST http://localhost:8080/auth/register \
-H "Content-Type: application/json" \
-d '{
  "username": "username1",
  "password": "password1",
  "nickname": "nickname1",
  "email": "email1@example.com"
}'
````

Ответ:

```json
{
  "msg": "User registered successfully"
}
```



### Логин пользователя

```bash
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{
  "username": "username1",
  "password": "password1"
}'
```

Ответ:

```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```



### Создание данных

```bash
curl -X POST http://localhost:8080/api/user/data \
-H "Content-Type: application/json" \
-H "Authorization: Bearer pdf" \
-d '{
  "content": "content number 1"
}'
```

Ответ:

```json
{
  "msg": "Post created with id: 1"
}
```



### Получение данных

```bash
curl -X GET http://localhost:8080/api/user/data \
-H "Authorization: Bearer JWT_TOKEN"
```

Ответ:

```json
[
  {
    "id": 1,
    "content": "content number 1",
    "profile": "nickname1"
  }
]
```


## Реализованные меры защиты

### SQL Injection

Используется ORM (Hibernate/JPA) и параметризованные запросы, что исключает возможность внедрения SQL-кода.

### XSS

* Сервер возвращает только JSON, а не HTML
* Spring Security добавляет защитные HTTP-заголовки

```java
@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(String username, String password, String nickname, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .build();
        userRepository.save(user);

        Profile profile = Profile.builder()
            .nickname(nickname)
            .email(email)
            .user(user)
            .build();
        profileRepository.save(profile);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Username doesn't exist"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtService.generateToken(user.getUsername());
    }
}
```

### Аутентификация и авторизация

Аутентификация и авторизация реализованы с помощью JWT. Каждый запрос проверяется фильтром на наличие и корректность токена; неавторизованные запросы блокируются.

```java
// UserDataService
public UserData createUserData(String token, String content) {
    String username = jwtService.extractUsername(token);
    Profile profile = profileRepository.findByUserUsername(username)
        .orElseThrow(() -> new RuntimeException("Profile not found"));
    UserData note = UserData.builder()
        .content(content)
        .profile(profile)
        .build();
    return userDataRepository.save(note);
}
```

### Хранение паролей

Пароли сохраняются в базе данных только в хешированном виде (с помощью BCrypt):

```java
User user = User.builder()
    .username(username)
    .password(passwordEncoder.encode(password))
    .build();
userRepository.save(user);
```


## Отчёты SAST/SCA

Отчёты из CI/CD GitHub Actions:

![SpotBugs report](docs/img/sb.pdf)

