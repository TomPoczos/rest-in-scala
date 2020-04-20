package example.algebras

import example.model.Season
import example.model.EpisodeDetails

trait ImdbAlgebra[F[_]] {
    def fetchSeason(): F[Season]
    def fetchEpisodeDetails(id: String): F[EpisodeDetails]
}