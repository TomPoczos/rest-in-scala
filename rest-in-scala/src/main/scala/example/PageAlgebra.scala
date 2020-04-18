package example.algebras

import example.model.Page

trait PageAlgebra[F[_]] {
    def fetch(pageNumber: Int): F[Page]
}