package cn.javastack.springboot.mapstruct.struct;

import cn.javastack.springboot.mapstruct.dto.UserNestedDTO;
import cn.javastack.springboot.mapstruct.entity.UserAddressDO;
import cn.javastack.springboot.mapstruct.entity.UserExtDO;
import cn.javastack.springboot.mapstruct.entity.UserNestedDO;
import java.text.SimpleDateFormat;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-12T09:43:02+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class UserNestedStructImpl implements UserNestedStruct {

    @Override
    public UserNestedDTO toUserNestedDTO(UserNestedDO userNestedDO) {
        if ( userNestedDO == null ) {
            return null;
        }

        UserNestedDTO userNestedDTO = new UserNestedDTO();

        if ( userNestedDO.getBirthday() != null ) {
            userNestedDTO.setBirthday( new SimpleDateFormat( "yyyy-MM-dd" ).format( userNestedDO.getBirthday() ) );
        }
        userNestedDTO.setMemo( userNestedDOUserExtDOMemo( userNestedDO ) );
        userNestedDTO.setCity( userNestedDOUserAddressDOCity( userNestedDO ) );
        userNestedDTO.setAddress( userNestedDOUserAddressDOAddress( userNestedDO ) );
        userNestedDTO.setRegSource( userNestedDOUserExtDORegSource( userNestedDO ) );
        userNestedDTO.setFavorite( userNestedDOUserExtDOFavorite( userNestedDO ) );
        userNestedDTO.setSchool( userNestedDOUserExtDOSchool( userNestedDO ) );
        userNestedDTO.setName( userNestedDO.getName() );
        userNestedDTO.setSex( userNestedDO.getSex() );
        userNestedDTO.setMarried( userNestedDO.isMarried() );

        userNestedDTO.setRegDate( org.apache.commons.lang3.time.DateFormatUtils.format(userNestedDO.getRegDate(),"yyyy-MM-dd HH:mm:ss") );

        return userNestedDTO;
    }

    private String userNestedDOUserExtDOMemo(UserNestedDO userNestedDO) {
        if ( userNestedDO == null ) {
            return null;
        }
        UserExtDO userExtDO = userNestedDO.getUserExtDO();
        if ( userExtDO == null ) {
            return null;
        }
        String memo = userExtDO.getMemo();
        if ( memo == null ) {
            return null;
        }
        return memo;
    }

    private String userNestedDOUserAddressDOCity(UserNestedDO userNestedDO) {
        if ( userNestedDO == null ) {
            return null;
        }
        UserAddressDO userAddressDO = userNestedDO.getUserAddressDO();
        if ( userAddressDO == null ) {
            return null;
        }
        String city = userAddressDO.getCity();
        if ( city == null ) {
            return null;
        }
        return city;
    }

    private String userNestedDOUserAddressDOAddress(UserNestedDO userNestedDO) {
        if ( userNestedDO == null ) {
            return null;
        }
        UserAddressDO userAddressDO = userNestedDO.getUserAddressDO();
        if ( userAddressDO == null ) {
            return null;
        }
        String address = userAddressDO.getAddress();
        if ( address == null ) {
            return null;
        }
        return address;
    }

    private String userNestedDOUserExtDORegSource(UserNestedDO userNestedDO) {
        if ( userNestedDO == null ) {
            return null;
        }
        UserExtDO userExtDO = userNestedDO.getUserExtDO();
        if ( userExtDO == null ) {
            return null;
        }
        String regSource = userExtDO.getRegSource();
        if ( regSource == null ) {
            return null;
        }
        return regSource;
    }

    private String userNestedDOUserExtDOFavorite(UserNestedDO userNestedDO) {
        if ( userNestedDO == null ) {
            return null;
        }
        UserExtDO userExtDO = userNestedDO.getUserExtDO();
        if ( userExtDO == null ) {
            return null;
        }
        String favorite = userExtDO.getFavorite();
        if ( favorite == null ) {
            return null;
        }
        return favorite;
    }

    private String userNestedDOUserExtDOSchool(UserNestedDO userNestedDO) {
        if ( userNestedDO == null ) {
            return null;
        }
        UserExtDO userExtDO = userNestedDO.getUserExtDO();
        if ( userExtDO == null ) {
            return null;
        }
        String school = userExtDO.getSchool();
        if ( school == null ) {
            return null;
        }
        return school;
    }
}
