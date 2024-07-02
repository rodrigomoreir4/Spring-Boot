package com.rodrigomoreira.myapp.constants;

import com.rodrigomoreira.myapp.domain.users.User;
import com.rodrigomoreira.myapp.domain.users.UserType;

public class UserConstants {
    public static final User USER_WITH_ID = new User(1L,"Rodrigo", "rodrigo@gmail.com", "12345678910", UserType.TEACHER);
    public static final User INVALID_USER = new User();
}
