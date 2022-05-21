package org.example.ldap.dao;

import org.example.ldap.model.Person;
import org.springframework.data.repository.CrudRepository;

import javax.naming.Name;

/**
 * Description: spring-boot2
 * User: yuanct
 * Date: 2019/1/11 2:05 PM
 */
public interface PersonRepository extends CrudRepository<Person, Name> {

}
