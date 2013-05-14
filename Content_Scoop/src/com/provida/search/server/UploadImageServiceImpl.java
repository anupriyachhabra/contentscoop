package com.provida.search.server;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.provida.search.client.UploadImageService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UploadImageServiceImpl extends RemoteServiceServlet implements
		UploadImageService {

	public String uploadImage(String urlString) throws IllegalArgumentException {

		Image image = null;
		try {
			URL url = new URL(urlString);
			image = ImageIO.read(url);
			image = image.getScaledInstance(1000, 1500, Image.SCALE_SMOOTH);
			BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), BufferedImage.TYPE_INT_RGB);

			//using "painter" we can draw in to "bufferedImage"
			Graphics2D painter = bufferedImage.createGraphics();

			//draw the "image" to the "bufferedImage"
			painter.drawImage(image, null, null);
			File outputImg=new File("output_"+System.currentTimeMillis()+".jpg");

			//write the image to the file
			ImageIO.write(bufferedImage, "jpg", outputImg);
            return outputImg.getName();
		} catch (IOException e) {
		}
		return null;
	}

	
}
