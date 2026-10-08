package GameObject;

import java.awt.image.BufferedImage;

// This class is for reading in a SpriteSheet (collection of images laid out in a specific way)
// As long as each graphic on the sheet is the same size, it can parse it into sub images
public class SpriteSheet {
	protected BufferedImage image;
	protected int spriteWidth;
	protected int spriteHeight;
	protected int rowLength;
	protected int columnLength;
	protected int spacing;

	public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeight, int spacing) {
    this.image = image;
    this.spriteWidth = spriteWidth;
    this.spriteHeight = spriteHeight;
    this.spacing = spacing;
    this.rowLength = (image.getHeight() + spacing) / (spriteHeight + spacing);
    this.columnLength = (image.getWidth() + spacing) / (spriteWidth + spacing);
}

public BufferedImage getSubImage(int row, int column) {
    return image.getSubimage(
        column * (spriteWidth + spacing),
        row * (spriteHeight + spacing),
        spriteWidth, spriteHeight);
}

	// returns a subimage from the sprite sheet image based on the row and column
	public BufferedImage getSprite(int spriteNumber, int animationNumber) {
		return image.getSubimage((animationNumber * spriteWidth) + animationNumber, (spriteNumber * spriteHeight) + spriteNumber, spriteWidth, spriteHeight);
	}


	public BufferedImage getImage() {
		return image;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}
}
