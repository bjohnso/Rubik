package com.rubix.rendering.textures;

import com.rubix.rendering.util.ImageTransformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Texture {

    private final static Map<String, BufferedImage> textureMap = new HashMap<>();
    private BufferedImage image;
    private String fileName;
    private int width, height;
    private boolean transform = false;

    public Texture(String fileName, boolean transform) {
        this.fileName = fileName;
        BufferedImage oldImage = textureMap.get(fileName);
        if (oldImage != null) {
            this.image = oldImage;
        } else {
            try {
                this.image = ImageIO.read(new File("./res/textures/" + fileName + ".png"));
                textureMap.put(fileName, this.image);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        this.width = image.getWidth();
        this.height = image.getHeight();
        transform(transform);
    }

    public Texture(String fileName, int width, int height, boolean transform) {
        this.fileName = fileName;
        this.width = width;
        this.height = height;
        BufferedImage oldImage = textureMap.get(fileName);
        if (oldImage != null) {
            this.image = oldImage;
        } else {
            try {
                BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName + ".png"));
                this.image = ImageTransformer.resize(image, width, height);
                textureMap.put(fileName, this.image);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        transform(transform);
    }

    public Texture(Texture spritesheet, int x, int y, int width, int height){
        this.width = width;
        this.height = height;
        String key = spritesheet.fileName + "_" + x + "_" + y;
        BufferedImage oldImage = textureMap.get(key);
        if (oldImage != null)
            this.image = oldImage;
        else {
            this.image = spritesheet.image.getSubimage(
                    x * width - width,
                    y * height - height,
                    width,
                    height);
            textureMap.put(key, this.image);
        }
    }

    public Texture(Texture spritesheet, int x, int y, int size){
        this(spritesheet, x, y, size, size);
    }

    private void transform(boolean transform){
        if (this.transform != transform)
            this.image = ImageTransformer.horImage(image);
        this.transform = transform;
    }

    public void render (Graphics graphics, double x, double y){
        graphics.drawImage(this.image, (int)x, (int)y, null);
    }

    public BufferedImage getImage(){
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
