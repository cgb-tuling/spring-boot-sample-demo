package cn.javastack.springboot.mapstruct.struct;

import cn.javastack.springboot.mapstruct.dto.UserMultiDTO;
import cn.javastack.springboot.mapstruct.entity.UserAddressDO;
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
public class UserMultiStructImpl implements UserMultiStruct {

    @Override
    public UserMultiDTO toUserMultiDTO(UserDO userDO, UserAddressDO userAddressDO) {
        if ( userDO == null && userAddressDO == null ) {
            return null;
        }

        UserMultiDTO userMultiDTO = new UserMultiDTO();

        if ( userDO != null ) {
            if ( userDO.getBirthday() != null ) {
                userMultiDTO.setBirthday( new SimpleDateFormat( "yyyy-MM-dd" ).format( userDO.getBirthday() ) );
            }
            userMultiDTO.setName( userDO.getName() );
            userMultiDTO.setSex( userDO.getSex() );
            userMultiDTO.setMarried( userDO.isMarried() );
            if ( userDO.getRegDate() != null ) {
                userMultiDTO.setRegDate( new SimpleDateFormat().format( userDO.getRegDate() ) );
            }
        }
        if ( userAddressDO != null ) {
            userMultiDTO.setPostcode( userAddressDO.getPostcode() );
            userMultiDTO.setAddress( userAddressDO.getAddress() );
        }

        return userMultiDTO;
    }
}
