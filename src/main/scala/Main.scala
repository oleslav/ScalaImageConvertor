import java.io.File
import javax.imageio.ImageIO
import ImageConverter.{CreateBorder, CropImage, FlipHorizontal, FlipVertical, Invert, InvertColor, PixelArt, TransformImage, isEqual}

object Main extends App {
  val input = ImageIO.read(new File("photo2.jpg"))
  println(isEqual(input, input))
//  val invert_image    = TransformImage(input, Invert)
//  val pixelart_image  = TransformImage(input, PixelArt)
//  val fliped_x_image  = TransformImage(input, FlipVertical)
//  val fliped_y_image  = TransformImage(input, FlipHorizontal)
//  val croped_image  = CropImage(input, 100, 100, 500, 500)
//  val image_with_border  = CreateBorder(CreateBorder(input, 20, 255), 10, 0)

//  ImageIO.write(invert_image, "png", new File("invert_test.png"))
//  ImageIO.write(pixelart_image, "png", new File("pixelart_test.png"))
//  ImageIO.write(fliped_x_image, "png", new File("fliped_x_test.png"))
//  ImageIO.write(fliped_y_image, "png", new File("fliped_y_test.png"))
//  ImageIO.write(image_with_border, "png", new File("image_with_border.png"))
}
