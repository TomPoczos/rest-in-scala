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
import example.interpreters.ImdbInterpreter
import example.stats.ImdbStats

object Main extends IOApp {

  implicit val cs: ContextShift[IO] = IO.contextShift(global)

  def uri(pageNum: Int) = 
    uri"https://reqres.in/api/users".withQueryParams(Map("page" -> pageNum))

  def run(args: List[String]): IO[ExitCode] =
    BlazeClientBuilder[IO](global).resource.use(program(Sync[IO], _))

  def program[F[_]: Sync: Client] = 
    for {
      seasonFetcher <- F.delay(new ImdbInterpreter)
      season <- seasonFetcher.fetchSeason
      _ <- F.delay(println(f"AVERAGE RATING: ${ImdbStats.averageRating(season)}"))
      episodes <- season.episodes.map(_.id).traverse(seasonFetcher.fetchEpisodeDetails)
      _ <- F.delay(println(f"MOST USED WORD: ${ImdbStats.mostUsedWord(episodes)}"))
      _ <- F.delay(println(f"TOTAL SEASON RUNTIME: ${ImdbStats.seasonRunTime(episodes)} minutes"))
    } yield ExitCode.Success

  def sums(l: List[Int]) = {
    
  }
}
