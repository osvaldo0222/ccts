package com.project.ccts.service;

import com.project.ccts.model.Tag;
import com.project.ccts.repository.TagRepository;
import com.project.ccts.service.common.AbstractCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagService extends AbstractCrud<Tag, Long> {

    private TagRepository tagRepository;

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public JpaRepository<Tag, Long> getDao() {
        return tagRepository;
    }

    public Tag findByTagId(String tagId) {
        return tagRepository.findByTagId(tagId);
    }
}
