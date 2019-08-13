package com.hichat.common.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ImageHelper {
    /*
     * 根据尺寸图片居中裁剪
     */
    public static void cutCenterImage(String src,String dest,int w,int h,String imgType) throws IOException{
        Iterator iterator = ImageIO.getImageReadersByFormatName(imgType);
        ImageReader reader = (ImageReader)iterator.next();
        InputStream in=new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        Rectangle rect =null;
        //360 480
        if(reader.getWidth(imageIndex)<=w&&reader.getHeight(imageIndex)>h){
            rect = new Rectangle(0, (reader.getHeight(imageIndex)-h)/2, reader.getWidth(imageIndex), reader.getWidth(imageIndex));
        }
        else if(reader.getWidth(imageIndex)>w&&reader.getHeight(imageIndex)<=h){
            rect = new Rectangle((reader.getWidth(imageIndex)-w)/2, 0, reader.getHeight(imageIndex), reader.getHeight(imageIndex));
        }
        else if(reader.getWidth(imageIndex)<w&&reader.getHeight(imageIndex)<h){
            if(reader.getWidth(imageIndex)>reader.getHeight(imageIndex)){
                rect = new Rectangle(0, 0, reader.getHeight(imageIndex), reader.getHeight(imageIndex));
            }else{
                rect = new Rectangle(0, 0, reader.getWidth(imageIndex), reader.getWidth(imageIndex));
            }
        }
        else{
            rect = new Rectangle((reader.getWidth(imageIndex)-w)/2, (reader.getHeight(imageIndex)-h)/2, w, h);
        }
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0,param);
        ImageIO.write(bi, imgType, new File(dest));

    }
    /*
     * 图片裁剪二分之一
     */
    public static void cutHalfImage(String src,String dest) throws IOException{
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader)iterator.next();
        InputStream in=new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        int width = reader.getWidth(imageIndex)/2;
        int height = reader.getHeight(imageIndex)/2;
        Rectangle rect = new Rectangle(width/2, height/2, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0,param);
        ImageIO.write(bi, "jpg", new File(dest));
    }
    /*
     * 图片裁剪通用接口
     */

    public static void cutImage(String src,String dest,int x,int y,int w,int h) throws IOException{
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader)iterator.next();
        InputStream in=new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, w,h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0,param);
        ImageIO.write(bi, "jpg", new File(dest));

    }
    /*
     * 图片缩放
     */
    public static void zoomImage(String src,String dest,int w,int h) throws Exception {
        double wr=0,hr=0;
        File srcFile = new File(src);
        File destFile = new File(dest);
        BufferedImage bufImg = ImageIO.read(srcFile);
        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);
        wr=w*1.0/bufImg.getWidth();
        hr=h*1.0 / bufImg.getHeight();
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static int resultWidth=0;
    private static int resultHeight=0;
    private static void scaleLength(int length,int scale,int type,String uploadType){
        length = length / scale;
        if(length>(uploadType.equals("essay") ? 1000 : 300)){
            scaleLength(length,scale,type,uploadType);
        }else{
            if(type==1){
                //宽度
                resultWidth=length;
            }else{
                resultHeight=length;
            }
        }
    }

    public final static void scale(String srcImageFile, String result,
                                   int scale, boolean flag,String imgType,String uploadType) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            if (flag) {// 放大
                width = width * scale;
                height = height * scale;
            } else {// 缩小
                if(width>height){
                    scaleLength(width,scale,1,uploadType);
                    int total = width / resultWidth;
                    int count = total / scale;
                    width=resultWidth;
                    height=height/(scale*count);
                }else{
                    scaleLength(height,scale,2,uploadType);
                    int total = height / resultHeight;
                    int count = total / scale;
                    height=resultHeight;
                    width=width/(scale*count);
                }
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, imgType, new File(result));// 输出到文件流
            resultWidth=0;
            resultHeight=0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
