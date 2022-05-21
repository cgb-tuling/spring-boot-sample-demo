package cn.javastack.springboot.mapstruct.struct;

import cn.javastack.springboot.mapstruct.dto.UserShowDTO;
import cn.javastack.springboot.mapstruct.entity.UserDO;
import cn.javastack.springboot.mapstruct.entity.UserExtDO;
import java.text.SimpleDateFormat;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-12T09:43:02+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class UserExistStructImpl implements UserExistStruct {

    @Override
    public void toUserShowDTO(UserShowDTO userShowDTO, UserDO userDO) {
        if ( userDO == null ) {
            return;
        }

        if ( userDO.getBirthday() != null ) {
            userShowDTO.setBirthday( new SimpleDateFormat( "yyyy-MM-dd" ).format( userDO.getBirthday() ) );
        }
        else {
            userShowDTO.setBirthday( null );
        }
        userShowDTO.setRegisterSource( userDOUserExtDORegSource( userDO ) );
        userShowDTO.setFavorite( userDOUserExtDOFavorite( userDO ) );
        userShowDTO.setSex( userDO.getSex() );
        userShowDTO.setMarried( userDO.isMarried() );

        userShowDTO.setRegDate( org.apache.commons.lang3.time.DateFormatUtils.format(userDO.getRegDate(),"yyyy-MM-dd HH:mm:ss") );
    }

    private String userDOUserExtDORegSource(UserDO userDO) {
        if ( userDO == null ) {
            return null;
        }
        UserExtDO userExtDO = userDO.getUserExtDO();
        if ( userExtDO == null ) {
            return null;
        }
        String regSource = userExtDO.getRegSource();
        if ( regSource == null ) {
            return null;
        }
        return regSource;
    }

    private String userDOUserExtDOFavorite(UserDO userDO) {
        if ( userDO == null ) {
            return null;
        }
        UserExtDO userExtDO = userDO.getUserExtDO();
        if ( userExtDO == null ) {
            return null;
        }
        String favorite = userExtDO.getFavorite();
        if ( favorite == null ) {
            return null;
        }
        return favorite;
    }
}
