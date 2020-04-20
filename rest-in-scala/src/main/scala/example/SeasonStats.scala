package example.stats

import example.model.Season
import example.model.EpisodeDetails

object ImdbStats {

    def mostUsedWordsQuestion(episodes: List[EpisodeDetails]) = {

        // found this complex enough to entertain the possibility that I
        // misunderstood the requirements, but after reading the question multiple times
        // this remains my interpretation:

        // there are 2 words that are used most frequently across the entire season, 
        // "the" and "a" are both used 15 times (if I recall correctly)
        // Therefore we need to check in which episode each of them is used most frequently.
        // Hence the result:

        // "a" is used most frequently in "Minimum viable product", 4 times
        // "the" is used most frequently in "Proof of concept". also 4 times
    
        def mostUsedWordsOccurenceInPlot(plot: String, words: List[String]): List[(String, Int)] = 
            words
                .map { word =>
                    plot.split(" ")
                        .filter(word == _)
                        .groupBy(identity)
                        .mapValues(_.size)
                        .toMap
                }
                // if empty it obviously isn't a candidate for the correct answer
                // but filtering empties out allows us to call head and therefore
                // simplify the resulting type
                .filter(theMap => ! theMap.isEmpty)
                .map(_.head)
            
    
        def mostUsedWords(episodes: List[EpisodeDetails]): List[String] = 
            episodes
                .flatMap(_.plot.split(" "))
                .groupBy(identity)
                .mapValues(_.size)
                .toMap
                .groupBy(_._2)
                .mapValues(_.keys)
                .max
                ._2
                .toList
        
        episodes
            .map( ep => (ep.title, mostUsedWordsOccurenceInPlot(ep.plot, mostUsedWords(episodes))))
            .flatMap {case (title, results) => 
                results.map {
                    case (word, numOfOccurences) => (title, word, numOfOccurences)
                }
            }
            .groupBy(_._2)
            .mapValues { listOfTitlesAndOccurences => 
                listOfTitlesAndOccurences.map {
                    case (title: String, word: String, numOfOccurences: Int) => (title, numOfOccurences)
                }
                .maxBy(_._2)
            }
            .toMap
    }
        
    
    // the assumption that all episodes last less than an hour holds for the data I've seen
    def seasonRunTime(episodes: List[EpisodeDetails]) = 
        episodes.map(_.runTime.split(" ").head.toInt).sum


    def averageRating(season: Season) = {
        val ratings = season.episodes.map(_.imdbRating.toDouble)
        ratings.sum / ratings.size
    }
}

