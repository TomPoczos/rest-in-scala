package example.interpreters

import example.algebras.ImdbAlgebra
import example.model.Season
import org.http4s.client.Client
import cats.effect.Sync

import org.http4s._
import org.http4s.implicits._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe._
import example.model.EpisodeDetails

class ImdbInterpreter[F[_]: Sync: Client] extends ImdbAlgebra[F] {

    implicit val jsonOfSeason = jsonOf[F, Season]
    implicit val jsonOfEpisodeDetails = jsonOf[F, EpisodeDetails]

    override def fetchSeason(): F[Season] = 
        F.expect[Season]("https://www.omdbapi.com/?t=Silicon%20Valley&Season=1&apikey=a7da74f5")
  
    override def fetchEpisodeDetails(id: String): F[EpisodeDetails] = 
        F.expect[EpisodeDetails](Uri.unsafeFromString(f"https://www.omdbapi.com/?i=${id}&plot=short&r=json&apikey=a7da74f5"))

}

