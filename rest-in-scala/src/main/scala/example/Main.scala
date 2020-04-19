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

  def uri(pageNum: Int) = 
    uri"https://reqres.in/api/users".withQueryParams(Map("page" -> pageNum))

  def run(args: List[String]): IO[ExitCode] =
    BlazeClientBuilder[IO](global).resource.use(program(Sync[IO], _))

  def program[F[_]: Sync: Client] = 
    for {
      pages <- F.delay(PageInterpreter(uri))
      page1 <- pages.fetch(1)
      page2 <- pages.fetch(2)
      _     <- F.delay(println(page1))
      _     <- F.delay(println(page2))
    } yield ExitCode.Success
}
