package com.archi_sub.archi_sub.content.content.controller;

import com.archi_sub.archi_sub.content.content.controller.docs.ContentControllerDocs;
import com.archi_sub.archi_sub.content.content.service.ContentReadService;
import com.archi_sub.archi_sub.content.content.service.ContentWriteService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/content")
public class ContentController implements ContentControllerDocs {

    private final ContentReadService contentReadService;
    private final ContentWriteService contentWriteService;

    // 1. best 컨텐츠 만들기
}
