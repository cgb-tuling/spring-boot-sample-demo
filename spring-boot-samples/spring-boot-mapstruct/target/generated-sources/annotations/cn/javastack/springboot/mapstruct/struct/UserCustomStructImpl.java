package cn.javastack.springboot.mapstruct.struct;

import cn.javastack.springboot.mapstruct.dto.UserCustomDTO;
import cn.javastack.springboot.mapstruct.entity.UserDO;
import java.text.SimpleDateFormat;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-12T09:43:02+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class UserCustomStructImpl implements UserCustomStruct {

    @Override
    public UserCustomDTO toUserCustomDTO(UserDO userDO) {
        if ( userDO == null ) {
            return null;
        }

        UserCustomDTO userCustomDTO = new UserCustomDTO();

        if ( userDO.getBirthday() != null ) {
            userCustomDTO.setBirthday( new SimpleDateFormat( "yyyy-MM-dd" ).format( userDO.getBirthday() ) );
        }
        userCustomDTO.setUserExtDTO( toUserExtDTO( userDO.getUserExtDO() ) );
        userCustomDTO.setName( userDO.getName() );
        userCustomDTO.setSex( userDO.getSex() );
        userCustomDTO.setMarried( userDO.isMarried() );

        userCustomDTO.setRegDate( org.apache.commons.lang3.time.DateFormatUtils.format(userDO.getRegDate(),"yyyy-MM-dd HH:mm:ss") );

        return userCustomDTO;
    }
}
