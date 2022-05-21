package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping
@Api(tags = "首页")
public class IndexController {
    @ApiOperation(value = "基础数据导入", notes = "基础数据导入")
    @PostMapping(value = "base", headers = "content-type=multipart/form-data")
    @ResponseBody
    public String baseImport(@ApiParam(value = "excel文件", required = true) @RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Assert.notNull(originalFilename, "file name must not be null.");
        String fileExt = originalFilename.substring(originalFilename.indexOf(".") + 1);
        return "success";
    }
}
