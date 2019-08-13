package com.hichat.provider.mapper;

import com.hichat.common.dto.UsersMessagesDTO;
import com.hichat.common.pojo.Message;
import com.hichat.common.pojo.MessageExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MessageMapper {
    int countByExample(MessageExample example);

    int deleteByExample(MessageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExample(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> getMessage(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId, @Param("time") Date time);


    List<UsersMessagesDTO> getUsersMessage(@Param("param") UsersMessagesDTO param);

}