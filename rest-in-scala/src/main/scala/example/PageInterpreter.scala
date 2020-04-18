package example.interpreters

import example.algebras.PageAlgebra
import example.model.Page
import example.model.Page._
import cats.effect.Resource
import org.http4s.client.Client
import org.http4s._
import org.http4s.implicits._

import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe._
import cats.effect.Sync


class PageInterpreter[F[_]: Sync: Client](uri: Int => Uri) extends PageAlgebra[F] {
 
    implicit val pageDecoder = jsonOf[F, Page]

    def fetch(pageNumber: Int): F[Page] = F.expect[Page](uri(2))
        // Request[F](
        //     uri = baseURI,
        //     // httpVersion = HttpVersion.`HTTP/2.0`,
        // )
        // method = Get
    // );
}