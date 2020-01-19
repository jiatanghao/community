package com.zte.oes.community.mapper;

import com.zte.oes.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface UserMapper {

    @Insert("INSERT INTO USER(ACCOUNT_ID, NAME, TOKEN, GMT_CREATE, GMT_MODIFY) " +
            "VALUES(#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModify})")
    void insertUser(User user);
}
