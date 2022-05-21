package cn.javastack.springboot.mapstruct.struct;

import cn.javastack.springboot.mapstruct.dto.UserShowDTO;
import cn.javastack.springboot.mapstruct.entity.UserDO;
import cn.javastack.springboot.mapstruct.entity.UserExtDO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-12T09:43:01+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
public class UserStructImpl implements UserStruct {

    @Override
    public UserShowDTO toUserShowDTO(UserDO userDO) {
        if ( userDO == null ) {
            return null;
        }

        UserShowDTO userShowDTO = new UserShowDTO();

        if ( userDO.getBirthday() != null ) {
            userShowDTO.setBirthday( new SimpleDateFormat( "yyyy-MM-dd" ).format( userDO.getBirthday() ) );
        }
        userShowDTO.setRegisterSource( userDOUserExtDORegSource( userDO ) );
        userShowDTO.setFavorite( userDOUserExtDOFavorite( userDO ) );
        userShowDTO.setName( userDO.getName() );
        userShowDTO.setSex( userDO.getSex() );
        userShowDTO.setMarried( userDO.isMarried() );

        userShowDTO.setRegDate( org.apache.commons.lang3.time.DateFormatUtils.format(userDO.getRegDate(),"yyyy-MM-dd HH:mm:ss") );

        return userShowDTO;
    }

    @Override
    public List<UserShowDTO> toUserShowDTOs(List<UserDO> userDOs) {
        if ( userDOs == null ) {
            return null;
        }

        List<UserShowDTO> list = new ArrayList<UserShowDTO>( userDOs.size() );
        for ( UserDO userDO : userDOs ) {
            list.add( toUserShowDTO( userDO ) );
        }

        return list;
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
