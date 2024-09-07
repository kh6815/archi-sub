package com.archi_sub.archi_sub.content.content.controller.docs;

import com.archi_sub.archi_sub.common.error.CustomException;
import com.archi_sub.archi_sub.common.model.ApiResponseModel;
import com.archi_sub.archi_sub.content.content.model.ContentModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "콘텐츠 SUB API", description = "콘텐츠 관련 컨트롤러입니다.")
public interface ContentControllerDocs {
}
