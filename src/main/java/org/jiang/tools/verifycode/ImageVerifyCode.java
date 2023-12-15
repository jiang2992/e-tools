package org.jiang.tools.verifycode;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * @author Bin
 * @since 1.0.0
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
