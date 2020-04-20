package example.model

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder, HCursor, Json}
import org.http4s.Uri

case class EpisodeDetails(
    title: String,
    plot: String,
    runTime: String
)

object EpisodeDetails {
    implicit val EpisodeDetailsDecoder: Decoder[EpisodeDetails] =
        (c: HCursor) => 
            for {
                title <- c.downField("Title").as[String]
                plot <- c.downField("Plot").as[String]
                runTime <- c.downField("Runtime").as[String]     
            } yield EpisodeDetails(title, plot, runTime)
}

case class Season(
    title: String,
    season: String,
    totalSeasons: String,
    episodes: List[Episode],
    response: String
)

object Season {
    implicit val SeasonDecoder: Decoder[Season] = 
        (c: HCursor) =>
            for {
                title <- c.downField("Title").as[String]
                season <- c.downField("Season").as[String]
                totalSeasons <- c.downField("totalSeasons").as[String]
                episodes <- c.downField("Episodes").as[List[Episode]]
                response <- c.downField("Response").as[String]
            } yield Season(title, season, totalSeasons, episodes, response)
}

case class Episode(
    title: String,
    released: String,
    episode: String,
    imdbRating: String,
    id: String
)

object Episode {
    implicit val episodeDecoder: Decoder[Episode] = 
        (c: HCursor) =>
            for {
                title <- c.downField("Title").as[String]
                released <- c.downField("Released").as[String]
                episode <- c.downField("Episode").as[String]
                imdbRating <- c.downField("imdbRating").as[String]
                id <- c.downField("imdbID").as[String]
            } yield Episode(title, released, episode, imdbRating, id)
}

//////////////////////////////////////////////////////////////////

case class Person(
    id: Int,
    email: String,
    firstName: String,
    lastName: String,
    avatarLink: Option[Uri]
)

object Person {
    implicit val personEncoder: Decoder[Person] = 
        (c: HCursor) =>
            for {
                id <- c.downField("id").as[Int]
                email <- c.downField("email").as[String]
                firstName <- c.downField("first_name").as[String]
                lastName <- c.downField("last_name").as[String]
                avatarLink <- c.downField("avatar").as[String]
            } yield Person(id, email, firstName, lastName, Uri.fromString(avatarLink).toOption)
    }

case class Ad(
    company: String,
    url: Option[Uri],
    text: String
)

object Ad {
    implicit val adDecoder: Decoder[Ad] = 
        (c: HCursor) =>
            for {
                company <- c.downField("company").as[String]
                url <- c.downField("url").as[String]
                text <- c.downField("text").as[String]    
            } yield Ad(company, Uri.fromString(url).toOption, text) 
}

case class Page(
    pageNumber: Int,
    peoplePerPage: Int,
    peopleTotal: Int,
    pagesTotal: Int,
    people: List[Person],
    ad: Ad
)

object Page {
    implicit val PageDecoder: Decoder[Page] = 
        (c: HCursor) =>
            for {
                pageNumber <- c.downField("page").as[Int]
                peoplePerPage <- c.downField("per_page").as[Int]
                peopleTotal <- c.downField("total").as[Int]
                pagesTotal <- c.downField("total_pages").as[Int]
                people <- c.downField("data").as[List[Person]]
                ad <- c.downField("ad").as[Ad]
            } yield Page(pageNumber, peoplePerPage, peopleTotal, pagesTotal, people, ad)
}