package com.archi_sub.archi_sub.content.file.service;

import com.archi_sub.archi_sub.config.aws.AwsS3Service;
import com.archi_sub.archi_sub.db.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final AwsS3Service awsS3Service;

    @Value("${spring.cloud.aws.folder.photo}")
    private String photoFolder;
}
