package example

import org.http4s.client.blaze.BlazeClientBuilder
import cats.effect.IO
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import cats.effect.ContextShift
import example.interpreters.PageInterpreter
import cats.effect.IOApp
import cats.effect.ExitCode
import org.http4s.client.Client
import cats.effect.Sync
import org.http4s._
import org.http4s.implicits._

object Main extends IOApp {

  implicit val cs: ContextShift[IO] = IO.contextShift(global)

  def uri(pageNum: Int) = Uri.unsafeFromString(f"https://reqres.in/api/users?page=${pageNum}")

  def run(args: List[String]): IO[ExitCode] =
    BlazeClientBuilder[IO](global).resource.use(program(_))
  def program(implicit client: Client[IO]) = 
    for {
      pages <- IO(new PageInterpreter[IO](uri))
      page <- pages.fetch(2)
      _ <- IO(println(page))
    } yield ExitCode.Success
}
