import org.scalatest.{Matchers, WordSpec}

import java.io.File
import javax.imageio.ImageIO

import java.awt.image.BufferedImage

import ImageConverter.isEqual

import ImageConverter.TransformPixel
import ImageConverter.InvertColor

import ImageConverter.Invert
import ImageConverter.FlipVertical
import ImageConverter.FlipHorizontal
import ImageConverter.TransformImage

import ImageConverter.ColortoRGB
import ImageConverter.RGBtoColor

class ImageConverterSpec extends WordSpec with Matchers {
  "A Image" should {
    "be equal to self" in {
      val input = ImageIO.read(new File("photo2.jpg"))
      isEqual(input, input) shouldBe true
    }

    "be double inverted" in {
      val input = ImageIO.read(new File("photo2.jpg"))
      val invert_invert_image = TransformImage(TransformImage(input, Invert), Invert)
      isEqual(input, invert_invert_image) shouldBe true
    }

    "be double x fliped" in {
      val input = ImageIO.read(new File("photo2.jpg"))
      val double_flip_x_image = TransformImage(TransformImage(input, FlipHorizontal), FlipHorizontal)
      isEqual(input, double_flip_x_image) shouldBe true
    }

    "be double y fliped" in {
      val input = ImageIO.read(new File("photo2.jpg"))
      val double_flip_y_image = TransformImage(TransformImage(input, FlipVertical), FlipVertical)
      isEqual(input, double_flip_y_image) shouldBe true
    }
  }

  "A Color" should {
    "be the same after transformation" in {
      val rgb : Int = 11168358
      val rgb_converted = ColortoRGB(RGBtoColor(rgb))
      (rgb) shouldBe (rgb_converted)
    }
    "check transform" in {
      val color : Int = 178
      val rgb_converted = TransformPixel(color)
      (rgb_converted) shouldBe (160)
    }
    "be the same after invert" in {
      val color : Int = 178
      val rgb_converted = InvertColor(color)
      (rgb_converted) shouldBe (77)
    }
  }

  "A RGB" should {
    "be the same after transformation" in {
      val (rgb) : (Int, Int, Int) = (255, 178, 22)
      val rgb_converted = RGBtoColor(ColortoRGB(rgb))
      (rgb) shouldBe (rgb_converted)
    }
  }

}