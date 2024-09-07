package com.archi_sub.archi_sub.content.content.service;

import com.archi_sub.archi_sub.common.error.CustomException;
import com.archi_sub.archi_sub.content.content.model.ContentModel;
import com.archi_sub.archi_sub.db.entity.content.ContentEntity;
import com.archi_sub.archi_sub.db.repository.content.ContentDao;
import com.archi_sub.archi_sub.db.repository.content.ContentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContentReadService {

    private final ContentRepository contentRepository;
    private final ContentDao contentDao;


}
