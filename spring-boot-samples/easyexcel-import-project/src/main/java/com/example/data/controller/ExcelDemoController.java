package com.example.data.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.example.data.domain.*;
import com.example.data.listener.test.ConverterDataListener;
import com.example.data.listener.test.DemoDataListener;
import com.example.data.listener.test.ReadDataListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

//@RestController
//@RequestMapping("excel/demo")
public class ExcelDemoController {

    /**
     * 测试简单的读excel
     */
    @RequestMapping("testRead")
    public String test() {
        String fileName = "E:\\work\\demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 参数一：读取的excel文件路径
        // 参数二：读取sheet的一行，将参数封装在DemoData实体类中
        // 参数三：读取每一行的时候会执行DemoDataListener监听器
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
        return "SUCCESS";
    }

    /**
     * 测试简单的写excel
     */
    @RequestMapping("testWrite")
    public String testWrite() {
        String fileName = "E:\\work\\demo.xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        // 参数一：写入excel文件路径
        // 参数二：写入的数据类型是DemoData
        // data()方法是写入的数据，结果是List<DemoData>集合
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
        return "SUCCESS";
    }

    /**
     * excel文件的下载
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        EasyExcel.write(response.getOutputStream(), DemoData.class).sheet("模板").doWrite(data());
    }

    @GetMapping("download1")
    public void download1(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("数据写出", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DemoData.class).sheet("模板").doWrite(data());
    }

    /**
     * excel文件的上传
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener()).sheet().doRead();
        return "success";
    }

    @PostMapping("upload1")
    @ResponseBody
    public String upload1(MultipartFile file) throws IOException {
        //EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        return "SUCCESS";
    }

    /**
     * 简单的读取excel
     */
    @GetMapping("simpleRead")
    public String simpleRead() {
        // 写法1：
        String fileName = "E:\\work\\demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
        // 写法2：
        fileName = "demo.xlsx";
        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
        return "success";
    }

    /**
     * 简单的写excel
     */
    @GetMapping("simpleWrite")
    public void simpleWrite() {
        // 写法1
        String fileName = System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("写入方法一").doWrite(data());


        // 写法2，方法二需要手动关闭流
        fileName = System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("写入方法二").build();
        excelWriter.write(data(), writeSheet);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }


    /**
     * 读取多个sheet页
     */
    @GetMapping("repeatedRead")
    public void repeatedRead() {
        String fileName = "E:\\work\\demo.xlsx";
        // 读取全部sheet
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).doReadAll();
        // 读取部分sheet
        fileName = "demo.xlsx";
        ExcelReader excelReader = EasyExcel.read(fileName).build();
        // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
        // readSheet参数设置读取sheet的序号
        ReadSheet readSheet1 =
                EasyExcel.readSheet(0).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
        ReadSheet readSheet2 =
                EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
        // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
        excelReader.read(readSheet1, readSheet2);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    /**
     * 自定义转换器
     */
    @GetMapping("converterRead")
    public void converterRead() {
        String fileName = "E:\\work\\demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ConverterData.class, new ConverterDataListener())
                // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
                // .registerConverter(new CustomStringStringConverter())
                // 读取sheet
                .sheet().doRead();
    }

    /**
     * 多行头
     */
    @GetMapping("complexHeaderRead")
    public void complexHeaderRead() {
        String fileName = "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet()
                // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入默认1行
                .headRowNumber(1).doRead();
    }

    /**
     * 读取表头数据
     */
    @GetMapping("headerRead")
    public void headerRead() {
        String fileName = "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, DemoData.class, new ReadDataListener())
                .sheet().doRead();
    }

