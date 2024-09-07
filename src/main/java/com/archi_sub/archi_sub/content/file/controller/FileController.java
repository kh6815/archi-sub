package com.archi_sub.archi_sub.content.file.controller;

import com.archi_sub.archi_sub.content.file.controller.docs.FileControllerDocs;
import com.archi_sub.archi_sub.content.file.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/file")
public class FileController implements FileControllerDocs {

    private final FileService fileService;

    // 1. 삭제가 된 파일을 배치로 삭제
    // 2. 유저 또는 컨텐츠 어디에도 속하지 않는 파일일 경우 삭제 -> 프로필 사진을 등록하거나 컨텐츠 작성 완료를 안하고 끌 경우
}
