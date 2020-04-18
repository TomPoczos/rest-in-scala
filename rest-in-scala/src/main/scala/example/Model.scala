package example.model

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder, HCursor, Json}

case class Person(
    id: Int,
    email: String,
    firstName: String,
    lastName: String,
    avatarLink: String
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
            } yield Person(id, email, firstName, lastName, avatarLink)
    }

case class Ad(
    company: String,
    url: String,
    text: String
)

object Ad {
    implicit val adDecoder: Decoder[Ad] = 
        (c: HCursor) =>
            for {
                company <- c.downField("company").as[String]
                url <- c.downField("url").as[String]
                text <- c.downField("text").as[String]    
            } yield Ad(company, url, text)
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