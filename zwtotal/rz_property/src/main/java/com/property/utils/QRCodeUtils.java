package com.property.utils;

import com.alibaba.druid.util.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtils {
//    /**
//     *  生成二维码
//     * @param content 二维码的内容
//     * @return BitMatrix对象
//     * */
//    public static BitMatrix createCode(String content) throws IOException {
//        //二维码的宽高
//        int width = 200;
//        int height = 200;
//
//        //其他参数，如字符集编码
//        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        //容错级别为H
//        hints.put(EncodeHintType.ERROR_CORRECTION , ErrorCorrectionLevel.H);
//        //白边的宽度，可取0~4
//        hints.put(EncodeHintType.MARGIN , 0);
//
//        BitMatrix bitMatrix = null;
//        try {
//            //生成矩阵，因为我的业务场景传来的是编码之后的URL，所以先解码
//            bitMatrix = new MultiFormatWriter().encode(content,
//                    BarcodeFormat.QR_CODE, width, height, hints);
//
//            //bitMatrix = deleteWhite(bitMatrix);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//
//        return bitMatrix;
//    }
//
//    /**
//     *  删除生成的二维码周围的白边，根据审美决定是否删除
//     * @param matrix BitMatrix对象
//     * @return BitMatrix对象
//     * */
//    private static BitMatrix deleteWhite(BitMatrix matrix) {
//        int[] rec = matrix.getEnclosingRectangle();
//        int resWidth = rec[2] + 1;
//        int resHeight = rec[3] + 1;
//
//        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
//        resMatrix.clear();
//        for (int i = 0; i < resWidth; i++) {
//            for (int j = 0; j < resHeight; j++) {
//                if (matrix.get(i + rec[0], j + rec[1]))
//                    resMatrix.set(i, j);
//            }
//        }
//        return resMatrix;
//    }

    // 创建二维码  content为二维码内容 width,height是二维码大小
    public static String crateB64QRCode(String content, int width, int height) {

        String resultImage = "";
        if (!StringUtils.isEmpty(content)) {
            ServletOutputStream stream = null;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            @SuppressWarnings("rawtypes")
            HashMap<EncodeHintType, Comparable> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 指定字符编码为“utf-8”
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // 指定二维码的纠错等级为中级
            hints.put(EncodeHintType.MARGIN, 2); // 设置图片的边距
            try {
                QRCodeWriter writer = new QRCodeWriter();
                //BitMatrix bitMatrix = writer.encode()
                BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(bufferedImage, "png", os);
                /**
                 * 原生转码前面没有 data:image/png;base64 这些字段，返回给前端是无法被解析，可以让前端加，也可以在下面加上
                 */
                resultImage = new String("data:image/png;base64," + Base64.encodeBase64String(os.toByteArray()));
                return resultImage;
            } catch (Exception e) {
                e.printStackTrace();
               // logger.info("二维码生成异常:" + e.getMessage());
            } finally {
                if (stream != null) {
                    try {
                        stream.flush();
                        stream.close();
                    } catch (IOException e) {
                       // logger.info("servlet输出流关闭异常:" + e.getMessage());
                    }

                }
            }
        }
        return resultImage;
    }
}
