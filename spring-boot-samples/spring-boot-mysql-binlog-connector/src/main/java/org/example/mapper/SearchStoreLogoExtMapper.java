package org.example.mapper;

import org.example.domain.SearchStoreLogo;

import java.util.List;

/**
 * @author yuancetian
 */
public interface SearchStoreLogoExtMapper {
    List<SearchStoreLogo> selectList(SearchStoreLogo searchStoreLogo);
}