    /**
     * 导出指定的列
     */
    @GetMapping("excludeOrIncludeWrite")
    public void excludeOrIncludeWrite() {
        String fileName = "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        // 忽略 date 不导出
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("date");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet("忽略date")
                .doWrite(data());
        fileName = "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        // 根据用户传入字段 假设我们只要导出 date
        Set<String> includeColumnFiledNames = new HashSet<>();
        includeColumnFiledNames.add("date");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).includeColumnFiledNames(includeColumnFiledNames).sheet("导出date")
                .doWrite(data());
    }

    /**
     * 导入指定的列
     */
    @GetMapping("indexWrite")
    public void indexWrite() {
        String fileName = "indexWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, IndexData.class).sheet("模板").doWrite(data());
    }

    /**
     * 复杂头导入
     */
    @GetMapping("complexHeadWrite")
    public void complexHeadWrite() {
        String fileName = "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ComplexHeadData.class).sheet("模板").doWrite(data());
    }

    /**
     * 数据转换导入
     */
    @GetMapping("converterWrite")
    public void converterWrite() {
        String fileName = "converterWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ConverterData.class).sheet("模板").doWrite(data());
    }


    /**
     * 图片写入到excel
     */
    @GetMapping("imageWrite")
    public void imageWrite() throws Exception {
        String fileName = "imageWrite" + System.currentTimeMillis() + ".xlsx";
        // 如果使用流 记得关闭
        InputStream inputStream = null;
        try {
            List<ImageData> list = new ArrayList<ImageData>();
            ImageData imageData = new ImageData();
            list.add(imageData);
            String imagePath = "converter" + File.separator + "img.jpg";
            // 放入五种类型的图片 根据实际使用只要选一种即可
            imageData.setByteArray(FileUtils.readFileToByteArray(new File(imagePath)));
            imageData.setFile(new File(imagePath));
            imageData.setString(imagePath);
            inputStream = FileUtils.openInputStream(new File(imagePath));
            imageData.setInputStream(inputStream);
            imageData.setUrl(new URL(
                    "https://raw.githubusercontent.com/alibaba/easyexcel/master/src/test/resources/converter/img.jpg"));
            EasyExcel.write(fileName, ImageData.class).sheet().doWrite(list);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 行高，列宽设置
     */
    @GetMapping("widthAndHeightWrite")
    public void widthAndHeightWrite() {
        String fileName = "widthAndHeightWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, WidthAndHeightData.class).sheet("模板").doWrite(data());
    }

    /**
     * 合并单元格设置
     */
    @GetMapping("mergeWrite")
    public void mergeWrite() {
        String fileName = "mergeWrite" + System.currentTimeMillis() + ".xlsx";
        // 每隔2行会合并 把eachColumn 设置成 3 也就是我们数据的长度，所以就第一列会合并。当然其他合并策略也可以自己写
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).registerWriteHandler(loopMergeStrategy).sheet("合并单元格")
                .doWrite(data());
    }

    /**
     * 动态表头
     */
    @GetMapping("dynamicHeadWrite")
    public void dynamicHeadWrite() {
        String fileName = "dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName)
                // 这里放入动态头
                .head(head()).sheet("模板")
                // 当然这里数据也可以用 List<List<String>> 去传入
                .doWrite(data());
    }

    // 动态表头的数据格式List<List<String>>
    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<>();
        List<String> head0 = new ArrayList<>();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = new ArrayList<>();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = new ArrayList<>();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }


    /**
     * 简单的填充
     */
    @GetMapping("simpleFill")
    public void simpleFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName = "simple.xlsx";
        // 方案1 根据对象填充
        String fileName = System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        FillData fillData = new FillData();
        fillData.setName("知春秋");
        fillData.setNumber(25);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(fillData);
        // 方案2 根据Map填充
        fileName = System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        Map<String, Object> map = new HashMap<>();
        map.put("name", "知春秋");
        map.put("number", 25);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);
    }


    @GetMapping("complexFill")
    public void complexFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        String templateFileName = "complex.xlsx";
        String fileName = System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        // 如果数据量大 list不是最后一行 参照下一个
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(data(), fillConfig, writeSheet);
        excelWriter.fill(data(), fillConfig, writeSheet);


        // 其他参数可以使用Map封装
        Map<String, Object> map = new HashMap<>();
        excelWriter.fill(map, writeSheet);
        excelWriter.finish();
    }


    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<>();

        for (int i = 60; i < 100; i++) {
            list.add(new DemoData("字符" + i, new Date(), (double) i));
        }
        return list;
    }

}
