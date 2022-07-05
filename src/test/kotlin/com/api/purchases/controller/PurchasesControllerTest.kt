package com.api.purchases.controller

import com.api.purchases.build.Builds
import com.api.purchases.constantes.URL_PURCHASES
import com.api.purchases.constantes.URL_PURCHASES_ID
import com.api.purchases.controller.request.PurchasesRequest
import com.api.purchases.integracao.client.response.ClientResponse
import com.api.purchases.integracao.client.rest.RestClient
import com.api.purchases.integracao.product.response.ProductResponse
import com.api.purchases.integracao.product.rest.RestProduct
import com.api.purchases.mapper.PurchasesMapper
import com.api.purchases.model.Purchases
import com.api.purchases.repository.PurchasesRepository
import com.api.purchases.service.PurchasesService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PurchasesControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var purchasesService: PurchasesService
    @MockkBean
    private lateinit var purchasesRepository: PurchasesRepository
    @MockkBean
    private lateinit var restProduct: RestProduct
    @MockkBean
    private lateinit var restClient: RestClient
    @Autowired
    private lateinit var purchasesMapper: PurchasesMapper


    private lateinit var purchases: Purchases
    private lateinit var purchasesRequest: PurchasesRequest
    private lateinit var productResponse: ProductResponse
    private lateinit var clientResponse: ClientResponse

    @BeforeEach
    fun setup(){
        purchases = Builds().createPurchases()
        purchasesRequest = Builds().createPurchasesRequest()
        productResponse = Builds().creatProducResponse()
        clientResponse = Builds().createClientResponse()
        every { purchasesRepository.save(any()) } returns purchases
        every { purchasesRepository.findById(any()) } returns Optional.empty()

        every { restProduct.requestProdust(purchasesRequest.product.idProduct) } returns productResponse
        every { restClient.requestClient(purchasesRequest.idClient) } returns clientResponse
    }

    @Test
    fun `test create new purchases valid`(){

        var body: String = ObjectMapper().writeValueAsString(purchasesRequest)

        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_PURCHASES)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").exists())
    }

    @Test
    fun `test create new purchases invalid id cliente`(){

        purchasesRequest.idClient = -10
        var body: String = ObjectMapper().writeValueAsString(purchasesRequest)

        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_PURCHASES)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `test create new purchases invalid id produto`(){

        purchasesRequest.product.idProduct = -10
        var body: String = ObjectMapper().writeValueAsString(purchasesRequest)

        this.mockMvc.perform(MockMvcRequestBuilders.post(URL_PURCHASES)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `test find all purchases by id client`(){

        every { purchasesRepository.findAllPurschasesByIdClient(purchases.idClient) } returns
                listOf(
                    Builds().createPurchases(),
                    Builds().createPurchases())

        this.mockMvc.perform(MockMvcRequestBuilders.get(URL_PURCHASES_ID, purchases.idClient)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun `test find purchases by id is not exist`(){

        this.mockMvc.perform(MockMvcRequestBuilders.get(URL_PURCHASES_ID, purchases.id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }

}