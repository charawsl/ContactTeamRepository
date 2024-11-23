package com.yusheng.contactbackend1.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EasyExcelUtils {
    /**
     * 写文件
     *
     * @param outFileName 写的路径
     * @param clazz       用于获取类中的Excel注解
     * @param list        数据
     */
    public static void write(String outFileName, Class<?> clazz, List<?> list) {
        // 新版本会自动关闭流，不需要自己操作
        EasyExcel.write(outFileName, clazz).sheet().doWrite(list);
    }

    /**
     * 写文件
     *
     * @param outFileName 文件名称
     * @param clazz       用于获取类中的Excel注解
     * @param list        数据
     * @param sheetName   工作簿名称
     */
    public static void write(String outFileName, Class<?> clazz, List<?> list, String sheetName) {
        // 新版本会自动关闭流，不需要自己操作
        EasyExcel.write(outFileName, clazz).sheet(sheetName).doWrite(list);
    }

    /**
     * 写文件
     *
     * @param outFile     保存的文件位置(必须存在否则报错)
     * @param outFileName 文件名称
     * @param clazz       用于获取类中的Excel注解
     * @param list        数据
     * @param sheetName   工作簿名称
     */
    public static void write(String outFile, String outFileName, Class<?> clazz, List<?> list, String sheetName) {
        // 新版本会自动关闭流，不需要自己操作
        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(outFile + File.separatorChar + outFileName, clazz);
        excelWriterBuilder.sheet(sheetName).doWrite(list);
    }

    /**
     * 写文件
     *
     * @param outputStream 输出流
     * @param list         数据
     * @param sheetName    工作簿名称
     */
    public static void write(OutputStream outputStream, List<?> list, String sheetName) {
        Class<?> clazz = list.get(0).getClass();
        // sheetName为sheet的名字，默认写第一个sheet
        EasyExcel.write(outputStream, clazz).sheet(sheetName).doWrite(list);
    }

    /**
     * 文件下载，用于前端下载Excel
     *
     * @param sheetName 工作簿名称
     * @param list      数据
     * @throws IOException
     */
    public static void download(String sheetName, List<?> list) {
        HttpServletResponse response = HttpServletUtil.getResponse();
        try {
            Class<?> clazz = list.get(0).getClass();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(sheetName + "_" + DateUtils.format(new Date()), "UTF-8").replaceAll("\\+", "%20") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), clazz)
                    .autoCloseStream(false)
                    .sheet(sheetName)
                    .doWrite(list);
        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private static void handleException(HttpServletResponse response, Exception e) {
        response.reset();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        Map<String, Object> map = MapUtils.newHashMap();
        map.put("status", "failure");
        map.put("message", "下载文件失败" + e.getMessage());
        try {
            response.getWriter().println(mapToJsonString(map));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * 将 Map 对象转换成 JSON 格式的字符串
     *
     * @param map
     * @return
     */
    public static String mapToJsonString(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!first) {
                json.append(",");
            }
            json.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof String) {
                json.append("\"").append(value).append("\"");
            } else {
                json.append(value);
            }
            first = false;
        }

        json.append("}");
        return json.toString();
    }

    /**
     * 读取Excel文件并且返回集合对象
     *
     * @param classT
     * @param filePath
     * @param <T>
     * @return
     */
    public static <T> List<T> readExcel(Class<T> classT, String filePath) {
        List<T> readAllList = new ArrayList<>();
        EasyExcel.read(filePath, classT, new ReadListener<T>() {
            @Override
            public void invoke(T data, AnalysisContext context) {
                //这个每一条数据解析都会来调用
                readAllList.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                //全部读取完成后会调用
            }
        }).sheet().doRead();
        return readAllList;
    }

    public static <T> List<T> readExcel(Class<T> classT, InputStream inputStream) {
        List<T> readAllList = new ArrayList<>();
        try {
            EasyExcel.read(inputStream, classT, new ReadListener<T>() {
                @Override
                public void invoke(T data, AnalysisContext context) {
                    // 这个每一条数据解析都会来调用
                    readAllList.add(data);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    // 全部读取完成后会调用
                    try {
                        inputStream.close(); // 关闭输入流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            // 处理异常情况，比如日志记录或者抛出自定义异常
        }
        return readAllList;
    }

}
