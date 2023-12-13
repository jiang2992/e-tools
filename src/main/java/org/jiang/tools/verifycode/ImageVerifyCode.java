package org.jiang.tools.verifycode;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * @author Bin
 * @date 2022/1/5 11:03
 */
@Getter
@Setter
public class ImageVerifyCode {

    private String code;

    private BufferedImage image;

    public ImageVerifyCode(String code, BufferedImage image) {
        this.code = code;
        this.image = image;
    }

    public void write(OutputStream outputStream) {

    }

}
