package com.kimboo.androidjobsnewsletter.retrofit.mappers

import com.kimboo.androidjobsnewsletter.retrofit.responses.ApiJobDetailResponse
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Response
import java.net.URL

/**
 * Created by Agustin Tomas Larghi on 3/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class HtmlToJson {

    fun mapAndroidJobsIo(response: Response<ResponseBody>): Response<List<ApiJobDetailResponse>> {
        var result: MutableList<ApiJobDetailResponse> = ArrayList()

        try {
            if (response.isSuccessful!!) {
                val htmlNode = Jsoup.parse(response.body()?.string()!!)

                for (liNode in htmlNode.getElementsByClass("jobs").get(0).getElementsByTag("li")) {
                    var apiResponse = ApiJobDetailResponse()

                    val detailHref = liNode.getElementsByClass("vacancy").get(0).attr("href")

                    val detailNode = Jsoup.parse(URL("https://androidjobs.io" + detailHref), 4000)

                    apiResponse.title = liNode.text()
                    apiResponse.description = detailNode.getElementsByClass("column").get(0).text()
                    apiResponse.url = detailNode.getElementsByClass("highlighted").get(0)
                            .getElementsByTag("a").get(0).attr("href")

                    result.add(apiResponse)
                }

            }
        } catch (e: Exception) {
            return Response.error(response.code(), response.errorBody())
        } finally {
            return Response.success(result.toList())
        }
    }

}
