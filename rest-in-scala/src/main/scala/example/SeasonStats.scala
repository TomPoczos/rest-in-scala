package example.stats

import example.model.Season
import example.model.EpisodeDetails

object ImdbStats {

    private def findMostUsed(s: String) = 
        s.split(" ")
            .groupBy(identity)
            .mapValues(_.size)
            .toMap
            .groupBy(_._2)
            .mapValues(_.keys)
            .max

    def mostUsedWord(episodes: List[EpisodeDetails]) = 
        episodes
            .map( ep => (ep.title, findMostUsed(ep.plot)))
            .maxBy(_._2._1)
    
    // the assumption that all episodes last less than an hour holds for the data I've seen
    def seasonRunTime(episodes: List[EpisodeDetails]) = 
        episodes.map(_.runTime.split(" ").head.toInt).sum


    def averageRating(season: Season) = {
        val ratings = season.episodes.map(_.imdbRating.toDouble)
        ratings.sum / ratings.size
    }
}

