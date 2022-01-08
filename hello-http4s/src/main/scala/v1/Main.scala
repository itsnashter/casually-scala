package v1

import cats.effect._, org.http4s._
import org.http4s.dsl.io._
import scala.concurrent.ExecutionContext.Implicits.global
import cats.syntax.all._
import org.http4s.implicits._
import cats.implicits._
import org.http4s.blaze.server._
import org.http4s.implicits._
import org.http4s.server.Router
import scala.io.StdIn
import cats.data.Kleisli
import UserRoutes._
import org.http4s.server.Server
object Main extends IOApp.Simple {

  val helloWorldRoute: Kleisli[IO, Request[IO], Response[IO]] = HttpRoutes
    .of[IO] { case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
    }
    .orNotFound

  def createProgram() = {
    val program = for {
      fiber <- BlazeServerBuilder[IO]
        .bindHttp(8080, "localhost")
        .withHttpApp(helloWorldRoute)
        .withHttpApp(userRoutes)
        .resource
        .use(_ => IO.never[Server])
        .start
      _     <- IO.delay(println("Press ENTER to shutdown"))
      _     <- IO.delay(StdIn.readLine())
      //_ = fiber.cancel.unsafeRunSync()
      _     <- IO.delay(println("Server cancelled"))
    } yield ()
    program
  }

  def createBlazeServerAsApp() =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(userRoutes)
      .withHttpApp(helloWorldRoute)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)

  val pressENTER = for {
    _ <- IO.consoleForIO.println(s"Press ENTER to terminate")
    _ <- IO.consoleForIO.readLine
  } yield ()

  override def run: IO[Unit] = {

    val runServer = BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(userRoutes)
      .withHttpApp(helloWorldRoute)
      .serve
      .compile
      .drain
    IO.race(runServer, pressENTER)
      .onCancel(IO.consoleForIO.print("Program terminated"))
      .map(_ => ())
  }

}
