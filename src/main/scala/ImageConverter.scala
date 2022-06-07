import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import scala.annotation.tailrec

object ImageConverter {
  def CreateBorder(input: BufferedImage, border_size: Int, border_color: Int): BufferedImage = {
    val out: BufferedImage = new BufferedImage(input.getWidth, input.getHeight, BufferedImage.TYPE_INT_RGB)

    for (x <- 0 until border_size)
      for (y <- 0 until input.getHeight)
        out.setRGB(x, y, border_color)

    for (x <- 0 until input.getWidth)
      for (y <- 0 until border_size)
        out.setRGB(x, y, border_color)

    for (x <- input.getWidth - border_size until input.getWidth)
      for (y <- 0 until input.getHeight)
        out.setRGB(x, y, border_color)

    for (x <- 0 until input.getWidth)
      for (y <- input.getHeight - border_size until input.getHeight)
        out.setRGB(x, y, border_color)

    for (x <- border_size until input.getWidth - border_size)
      for (y <- border_size until input.getHeight - border_size)
        out.setRGB(x, y, input.getRGB(x, y))

    out
  }

  def CropImage(input: BufferedImage, startX: Int, startY: Int, endX: Int, endY: Int): BufferedImage = {
    val new_width = endX - startX
    val new_height = endY - startY
    val out: BufferedImage = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB)

    def CropImageCycle(image: BufferedImage, x: Int, y: Int): Unit = {
      if (x < image.getWidth) {
        if (y < image.getHeight) {
          out.setRGB(x, y, input.getRGB(startX + x, startY + y))
          CropImageCycle(image, x + 1, y)
        } else {
          // Exit
        }
      } else {
        CropImageCycle(image, 0, y + 1)
      }
    }

    CropImageCycle(out, 0, 0);
//    for (x <- 0 until new_width)
//      for (y <- 0 until new_height)
//        out.setRGB(x, y, input.getRGB(startX + x, startY + y))

    out
  }


  def TransformImage(input: BufferedImage, TransformFunction: (BufferedImage, Int, Int) => Int): BufferedImage = {
    val out: BufferedImage = new BufferedImage(input.getWidth, input.getHeight, BufferedImage.TYPE_INT_RGB)

    def TransformImageCycle(image: BufferedImage, x: Int, y: Int): Unit = {
      if (x < image.getWidth) {
        if (y < image.getHeight) {
          out.setRGB(x, y, TransformFunction(input, x, y))
          TransformImageCycle(image, x + 1, y)
        } else {
          // Exit
        }
      } else {
        TransformImageCycle(image, 0, y + 1)
      }
    }

    TransformImageCycle(out, 0, 0);

    //    for (x <- 0 until input.getWidth)
    //      for (y <- 0 until input.getHeight)
    //        out.setRGB(x, y, TransformFunction(input, x, y))

    out
  }

  def FlipHorizontal(input: BufferedImage, x: Int, y: Int): Int = {
    input.getRGB(input.getWidth - x - 1, y) & 0xffffff
  }

  def FlipVertical(input: BufferedImage, x: Int, y: Int): Int = {
    input.getRGB(x, input.getHeight - y - 1) & 0xffffff
  }

  def RGBtoColor(color: Int): (Int, Int, Int) = {
    ((color & 0xff0000) / 65536, (color & 0xff00) / 256, color & 0xff)
  }

  def ColortoRGB(color: (Int, Int, Int)): Int = {
    color._1 * 65536 + color._2 * 256 + color._3
  }

  def Invert(input: BufferedImage, x: Int, y: Int): Int = {
    ColortoRGB(InvertPixel(RGBtoColor(input.getRGB(x, y))))
  }

  def PixelArt(input: BufferedImage, x: Int, y: Int): Int = {
    ColortoRGB(TransformtoPixelArt(RGBtoColor(input.getRGB(x, y))))
  }

  def InvertPixel(color: (Int, Int, Int)): (Int, Int, Int) = {
    (InvertColor(color._1), InvertColor(color._2), InvertColor(color._3))
  }

  def InvertColor(x: Int): Int = {
    255 - x
  }

  def TransformtoPixelArt(color: (Int, Int, Int)): (Int, Int, Int) = {
    (TransformPixel(color._1), TransformPixel(color._2), TransformPixel(color._3))
  }

  def TransformPixel(x: Int): Int = {
    x / 32 * 32
  }

  def isEqual(input1: BufferedImage, input2: BufferedImage): Boolean = {
    @tailrec
    def isEqualCycle(input1: BufferedImage, input2: BufferedImage, x: Int, y: Int): Boolean = {
      if (x >= input1.getWidth) {
        isEqualCycle(input1, input2, 0, y + 1)
      } else if (y >= input1.getHeight) {
        true
      } else {
        input1.getRGB(x, y) == input2.getRGB(x, y) && isEqualCycle(input1, input2, x + 1, y)
      }
    }

    if (input1.getWidth == input2.getWidth && input1.getHeight == input2.getHeight) {
      isEqualCycle(input1, input2, 0, 0)
    } else {
      false
    }
  }
}