package wu.seal.jsontokotlinclass.server.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import wu.seal.jsontokotlinclass.server.data.entities.Hit
import wu.seal.jsontokotlinclass.server.data.repos.HitsRepo
import wu.seal.jsontokotlinclass.server.models.routes.addhit.AddHitRequest
import wu.seal.jsontokotlinclass.server.models.routes.addhit.AddHitResponse
import wu.seal.jsontokotlinclass.server.utils.toHit

@Controller
class AddHitController {

    companion object {
        private fun verifyClient(client: String) {
            val isValidClient = when (client) {
                Hit.CLIENT_API,
                Hit.CLIENT_PLUG_IN,
                Hit.CLIENT_WEB -> true
                else -> false
            }

            require(isValidClient) { "Invalid client `$client`" }
        }
    }

    @Autowired
    lateinit var hitsRepo: HitsRepo

    @PostMapping("/add_hit")
    @ResponseBody
    fun addHit(@RequestBody addHitRequest: AddHitRequest): AddHitResponse {
        verifyClient(addHitRequest.client)
        hitsRepo.save(
                addHitRequest.generateRequest.toHit(addHitRequest.client)
        )
        return AddHitResponse(
                null,
                false,
                -1,
                "Added"
        )
    }


}