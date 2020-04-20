package example.stats

import example.model.Season
import example.model.EpisodeDetails

object ImdbStats {
    def mostUsedWord(episodes: List[EpisodeDetails]) = 
        episodes
            .flatMap(_.plot.split(" "))
            .groupBy(identity)
            .mapValues(_.size)
            .toMap
            .groupBy(_._2)
            .mapValues(_.keys)
            .max
    
    // the assumption that all episodes last less than an hour holds for the data I've seen
    def seasonRunTime(episodes: List[EpisodeDetails]) = 
        episodes.map(_.runTime.split(" ").head.toInt).sum


    def averageRating(season: Season) = {
        val ratings = season.episodes.map(_.imdbRating.toDouble)
        ratings.sum / ratings.size
    }
}

